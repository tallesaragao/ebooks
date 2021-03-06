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
		Pedido pedidoSession = carrinho.getPedidoSession();
		long quantidadeCartoes = 0;
		if(pedido != null) {
			FormaPagamento formaPagamento = pedido.getFormaPagamento();
			if(formaPagamento != null) {
				List<Pagamento> pagamentos = formaPagamento.getPagamentos();
				BigDecimal valorTotalPagamentos = new BigDecimal("0.0");
				if(pagamentos != null && !pagamentos.isEmpty()) {
					for(Pagamento pag : pagamentos) {
						if(pag.getClass().getName().equals(PagamentoCartao.class.getName())) {
							quantidadeCartoes++;
						}
					}
					for(Pagamento pag : pagamentos) {
						if(pag.getValorPago() != null) {
							if(pag.getValorPago().doubleValue() == 0.0) {
								sb.append("O(s) valor(es) do(s) pagamento(s) deve(m) ser informado(s):");
								break;
							}
							else if(pag.getClass().getName().equals(PagamentoCartao.class.getName())
									&& quantidadeCartoes >= 2
									&& pag.getValorPago().longValue() < 10) {
								sb.append("O valor mínimo a pagar em cada cartão é de R$ 10,00:");
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
					boolean valorValido = false;
					if(quantidadeCartoes > 0) {
						valorValido = valorTotalPagamentos.doubleValue() == valorTotalPedido.doubleValue();
					}
					else {
						valorValido = valorTotalPagamentos.doubleValue() >= valorTotalPedido.doubleValue();
					}
					if(valorValido) {
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
										long idVale = pagVale.getCupomTroca().getId();
										long idValeSession = pagValeSession.getCupomTroca().getId();
										if(idVale == idValeSession) {
											pagValeSession.setValorPago(pagVale.getValorPago());
										}
									}
								}
							}
						}
						pedidoSession.setFormaPagamento(formaPagamentoSession);
						pedidoSession.setValorTotal(valorTotalPedido);
						carrinho.setPedidoSession(pedidoSession);
					}
					else {
						if(valorTotalPagamentos.doubleValue() > 0) {
							sb.append("Pagamento inválido. Se estiver utilizando somente cupons de troca, certifique-se de que a"
									+ " soma de seus valores seja, no mínimo, igual ao valor do pedido. Caso esteja utilizando"
									+ " cartão de crédito, a soma dos cupons e o valor pago nos cartões deve ser igual ao valor"
									+ " total do pedido:");
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
