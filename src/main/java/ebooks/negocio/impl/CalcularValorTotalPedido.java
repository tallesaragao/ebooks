package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.EnderecoDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.CupomPromocional;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Frete;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class CalcularValorTotalPedido implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		HttpSession session = carrinho.getSession();
		Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
		if(pedidoSession != null) {
			List<ItemPedido> itensPedido = pedidoSession.getItensPedido();
			Frete frete = pedidoSession.getFrete();
			CupomPromocional cupomPromocional = pedidoSession.getCupomPromocional();
			double valorTotal = 0.0;
			if(itensPedido != null) {
				for(ItemPedido item : itensPedido) {
					valorTotal += item.getSubtotal();
				}
			}
			if(frete != null) {
				valorTotal += frete.getValor();
			}
			if(cupomPromocional != null) {
				Double porcentagemDesconto = cupomPromocional.getPorcentagemDesconto();
				valorTotal = valorTotal - (valorTotal * porcentagemDesconto / 100);
			}
			pedidoSession.setValorTotal(valorTotal);
			session.setAttribute("pedido", pedidoSession);
		}
				
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
