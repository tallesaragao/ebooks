package ebooks.negocio.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoCartao;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class ValidarFormaPagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		HttpSession session = carrinho.getSession();
		Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
		
		if(pedido != null) {
			FormaPagamento formaPagamento = pedido.getFormaPagamento();
			if(formaPagamento != null) {
				List<Pagamento> pagamentos = formaPagamento.getPagamentos();
				BigDecimal valorTotalPagamentos = new BigDecimal("0.0");
				if(pagamentos != null && !pagamentos.isEmpty()) {
					for(Pagamento pag : pagamentos) {
						if(pag.getValorPago() != null) {
							if(pag.getValorPago().doubleValue() == 0.0) {
								sb.append("O(s) valor(es) do(s) pagamento(s) deve(m) ser informado(s):");
								break;
							}
							else {
								valorTotalPagamentos = valorTotalPagamentos.add(pag.getValorPago());
							}
						}
					}
				}
				else {
					valorTotalPagamentos = null;
				}
				if(valorTotalPagamentos != null) {
					valorTotalPagamentos = valorTotalPagamentos.setScale(2, BigDecimal.ROUND_CEILING);
					BigDecimal valorTotalPedido = pedidoSession.getValorTotal();
					valorTotalPedido = valorTotalPedido.setScale(2, BigDecimal.ROUND_CEILING);
					if(valorTotalPagamentos.doubleValue() == valorTotalPedido.doubleValue()) {
						FormaPagamento formaPagamentoSession = pedidoSession.getFormaPagamento();
						Iterator<Pagamento> iterator = formaPagamentoSession.getPagamentos().iterator();
						//Iterando a lista de pagamentos dentro da Session, para setar o valor de cada pagamento
						while(iterator.hasNext()) {
							Pagamento pagSession = iterator.next();
							//Verifica se o pagamento é feito através de cartão de crédito ou vale
							if(pagSession.getClass().getName().equals(PagamentoCartao.class.getName())) {
								PagamentoCartao pagCartaoSession = (PagamentoCartao) pagSession;
								for(Pagamento pag : pagamentos) {
									if(pag.getClass().getName().equals(PagamentoCartao.class.getName())) {
										PagamentoCartao pagCartao = (PagamentoCartao) pag;
										long idCartao = pagCartao.getCartaoCredito().getId();
										long idCartaoSession = pagCartaoSession.getCartaoCredito().getId();
										if(idCartao == idCartaoSession) {
											pagCartaoSession.setValorPago(pagCartao.getValorPago());
										}
									}
								}
							}
							else {
								PagamentoValeCompras pagValeSession = (PagamentoValeCompras) pagSession;
								for(Pagamento pag : pagamentos) {
									if(pag.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
										PagamentoValeCompras pagVale = (PagamentoValeCompras) pag;
										long idVale = pagVale.getValeCompras().getId();
										long idValeSession = pagValeSession.getValeCompras().getId();
										if(idVale == idValeSession) {
											pagValeSession.setValorPago(pagVale.getValorPago());
										}
									}
								}
							}
						}
						pedidoSession.setFormaPagamento(formaPagamentoSession);
						pedidoSession.setValorTotal(valorTotalPedido);
						session.setAttribute("pedido", pedidoSession);
					}
					else {
						if(valorTotalPagamentos.doubleValue() > 0) {
							sb.append("A soma dos valores divididos entre cartões e vale-compras deve totalizar o valor do pedido:");
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
