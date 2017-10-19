package ebooks.negocio.impl;

import java.sql.SQLException;
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

public class AdicionarLivroCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		List<ItemPedido> itensPedido = pedido.getItensPedido();
		IDAO dao = new LivroDAO();
		for(ItemPedido item : itensPedido) {
			Livro livroConsulta = item.getLivro();
			try {
				List<EntidadeDominio> consulta = dao.consultar(livroConsulta);
				if(!consulta.isEmpty()) {
					Livro livro = (Livro) consulta.get(0);
					if(livro.getAtivo()) {
						item.setQuantidade(Long.valueOf(1));
						item.setLivro(livro);
						HttpSession session = carrinho.getSession();
						Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
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
						}
						pedidoSession.setItensPedido(itensPedido);
						session.setAttribute("pedido", pedidoSession);
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
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
