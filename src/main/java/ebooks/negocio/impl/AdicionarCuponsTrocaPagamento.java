package ebooks.negocio.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.CupomTrocaDAO;
import ebooks.dao.IDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.CupomTroca;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class AdicionarCuponsTrocaPagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		BigDecimal totalPagamentosCupom = new BigDecimal("0");
		BigDecimal totalPedido = new BigDecimal("0");
		if(!carrinho.isPedidoFinalizado()) {
			Pedido pedido = carrinho.getPedido();
			FormaPagamento formaPagamentoConsulta = pedido.getFormaPagamento();
			if(formaPagamentoConsulta != null) {
				List<Pagamento> pagamentos = formaPagamentoConsulta.getPagamentos();
				if(pagamentos != null) {
					Pedido pedidoSession = carrinho.getPedidoSession();
					totalPedido = pedidoSession.getValorTotal();
					FormaPagamento formaPagamentoSession = pedidoSession.getFormaPagamento();
					if(formaPagamentoSession == null) {
						formaPagamentoSession = new FormaPagamento();
						formaPagamentoSession.setPagamentos(new ArrayList<Pagamento>());
					}
					pedidoSession.setFormaPagamento(formaPagamentoSession);
					for(Pagamento pagamento : pagamentos) {
						if(pagamento.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
							PagamentoValeCompras pagamentoValeComprasConsulta = (PagamentoValeCompras) pagamento;
							CupomTroca cupomTrocaConsulta = pagamentoValeComprasConsulta.getCupomTroca();
							IDAO dao = new CupomTrocaDAO();
							try {
								List<EntidadeDominio> consulta = dao.consultar(cupomTrocaConsulta);
								if(!consulta.isEmpty()) {
									CupomTroca cupomTroca = (CupomTroca) consulta.get(0);
									PagamentoValeCompras pagamentoValeCompras = new PagamentoValeCompras();
									pagamentoValeCompras.setCupomTroca(cupomTroca);
									pagamentoValeCompras.setValorPago(cupomTroca.getValor());
									formaPagamentoSession = pedidoSession.getFormaPagamento();
									if(formaPagamentoSession != null) {
										List<Pagamento> pagamentosSession = formaPagamentoSession.getPagamentos();
										if(pagamentosSession != null) {
											boolean cupomIgual = false;
											for(Pagamento pagamentoSession : pagamentosSession) {
												if(pagamentoSession.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
													PagamentoValeCompras pagamentoCupomSession = (PagamentoValeCompras) pagamentoSession;
													totalPagamentosCupom = totalPagamentosCupom.add(pagamentoCupomSession.getValorPago());
													long idPagCupomSession = pagamentoCupomSession.getCupomTroca().getId();
													long idPagCupom = pagamentoValeCompras.getCupomTroca().getId();
													if(idPagCupomSession == idPagCupom) {
														cupomIgual = true;
														break;
													}
												}
											}
											if(!cupomIgual) {
												boolean valorNaoExtrapolado = totalPagamentosCupom.doubleValue() < totalPedido.doubleValue();
												if(valorNaoExtrapolado) {
													BigDecimal totalPagamentosCupomTemp = new BigDecimal("0");
													totalPagamentosCupomTemp = totalPagamentosCupom.add(pagamentoValeCompras.getValorPago());
													if(totalPagamentosCupomTemp.doubleValue() > totalPedido.doubleValue()) {
														BigDecimal diferenca = totalPagamentosCupomTemp.subtract(totalPedido);
														BigDecimal valorPago = pagamentoValeCompras.getValorPago();
														valorPago = valorPago.subtract(diferenca);
														pagamentoValeCompras.setValorPago(valorPago);
													}
													pagamentosSession.add(pagamentoValeCompras);
													carrinho.setPedidoSession(pedidoSession);
												}
												else {
													sb.append("Não foi possível adicionar o cupom, valor do pedido extrapolado:");
												}
											}
										}
									}
								}
								else {
									sb.append("Cupom não encontrado:");
								}
							} catch (SQLException e) {
								e.printStackTrace();
								sb.append("Problema na consulta do cupom:");
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
