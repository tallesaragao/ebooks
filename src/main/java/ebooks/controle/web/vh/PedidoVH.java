package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;

public class PedidoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		EntidadeDominio entidade = new EntidadeDominio();
		String operacao = request.getParameter("operacao");
		if(operacao != null) {
			if(operacao.equals("SALVAR")) {
				Carrinho carrinho = new Carrinho();
				HttpSession session = request.getSession();
				carrinho.setSession(session);
				Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
				carrinho.setPedido(pedidoSession);
				carrinho.setPedidoFinalizado(true);
				entidade = carrinho;
			}
			if(operacao.equals("CONSULTAR")) {
				Pedido pedido = new Pedido();
				String idPedido = request.getParameter("idPedido");
				String idCliente = request.getParameter("idCliente");
				if(idCliente != null && !idCliente.equals("")) {
					Cliente cliente = new Cliente();
					cliente.setId(Long.valueOf(idCliente));
					pedido.setCliente(cliente);
				}
				if(idPedido != null && !idPedido.equals("")) {
					pedido.setId(Long.valueOf(idPedido));
				}
				entidade = pedido;
			}
		}		
		return entidade;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/pedidoDetalhes")) {
			request.getRequestDispatcher("WEB-INF/jsp/pedido/view.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/pedidoConfirmarCompra")) {
			if(object == null) {
				HttpSession session = request.getSession();
				Pedido pedido = (Pedido) session.getAttribute("pedido");
				String numeroPedido = pedido.getNumero();
				request.setAttribute("numeroPedido", numeroPedido);
				session.removeAttribute("pedido");
				request.getRequestDispatcher("WEB-INF/jsp/pedido/success.jsp").forward(request, response);
			}
			else {
				request.getRequestDispatcher("pedidoDetalhes").forward(request, response);
			}
		}
		if(uri.equals(contexto + "/pedidoView")) {
			if(object != null) {
				List<EntidadeDominio> consulta = (List<EntidadeDominio>) object;
				request.setAttribute("pedido", consulta.get(0));
			}
			request.getRequestDispatcher("WEB-INF/jsp/pedido/tracking.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/pedidoTroca")) {
			if(object != null) {
				List<EntidadeDominio> consulta = (List<EntidadeDominio>) object;
				request.setAttribute("pedido", consulta.get(0));
			}
			String idPedido = request.getParameter("idPedido");
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idPedido", idPedido);
			request.setAttribute("idCliente", idCliente);
			request.getRequestDispatcher("trocaForm?operacao=").forward(request, response);
		}
	}

}
