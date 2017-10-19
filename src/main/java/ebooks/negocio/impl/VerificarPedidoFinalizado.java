package ebooks.negocio.impl;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class VerificarPedidoFinalizado implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		StringBuilder sb = new StringBuilder();
		//Se o pedido não estiver finalizado, evita chamar o DAO, passando um caracter usado para quebra de strings (:)
		if(!carrinho.isPedidoFinalizado()) {
			sb.append(":");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
