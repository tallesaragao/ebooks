package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.IDAO;
import ebooks.dao.LivroDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class VerificarDisponibilidadeLivros implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		if(carrinho.isPedidoFinalizado()) {
			Pedido pedido = carrinho.getPedido();
			List<ItemPedido> itensPedido = pedido.getItensPedido();
			IDAO dao = new LivroDAO();
			for(ItemPedido item : itensPedido) {
				Livro livroConsulta = item.getLivro();
				try {
					List<EntidadeDominio> consulta = dao.consultar(livroConsulta);
					if(!consulta.isEmpty()) {
						Livro livro = (Livro) consulta.get(0);
						Long quantidadeAtual = livro.getEstoque().getQuantidadeAtual();
						//Verifica se a quantidade desejada está disponível em estoque
						if(quantidadeAtual.longValue() < item.getQuantidade().longValue()) {
							sb.append("A quantidade desejada do livro ("+ livro.getTitulo() + ") não está mais disponível:");
							carrinho.setPedidoFinalizado(false); //Evita a finalização do pedido
							if(quantidadeAtual.longValue() >= 0) {
								HttpSession session = carrinho.getSession();
								Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
								List<ItemPedido> itensPedidoSession = pedidoSession.getItensPedido();
								//Itera a lista de itens dentro da session para alterar a quantidade
								Iterator<ItemPedido> iterator = itensPedidoSession.iterator();
								if(iterator.hasNext()) {
									ItemPedido itemSession = iterator.next();
									if(itemSession.getLivro().getId().longValue() == livro.getId().longValue()) {
										itemSession.setQuantidade(livro.getEstoque().getQuantidadeAtual());
										//Se a quantidade em estoque for zero, remove o item da lista
										if(quantidadeAtual.longValue() == 0) {
											iterator.remove();
										}
									}
								}
								pedidoSession.setItensPedido(itensPedidoSession);
								session.setAttribute("pedido", pedidoSession);
							}
						}
					}
					else {
						sb.append("Livro não encontrado:");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					sb.append("Problema na consulta de livro:");
				}
				
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
