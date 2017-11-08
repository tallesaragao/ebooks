package ebooks.negocio.impl;

import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.modelo.Status;
import ebooks.modelo.StatusPedido;
import ebooks.negocio.IStrategy;

public class VerificarPedidoFinalizado implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		StringBuilder sb = new StringBuilder();
		if(carrinho.isPedidoFinalizado()) {
			//ID 1 = Em processamento
			IStrategy strategy = new GerarNumeroPedido();
			strategy.processar(carrinho.getPedido());
			
			Status status = new Status();
			status.setId(Long.valueOf(1));
			status.setNome("Em processamento");
			StatusPedido statusPedido = new StatusPedido();
			statusPedido.setAtual(true);
			statusPedido.setStatus(status);
			List<StatusPedido> statusesPedido = new ArrayList<>();
			statusesPedido.add(statusPedido);
			pedido.setStatusesPedido(statusesPedido);
			
			strategy = new ComplementarDtCadastro();
			strategy.processar(statusPedido);
			strategy.processar(carrinho.getPedido());
			
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
