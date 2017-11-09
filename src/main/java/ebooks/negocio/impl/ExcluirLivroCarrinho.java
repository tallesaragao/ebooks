package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.Iterator;
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

public class ExcluirLivroCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		HttpSession session = carrinho.getSession();
		List<ItemPedido> itensPedido = carrinho.getPedido().getItensPedido();
		if(itensPedido != null) {
			for(ItemPedido item : itensPedido) {
				Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
				List<ItemPedido> itensPedidoSession = pedidoSession.getItensPedido();
				Iterator<ItemPedido> iterator = itensPedidoSession.iterator();
				while(iterator.hasNext()) {
					ItemPedido itemSession = iterator.next();
					if(itemSession.getLivro().getId() == item.getLivro().getId()) {
						IDAO dao = new LivroDAO();
						Livro livro = itemSession.getLivro();
						try {
							List<EntidadeDominio> consulta = dao.consultar(livro);
							livro = (Livro) consulta.get(0);
							Estoque estoque = livro.getEstoque();
							Long quantidadeReservada = estoque.getQuantidadeReservada();
							quantidadeReservada -= itemSession.getQuantidade();
							estoque.setQuantidadeReservada(quantidadeReservada);
							livro.setEstoque(estoque);
							dao.alterar(livro);
							iterator.remove();
						} catch (SQLException e) {
							e.printStackTrace();
							sb.append("Erro ao remover o livro do carrinho:");
						}
					}
				}
				pedidoSession.setItensPedido(itensPedidoSession);
				session.setAttribute("pedido", pedidoSession);
			}
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
