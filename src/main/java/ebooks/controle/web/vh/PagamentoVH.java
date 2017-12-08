package ebooks.controle.web.vh;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.Carrinho;
import ebooks.modelo.CartaoCredito;
import ebooks.modelo.CupomPromocional;
import ebooks.modelo.CupomTroca;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Login;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoCartao;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.Pedido;

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
			String[] idsCuponsTroca = request.getParameterValues("cuponsTroca");
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
			
			if(idsCuponsTroca != null && idsCuponsTroca.length > 0) {
				for(String idCupom : idsCuponsTroca) {
					CupomTroca cupom = new CupomTroca();
					cupom.setId(Long.valueOf(idCupom));
					PagamentoValeCompras pagamentoValeCompras = new PagamentoValeCompras();
					pagamentoValeCompras.setCupomTroca(cupom);
					String valorCupom = request.getParameter("valorCupom" + idCupom);
					if(valorCupom != null) {
						if(!valorCupom.equals(""))	{
							pagamentoValeCompras.setValorPago(new BigDecimal(valorCupom));
						}
						else {
							pagamentoValeCompras.setValorPago(new BigDecimal("0.0"));
						}
					}
					pagamentos.add(pagamentoValeCompras);
				}
			}
			
			if(codigoPromocional != null && !codigoPromocional.equals("")) {
				CupomPromocional cupomPromocional = new CupomPromocional();
				cupomPromocional.setCodigo(codigoPromocional);
				pedido.setCupomPromocional(cupomPromocional);
				carrinho.setPedido(pedido);
			}
			
			FormaPagamento formaPagamento = new FormaPagamento();
			formaPagamento.setPagamentos(pagamentos);
			pedido.setFormaPagamento(formaPagamento);
			carrinho.setPedido(pedido);
			pedidoSession = (Pedido) session.getAttribute("pedido");
			carrinho.setPedidoSession(pedidoSession);
		}
		if(operacao.equals("CONSULTAR")) {
			carrinho.setPedido(pedidoSession);
			pedidoSession = (Pedido) session.getAttribute("pedido");
			carrinho.setPedidoSession(pedidoSession);
		}
		if(operacao.equals("EXCLUIR")) {
			String idVale = request.getParameter("idVale");
			String idCartao = request.getParameter("idCartao");
			String idCupom = request.getParameter("idCupom");
			FormaPagamento formaPagamento = new FormaPagamento();
			formaPagamento.setPagamentos(new ArrayList<Pagamento>());
			if(idVale != null && !idVale.equals("")) {
				CupomTroca cupomTroca = new CupomTroca();
				cupomTroca.setId(Long.valueOf(idVale));
				PagamentoValeCompras pagamentoValeCompras = new PagamentoValeCompras();
				pagamentoValeCompras.setCupomTroca(cupomTroca);
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
			pedidoSession = (Pedido) session.getAttribute("pedido");
			carrinho.setPedidoSession(pedidoSession);
		}
		return carrinho;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/carrinhoPagamento")) {
			request.getRequestDispatcher("WEB-INF/jsp/pagamento/view.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoSelecionarCartoes")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoSelecionarCupons")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoAdicionarCupom")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			if(resultado.getResposta() != null) {
				String mensagem = resultado.getResposta();
				String[] mensagens = mensagem.split(":");
				if(mensagens.length > 0) {
					request.setAttribute("mensagens", mensagens);
				}
			}
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoRemoverCupom")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoAdicionarValeCompras")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			if(resultado.getResposta() != null) {
				String mensagem = resultado.getResposta();
				String[] mensagens = mensagem.split(":");
				if(mensagens.length > 0) {
					request.setAttribute("mensagens", mensagens);
				}
			}
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoRemoverValeCompras")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/pagamentoRemoverCartao")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			request.getRequestDispatcher("carrinhoPagamento?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/validarFormaPagamento")) {
			List<EntidadeDominio> entidades = resultado.getEntidades();
			Carrinho carrinho = (Carrinho) entidades.get(0);
			Pedido pedidoSession = carrinho.getPedidoSession();
			HttpSession session = request.getSession();
			session.setAttribute("pedido", pedidoSession);
			if(resultado.getResposta() != null) {
				String mensagem = resultado.getResposta();
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
