package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.Carrinho;
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
				Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
				carrinho.setPedido(pedidoSession);
				carrinho.setPedidoSession(pedidoSession);
				carrinho.setPedidoFinalizado(true);
				entidade = carrinho;
			}
			if(operacao.equals("CONSULTAR")) {
				Pedido pedido = new Pedido();
				String idPedido = request.getParameter("idPedido");
				if(idPedido != null && !idPedido.equals("")) {
					pedido.setId(Long.valueOf(idPedido));
				}
				entidade = pedido;
			}
		}		
		return entidade;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/pedidoDetalhes")) {
			request.getRequestDispatcher("WEB-INF/jsp/pedido/view.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/pedidoConfirmarCompra")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			if(resultado.getResposta() == null) {
				Pedido pedido = (Pedido) session.getAttribute("pedido");
				String numeroPedido = pedido.getNumero();
				Long idPedido = pedido.getId();
				request.setAttribute("numeroPedido", numeroPedido);
				request.setAttribute("idPedido", idPedido);
				session.removeAttribute("pedido");
				request.getRequestDispatcher("WEB-INF/jsp/pedido/success.jsp").forward(request, response);
			}
			else {
				request.getRequestDispatcher("pedidoDetalhes").forward(request, response);
			}
		}
		if(uri.equals(contexto + "/pedidoView")) {
			if(resultado.getEntidades() != null) {
				List<EntidadeDominio> consulta = resultado.getEntidades();
				request.setAttribute("pedido", consulta.get(0));
			}
			request.getRequestDispatcher("WEB-INF/jsp/pedido/tracking.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/pedidoList")) {
			request.getRequestDispatcher("WEB-INF/jsp/pedido/list.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/pedidoConsultar")) {
			if(resultado.getEntidades() != null) {
				List<EntidadeDominio> consulta = resultado.getEntidades();
				request.setAttribute("consulta", consulta);
			}
			request.getRequestDispatcher("WEB-INF/jsp/pedido/list.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/pedidoTroca")) {
			if(resultado.getEntidades() != null) {
				List<EntidadeDominio> consulta = resultado.getEntidades();
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
