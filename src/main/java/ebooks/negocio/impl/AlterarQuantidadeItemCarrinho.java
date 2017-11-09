package ebooks.negocio.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.IDAO;
import ebooks.dao.LivroDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Estoque;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class AlterarQuantidadeItemCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		if(!carrinho.isPedidoFinalizado()) {
			Pedido pedido = carrinho.getPedido();
			List<ItemPedido> itensPedido = pedido.getItensPedido();
			IDAO dao = new LivroDAO();
			for(ItemPedido item : itensPedido) {
				Livro livro = item.getLivro();
				try {
					//Consulta a quantidade disponível em estoque do livro desejado
					List<EntidadeDominio> consulta = dao.consultar(livro);
					if(!consulta.isEmpty()) {
						Livro livroConsultado = (Livro) consulta.get(0);
						long quantidadeAtual = livroConsultado.getEstoque().getQuantidadeAtual();
						long quantidadeReservada = livroConsultado.getEstoque().getQuantidadeReservada();
						
						HttpSession session = carrinho.getSession();
						Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
						itensPedido = pedidoSession.getItensPedido();
						for(ItemPedido itemSession : itensPedido) {
							if(itemSession.getLivro().getId() == item.getLivro().getId()) {
								if(item.getQuantidade() > itemSession.getQuantidade()) {
									if(item.getQuantidade() - itemSession.getQuantidade() > quantidadeAtual - quantidadeReservada) {
										sb.append("Quantidade desejada do livro não disponível em estoque (Max. " 
												+ (quantidadeAtual - quantidadeReservada)
												+ " un):");
									}
									else {
										quantidadeReservada = quantidadeReservada + (item.getQuantidade() - itemSession.getQuantidade());
										Estoque estoque = livroConsultado.getEstoque();
										estoque.setQuantidadeReservada(quantidadeReservada);
										dao.alterar(livroConsultado);
										itemSession.setQuantidade(item.getQuantidade());
										BigDecimal subtotal = itemSession.getLivro().getPrecificacao().getPrecoVenda();
										subtotal = subtotal.multiply(new BigDecimal(itemSession.getQuantidade()));
										subtotal = subtotal.setScale(2, BigDecimal.ROUND_CEILING);
										itemSession.setSubtotal(subtotal);
										break;
									}
								}
								else if(item.getQuantidade() < itemSession.getQuantidade()) {
									quantidadeReservada = quantidadeReservada + (item.getQuantidade() - itemSession.getQuantidade());
									Estoque estoque = livroConsultado.getEstoque();
									estoque.setQuantidadeReservada(quantidadeReservada);
									dao.alterar(livroConsultado);
									itemSession.setQuantidade(item.getQuantidade());
									BigDecimal subtotal = itemSession.getLivro().getPrecificacao().getPrecoVenda();
									subtotal = subtotal.multiply(new BigDecimal(itemSession.getQuantidade()));
									subtotal = subtotal.setScale(2, BigDecimal.ROUND_CEILING);
									itemSession.setSubtotal(subtotal);
									break;
								}
							}
							
							pedidoSession.setItensPedido(itensPedido);
							session.setAttribute("pedido", pedidoSession);
						}
					}		
				} catch (SQLException e) {
					e.printStackTrace();
					sb.append("Problema na consulta SQL:");
				}
			}
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
