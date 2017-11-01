package ebooks.negocio.impl;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class GerarNumeroPedido implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		Integer hashCode = pedido.hashCode();
		String numero = hashCode.toString();
		for(int i=0; i<3; i++) {
			Long random = Math.round(Math.random() * 9);
			numero = numero + random.toString();
		}
		pedido.setNumero(numero);
		return null;
	}

}
