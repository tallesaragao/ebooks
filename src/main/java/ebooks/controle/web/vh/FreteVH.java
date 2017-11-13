package ebooks.controle.web.vh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;

public class FreteVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Carrinho carrinho = new Carrinho();
		HttpSession session = request.getSession();
		String operacao = request.getParameter("operacao");
		if(operacao == null) {
			operacao = "";
		}
		if(operacao.equals("CONSULTAR")) {
			Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
			String idEndereco = request.getParameter("endereco");
			Endereco endereco = new Endereco();
			endereco.setId(Long.valueOf(idEndereco));
			pedidoSession.setEnderecoEntrega(endereco);
			carrinho.setPedido(pedidoSession);
			carrinho.setSession(session);
		}
		return carrinho;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/freteCalcular")) {
			response.sendRedirect("carrinhoCliente");
		}

	}

}
