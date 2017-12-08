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

public class AdicionarLivroCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		if(!carrinho.isPedidoFinalizado()) {
			Pedido pedido = carrinho.getPedido();
			List<ItemPedido> itensPedido = pedido.getItensPedido();
			IDAO dao = new LivroDAO();
			if(itensPedido != null) {
				for(ItemPedido item : itensPedido) {
					Livro livroConsulta = item.getLivro();
					try {
						List<EntidadeDominio> consulta = dao.consultar(livroConsulta);
						if(!consulta.isEmpty()) {
							Livro livro = (Livro) consulta.get(0);
							if(livro.getAtivo()) {
								long quantidadeAtual = livro.getEstoque().getQuantidadeAtual();
								long quantidadeReservada = livro.getEstoque().getQuantidadeReservada();
								if((quantidadeAtual - quantidadeReservada) < 1) {
									sb.append("Livro indisponível em estoque:");
								}
								else {
									item.setQuantidade(Long.valueOf(1));
									item.setLivro(livro);
									BigDecimal subtotal = new BigDecimal("0.0");
									BigDecimal precoVenda = livro.getPrecificacao().getPrecoVenda();
									subtotal = subtotal.add(precoVenda);
									subtotal = subtotal.multiply(new BigDecimal(item.getQuantidade()));
									subtotal = subtotal.setScale(2, BigDecimal.ROUND_CEILING);
									item.setSubtotal(subtotal);
									Pedido pedidoSession = carrinho.getPedidoSession();
									itensPedido = pedidoSession.getItensPedido();
									boolean livroIgual = false;
									for(ItemPedido itemSession : itensPedido) {
										livroIgual = itemSession.getLivro().getCodigo().equals(item.getLivro().getCodigo());
										if(livroIgual) {
											break;
										}
									}
									if(!livroIgual) {
										itensPedido.add(item);
										Estoque estoque = livro.getEstoque();
										quantidadeReservada += 1;
										estoque.setQuantidadeReservada(quantidadeReservada);
										livro.setEstoque(estoque);
										dao.alterar(livro);								
										
									}
									pedidoSession.setItensPedido(itensPedido);
									carrinho.setPedidoSession(pedidoSession);
								}
							}
							else {
								sb.append("O livro está inativo:");
							}
						}
						else {
							sb.append("Livro não encontrado:");
						}
					} catch (SQLException e) {
						e.printStackTrace();
						sb.append("Problema na consulta SQL:");
					}
				}
			}
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
