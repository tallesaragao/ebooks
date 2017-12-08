package ebooks.negocio.impl;

import java.math.BigDecimal;
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
		Pedido pedidoSession = carrinho.getPedidoSession();
		if(pedidoSession != null) {
			List<ItemPedido> itensPedido = pedidoSession.getItensPedido();
			Frete frete = pedidoSession.getFrete();
			CupomPromocional cupomPromocional = pedidoSession.getCupomPromocional();
			BigDecimal valorTotal = new BigDecimal("0.0");
			if(itensPedido != null) {
				for(ItemPedido item : itensPedido) {
					valorTotal = valorTotal.add(item.getSubtotal());
				}
			}
			if(frete != null) {
				valorTotal = valorTotal.add(frete.getValor());
			}
			if(cupomPromocional != null) {
				BigDecimal porcentagemDesconto = cupomPromocional.getPorcentagemDesconto();
				BigDecimal valorDesconto = new BigDecimal("0.0");
				valorDesconto = valorDesconto.add(valorTotal);
				valorDesconto = valorDesconto.multiply(porcentagemDesconto);
				valorDesconto = valorDesconto.divide(new BigDecimal("100"));
				valorDesconto = valorDesconto.setScale(2, BigDecimal.ROUND_CEILING);
				valorTotal = valorTotal.subtract(valorDesconto);
				valorTotal = valorTotal.setScale(2, BigDecimal.ROUND_CEILING);
			}
			pedidoSession.setValorTotal(valorTotal);
			carrinho.setPedidoSession(pedidoSession);
		}
				
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
