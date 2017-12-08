package ebooks.negocio.impl;

import javax.servlet.http.HttpSession;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class RetornarNumeroPedido implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		String numero = pedido.getNumero();
		Pedido pedidoSession = carrinho.getPedidoSession();
		pedidoSession.setNumero(numero);
		carrinho.setPedidoSession(pedidoSession);
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
