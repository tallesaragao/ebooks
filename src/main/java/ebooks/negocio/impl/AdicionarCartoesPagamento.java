package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.CartaoCreditoDAO;
import ebooks.dao.IDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.CartaoCredito;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoCartao;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class AdicionarCartoesPagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		if(!carrinho.isPedidoFinalizado()) {
			HttpSession session = carrinho.getSession();
			Pedido pedido = carrinho.getPedido();
			FormaPagamento formaPagamentoConsulta = pedido.getFormaPagamento();
			if(formaPagamentoConsulta != null) {
				List<Pagamento> pagamentos = formaPagamentoConsulta.getPagamentos();
				if(pagamentos != null) {
					Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
					FormaPagamento formaPagamentoSession = pedidoSession.getFormaPagamento();
					if(formaPagamentoSession == null) {
						formaPagamentoSession = new FormaPagamento();
						formaPagamentoSession.setPagamentos(new ArrayList<Pagamento>());
					}
					pedidoSession.setFormaPagamento(formaPagamentoSession);
					for(Pagamento pagamento : pagamentos) {
						if(pagamento.getClass().getName().equals(PagamentoCartao.class.getName())) {
							PagamentoCartao pagamentoCartaoConsulta = (PagamentoCartao) pagamento;
							CartaoCredito cartaoCreditoConsulta = pagamentoCartaoConsulta.getCartaoCredito();
							IDAO dao = new CartaoCreditoDAO();
							try {
								List<EntidadeDominio> consulta = dao.consultar(cartaoCreditoConsulta);
								if(!consulta.isEmpty()) {
									CartaoCredito cartaoCredito = (CartaoCredito) consulta.get(0);
									PagamentoCartao pagamentoCartao = new PagamentoCartao();
									pagamentoCartao.setCartaoCredito(cartaoCredito);
									formaPagamentoSession = pedidoSession.getFormaPagamento();
									if(formaPagamentoSession != null) {
										List<Pagamento> pagamentosSession = formaPagamentoSession.getPagamentos();
										if(pagamentosSession != null) {
											boolean cartaoIgual = false;
											for(Pagamento pagamentoSession : pagamentosSession) {
												if(pagamentoSession.getClass().getName().equals(PagamentoCartao.class.getName())) {
													PagamentoCartao pagamentoCartaoSession = (PagamentoCartao) pagamentoSession;
													long idPagCartaoSession = pagamentoCartaoSession.getCartaoCredito().getId();
													long idPagCartao = pagamentoCartao.getCartaoCredito().getId();
													if(idPagCartaoSession == idPagCartao) {
														cartaoIgual = true;
														break;
													}
												}
											}
											if(!cartaoIgual) {
												pagamentosSession.add(pagamentoCartao);
												session.setAttribute("pedido", pedidoSession);
											}
										}
									}
								}
								else {
									sb.append("Cartão não encontrado:");
								}
							} catch (SQLException e) {
								e.printStackTrace();
								sb.append("Problema na consulta do cartão:");
							}
						}
					}
				}
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
