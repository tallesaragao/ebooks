package ebooks.controle.web.vh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.modelo.Status;
import ebooks.modelo.StatusPedido;

public class StatusPedidoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		EntidadeDominio entidade = new EntidadeDominio();
		String operacao = request.getParameter("operacao");
		if(operacao != null) {
			if(operacao.equals("SALVAR")) {
				StatusPedido statusPedido = new StatusPedido();
				String idPedido = request.getParameter("idPedido");
				if(idPedido != null && !idPedido.equals("")) {
					Pedido pedido = new Pedido();
					pedido.setId(Long.valueOf(idPedido));
					statusPedido.setPedido(pedido);
				}
				String nomeStatus = request.getParameter("status");
				if(nomeStatus != null) {
					Status status = new Status();
					status.setNome(nomeStatus);
					statusPedido.setStatus(status);
				}
				
				entidade = statusPedido;
			}
		}
		return entidade;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/statusSalvar")) {
			String idPedido = request.getParameter("idPedido");
			if(idPedido == null) {
				idPedido = "";
			}
			if(object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				request.setAttribute("mensagens", mensagens);
				request.getRequestDispatcher("pedidoView?operacao=CONSULTAR&idPedido=" + idPedido).forward(request, response);
			}
			else {
				response.sendRedirect("pedidoView?operacao=CONSULTAR&idPedido=" + idPedido);
			}
		}
	}

}
