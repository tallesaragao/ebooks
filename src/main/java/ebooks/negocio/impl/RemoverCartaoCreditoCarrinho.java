package ebooks.negocio.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoCartao;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class RemoverCartaoCreditoCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		HttpSession session = carrinho.getSession();
		Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
		Pedido pedido = carrinho.getPedido();
		FormaPagamento formaPagamento = pedido.getFormaPagamento();
		if(formaPagamento != null) {
			List<Pagamento> pagamentos = formaPagamento.getPagamentos();
			if(pagamentos != null && !pagamentos.isEmpty()) {
				Iterator<Pagamento> iterator = pagamentos.iterator();
				while(iterator.hasNext()) {
					Pagamento pagamento = iterator.next();
					if(pagamento.getClass().getName().equals(PagamentoCartao.class.getName())) {
						PagamentoCartao pagamentoCartao = (PagamentoCartao) pagamento;
						FormaPagamento formaPagamentoSession = pedidoSession.getFormaPagamento();
						if(formaPagamentoSession != null) {
							List<Pagamento> pagamentosSession = formaPagamentoSession.getPagamentos();
							if(pagamentosSession != null) {
								Iterator<Pagamento> iteratorSession = pagamentosSession.iterator();
								while(iteratorSession.hasNext()) {
									Pagamento pagamentoSession = iteratorSession.next();
									if(pagamentoSession.getClass().getName().equals(PagamentoCartao.class.getName())) {
										PagamentoCartao pagamentoCartaoSession = (PagamentoCartao) pagamentoSession;
										long idCartao = pagamentoCartao.getCartaoCredito().getId();
										long idCartaoSession = pagamentoCartaoSession.getCartaoCredito().getId();
										if(idCartao == idCartaoSession) {
											iteratorSession.remove();
										}
									}
								}
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
