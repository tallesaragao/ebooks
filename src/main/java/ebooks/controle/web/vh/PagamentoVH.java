package ebooks.controle.web.vh;

import java.io.IOException;
import java.math.BigDecimal;
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
					String valorCartao = request.getParameter("valorCartao" + idCartao);
					if(valorCartao != null) {
						if(!valorCartao.equals(""))	{
							pagCartao.setValorPago(new BigDecimal(valorCartao));
						}
						else {
							pagCartao.setValorPago(new BigDecimal("0.0"));
						}
					}
					pagamentos.add(pagCartao);
				}
			}
			if(codigoPromocional != null && !codigoPromocional.equals("")) {
				CupomPromocional cupomPromocional = new CupomPromocional();
				cupomPromocional.setCodigo(codigoPromocional);
				pedido.setCupomPromocional(cupomPromocional);
				carrinho.setPedido(pedido);
			}
			if(codigoValeCompras != null && !codigoValeCompras.equals("")) {
				ValeCompras valeCompras = new ValeCompras();
				valeCompras.setCodigo(codigoValeCompras);
				PagamentoValeCompras pagamentoValeCompras = new PagamentoValeCompras();
				pagamentoValeCompras.setValeCompras(valeCompras);
				String idValeCompras = request.getParameter("idValeCompras");
				if(idValeCompras != null) {
					valeCompras.setId(Long.valueOf(idValeCompras));
				}
				String valorVale = request.getParameter("valorValeCompras" + idValeCompras);
				if(valorVale != null) {
					if(!valorVale.equals("")) {
						pagamentoValeCompras.setValorPago(new BigDecimal(valorVale));
					}
					else {
						pagamentoValeCompras.setValorPago(new BigDecimal("0.0"));
					}
				}
				pagamentos.add(pagamentoValeCompras);
			}
			FormaPagamento formaPagamento = new FormaPagamento();
			formaPagamento.setPagamentos(pagamentos);
			pedido.setFormaPagamento(formaPagamento);
			carrinho.setPedido(pedido);
			carrinho.setSession(request.getSession());
		}
		if(operacao.equals("CONSULTAR")) {
			carrinho.setPedido(pedidoSession);
			carrinho.setSession(request.getSession());
		}
		if(operacao.equals("EXCLUIR")) {
			String idVale = request.getParameter("idVale");
			String idCartao = request.getParameter("idCartao");
			String idCupom = request.getParameter("idCupom");
			FormaPagamento formaPagamento = new FormaPagamento();
			formaPagamento.setPagamentos(new ArrayList<Pagamento>());
			if(idVale != null && !idVale.equals("")) {
				ValeCompras valeCompras = new ValeCompras();
				valeCompras.setId(Long.valueOf(idVale));
				PagamentoValeCompras pagamentoValeCompras = new PagamentoValeCompras();
				pagamentoValeCompras.setValeCompras(valeCompras);
				formaPagamento.getPagamentos().add(pagamentoValeCompras);
			}
			if(idCartao != null && !idCartao.equals("")) {
				CartaoCredito cartaoCredito = new CartaoCredito();
				cartaoCredito.setId(Long.valueOf(idCartao));
				PagamentoCartao pagamentoCartao = new PagamentoCartao();
				pagamentoCartao.setCartaoCredito(cartaoCredito);
				formaPagamento.getPagamentos().add(pagamentoCartao);
			}
			if(!formaPagamento.getPagamentos().isEmpty()) {
				pedido.setFormaPagamento(formaPagamento);
			}
			if(idCupom != null && !idCupom.equals("")) {
				CupomPromocional cupomPromocional = new CupomPromocional();
				cupomPromocional.setId(Long.valueOf(idCupom));
				pedido.setCupomPromocional(cupomPromocional);
			}
			pedido.setItensPedido(null);
			carrinho.setPedido(pedido);
			carrinho.setSession(request.getSession());
		}
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
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoAdicionarCupom")) {
			if(object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				if(mensagens.length > 0) {
					request.setAttribute("mensagens", mensagens);
				}
			}
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoRemoverCupom")) {
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoAdicionarValeCompras")) {
			if(object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				if(mensagens.length > 0) {
					request.setAttribute("mensagens", mensagens);
				}
			}
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoRemoverValeCompras")) {
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoRemoverCartao")) {
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/validarFormaPagamento")) {
			if(object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				if(mensagens.length > 0) {
					request.setAttribute("mensagens", mensagens);
					request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
				}
				else {
					response.sendRedirect("pedidoDetalhes");
				}
			}
		}
	}

}
