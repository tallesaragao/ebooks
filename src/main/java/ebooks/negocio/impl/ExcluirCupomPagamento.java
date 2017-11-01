package ebooks.negocio.impl;

import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.CupomPromocional;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class ExcluirCupomPagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		CupomPromocional cupomPromocional = pedido.getCupomPromocional();
		if(cupomPromocional != null) {
			HttpSession session = carrinho.getSession();
			Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
			CupomPromocional cupomPromocionalSession = pedidoSession.getCupomPromocional();
			if(cupomPromocional.getId().longValue() == cupomPromocionalSession.getId().longValue()) {
				pedidoSession.setCupomPromocional(null);
				session.setAttribute("pedido", pedidoSession);
			}
			else {
				sb.append("Não foi possível remover o cupom");
			}
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
