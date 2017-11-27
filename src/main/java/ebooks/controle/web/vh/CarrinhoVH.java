package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Login;
import ebooks.modelo.Pedido;

public class CarrinhoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Carrinho carrinho = new Carrinho();
		HttpSession session = request.getSession();
		Login loginSession = (Login) session.getAttribute("login");
		Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
		if(pedidoSession == null) {
			pedidoSession = new Pedido();
			pedidoSession.setItensPedido(new ArrayList<ItemPedido>());
		}
		pedidoSession.setCliente(loginSession.getCliente());
		session.setAttribute("pedido", pedidoSession);
		
		String operacao = request.getParameter("operacao");
		if(operacao.equals("SALVAR")) {
			carrinho.setSession(request.getSession());
			Pedido pedido = new Pedido();
			pedido.setItensPedido(new ArrayList<ItemPedido>());
			Livro livro = new Livro();
			String idLivro = request.getParameter("id");
			livro.setId(Long.valueOf(idLivro));
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setLivro(livro);
			List<ItemPedido> itensPedido = pedido.getItensPedido();
			itensPedido.add(itemPedido);
			pedido.setItensPedido(itensPedido);
			carrinho.setPedido(pedido);
		}
		if(operacao.equals("ALTERAR")) {
			carrinho.setSession(request.getSession());
			Livro livro = new Livro();
			String idLivro = request.getParameter("id");
			livro.setId(Long.valueOf(idLivro));
			String quantidade = request.getParameter("quantidade" + livro.getId());
			ItemPedido item = new ItemPedido();
			item.setLivro(livro);
			item.setQuantidade(Long.valueOf(quantidade));
			List<ItemPedido> itensPedido = new ArrayList<>();
			itensPedido.add(item);
			Pedido pedido = new Pedido();
			pedido.setItensPedido(itensPedido);
			carrinho.setPedido(pedido);
		}
		if(operacao.equals("EXCLUIR")) {
			carrinho.setSession(request.getSession());
			Pedido pedidoRequest = (Pedido) request.getAttribute("pedido");
			List<ItemPedido> itensPedido = new ArrayList<>();
			if(pedidoRequest != null) {
				for(ItemPedido item : pedidoRequest.getItensPedido()) {
					itensPedido.add(item);
				}
			}
			else {
				String idLivro = request.getParameter("id");
				Livro livro = new Livro();
				livro.setId(Long.valueOf(idLivro));
				ItemPedido item = new ItemPedido();
				item.setLivro(livro);
				itensPedido.add(item);
			}
			Pedido pedido = new Pedido();
			pedido.setItensPedido(itensPedido);
			carrinho.setPedido(pedido);
		}
		if(operacao.equals("CONSULTAR")) {
			carrinho.setPedido(pedidoSession);
			carrinho.setSession(request.getSession());
		}
		return carrinho;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/carrinhoCliente")) {
			HttpSession session = request.getSession();
			Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
			if(pedidoSession == null) {
				pedidoSession = new Pedido();
				pedidoSession.setItensPedido(new ArrayList<ItemPedido>());
				session.setAttribute("pedido", pedidoSession);
			}
			else {
				pedidoSession.setFormaPagamento(null);
				session.setAttribute("pedido", pedidoSession);
			}
			request.getRequestDispatcher("carrinhoConsultar?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/carrinhoConsultar")) {
			String pagina = request.getParameter("pagina");
			if(pagina != null && pagina.equals("pagamento")) {
				request.getRequestDispatcher("carrinhoPagamento").forward(request, response);
			}
			else {
				request.getRequestDispatcher("WEB-INF/jsp/carrinho/view.jsp").forward(request, response);
			}
		}
		if(uri.equals(contexto + "/carrinhoAdicionar")) {
			if (object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				if(mensagens.length > 0) {
					request.setAttribute("erro", mensagens[0]);
				}
				request.getRequestDispatcher("carrinhoCliente").forward(request, response);;
				return;
			}
		}
		if(uri.equals(contexto + "/carrinhoRemover")) {
			if (object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				if(mensagens.length > 0) {
					request.setAttribute("erro", mensagens[0]);
				}
				request.getRequestDispatcher("carrinhoCliente").forward(request, response);;
				return;
			}
		}
		if(uri.equals(contexto + "/carrinhoAlterar")) {
			if (object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				if(mensagens.length > 0) {
					request.setAttribute("erro", mensagens[0]);
				}
				request.getRequestDispatcher("carrinhoCliente").forward(request, response);;
				return;
			}
		}
		if(uri.equals(contexto + "/carrinhoPedidoRemover")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("loginSite");
		}

	}

}
