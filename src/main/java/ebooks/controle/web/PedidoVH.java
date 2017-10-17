package ebooks.controle.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Pedido;

public class PedidoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/carrinhoCliente")) {
			HttpSession session = request.getSession();
			Pedido pedido = (Pedido) session.getAttribute("pedido");
			if(pedido == null) {
				session.setAttribute("pedido", new Pedido());
			}
			request.getRequestDispatcher("WEB-INF/jsp/carrinho/view.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/carrinhoAdicionar")) {
			HttpSession session = request.getSession();
			Pedido pedido = (Pedido) session.getAttribute("pedido");
			if(pedido == null) {
				pedido = new Pedido();
			}
			if(pedido.getItensPedido() == null) {
				pedido.setItensPedido(new ArrayList<ItemPedido>());
			}
			Livro livro = (Livro) request.getAttribute("livro");
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setLivro(livro);
			itemPedido.setQuantidade(Long.valueOf(1));
			List<ItemPedido> itensPedido = pedido.getItensPedido();
			boolean livroIgual = false;
			for(ItemPedido item : itensPedido) {
				livroIgual = item.getLivro().getTitulo().equals(livro.getTitulo());
				if(livroIgual) {
					break;
				}
			}
			if(!livroIgual) {
				itensPedido.add(itemPedido);
			}
			pedido.setItensPedido(itensPedido);
			session.setAttribute("pedido", pedido);
			request.getRequestDispatcher("WEB-INF/jsp/carrinho/view.jsp").forward(request, response);
		}

	}

}
