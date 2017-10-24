package ebooks.controle.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.CartaoCredito;
import ebooks.modelo.CupomPromocional;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Login;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoCartao;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.Pedido;
import ebooks.modelo.ValeCompras;

public class PagamentoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Carrinho carrinho = new Carrinho();
		Pedido pedido = new Pedido();
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
			String codigoPromocional = request.getParameter("codigoPromocional");
			String codigoValeCompras = request.getParameter("codigoValeCompras");
			String[] idsCartoesCredito = request.getParameterValues("cartoesCredito");
			List<Pagamento> pagamentos = new ArrayList<>();
			
			if(idsCartoesCredito != null && idsCartoesCredito.length > 0) {
				for(String idCartao : idsCartoesCredito) {
					CartaoCredito cartao = new CartaoCredito();
					cartao.setId(Long.valueOf(idCartao));
					PagamentoCartao pagCartao = new PagamentoCartao();
					pagCartao.setCartaoCredito(cartao);
					pagamentos.add(pagCartao);
				}
			}
			if(codigoPromocional != null) {
				CupomPromocional cupomPromocional = new CupomPromocional();
				cupomPromocional.setCodigo(codigoPromocional);
				pedido.setCupomPromocional(cupomPromocional);
				carrinho.setPedido(pedido);
			}
			if(codigoValeCompras != null) {
				ValeCompras valeCompras = new ValeCompras();
				valeCompras.setCodigo(codigoValeCompras);
				PagamentoValeCompras pagamentoValeCompras = new PagamentoValeCompras();
				pagamentoValeCompras.setValeCompras(valeCompras);
				pagamentos.add(pagamentoValeCompras);
			}
			FormaPagamento formaPagamento = new FormaPagamento();
			formaPagamento.setPagamentos(pagamentos);
			pedido.setFormaPagamento(formaPagamento);
			carrinho.setPedido(pedido);
		}
		carrinho.setSession(session);
		return carrinho;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/carrinhoPagamento")) {
			request.getRequestDispatcher("WEB-INF/jsp/pagamento/view.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoSelecionarCartoes")) {
			request.getRequestDispatcher("carrinhoPagamento").forward(request, response);
		}
	}

}
