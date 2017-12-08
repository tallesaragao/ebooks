package ebooks.negocio.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class RemoverValeComprasCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedidoSession = carrinho.getPedidoSession();
		Pedido pedido = carrinho.getPedido();
		FormaPagamento formaPagamento = pedido.getFormaPagamento();
		if(formaPagamento != null) {
			List<Pagamento> pagamentos = formaPagamento.getPagamentos();
			if(pagamentos != null && !pagamentos.isEmpty()) {
				Iterator<Pagamento> iterator = pagamentos.iterator();
				while(iterator.hasNext()) {
					Pagamento pagamento = iterator.next();
					if(pagamento.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
						PagamentoValeCompras pagamentoValeCompras = (PagamentoValeCompras) pagamento;
						FormaPagamento formaPagamentoSession = pedidoSession.getFormaPagamento();
						if(formaPagamentoSession != null) {
							List<Pagamento> pagamentosSession = formaPagamentoSession.getPagamentos();
							if(pagamentosSession != null) {
								Iterator<Pagamento> iteratorSession = pagamentosSession.iterator();
								while(iteratorSession.hasNext()) {
									Pagamento pagamentoSession = iteratorSession.next();
									if(pagamentoSession.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
										PagamentoValeCompras pagamentoValeComprasSession = (PagamentoValeCompras) pagamentoSession;
										long idVale = pagamentoValeCompras.getCupomTroca().getId();
										long idValeSession = pagamentoValeComprasSession.getCupomTroca().getId();
										if(idVale == idValeSession) {
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
