package ebooks.negocio.impl;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class VerificarPedidoFinalizado implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		StringBuilder sb = new StringBuilder();
		//Se o pedido estiver finalizado, atribui o pedido à entidade para efetuar a persistência dos dados
		if(carrinho.isPedidoFinalizado()) {
			IStrategy strategy = new GerarNumeroPedido();
			strategy.processar(carrinho.getPedido());
			strategy = new ComplementarDtCadastro();
			strategy.processar(carrinho.getPedido());
			entidade = null;
		}
		//Se o pedido não estiver finalizado, evita chamar o DAO, passando um caracter usado para quebra de strings (:)
		else {
			sb.append(":");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}
}
