package ebooks.negocio.impl;

import java.util.List;

import ebooks.modelo.CupomTroca;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class VerificarCuponsAtivos implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Pedido pedido = (Pedido) entidade;
		FormaPagamento formaPagamento = pedido.getFormaPagamento();
		List<Pagamento> pagamentos = formaPagamento.getPagamentos();
		for(Pagamento pagamento : pagamentos) {
			if(pagamento.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
				PagamentoValeCompras p = (PagamentoValeCompras) pagamento;
				CupomTroca cupomTroca = p.getCupomTroca();
				if(cupomTroca.getAtivo() != true) {
					sb.append("Cupom inativo:");
				}
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
