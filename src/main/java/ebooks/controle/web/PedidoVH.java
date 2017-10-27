package ebooks.controle.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;

public class PedidoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
		return pedidoSession;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/pedidoDetalhes")) {
			request.getRequestDispatcher("WEB-INF/jsp/pedido/view.jsp").forward(request, response);
		}

	}

}
