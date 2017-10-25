package ebooks.negocio.impl;

import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class ExcluirCupomPagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		HttpSession session = carrinho.getSession();
		Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
		pedidoSession.setCupomPromocional(null);
		session.setAttribute("pedido", pedidoSession);
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
