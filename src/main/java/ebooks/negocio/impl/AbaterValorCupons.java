package ebooks.negocio.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ebooks.dao.CupomTrocaDAO;
import ebooks.dao.IDAO;
import ebooks.modelo.CupomTroca;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class AbaterValorCupons implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		IDAO dao;
		Pedido pedido = (Pedido) entidade;
		FormaPagamento formaPagamento = pedido.getFormaPagamento();
		List<Pagamento> pagamentos = formaPagamento.getPagamentos();
		for(Pagamento pagamento : pagamentos) {
			if(pagamento.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
				PagamentoValeCompras pagamentoValeCompras = (PagamentoValeCompras) pagamento;
				CupomTroca cupomTroca = pagamentoValeCompras.getCupomTroca();
				BigDecimal valorPago = pagamentoValeCompras.getValorPago();
				dao = new CupomTrocaDAO();
				try {
					List<EntidadeDominio> consulta = dao.consultar(cupomTroca);
					if(!consulta.isEmpty()) {
						cupomTroca = (CupomTroca) consulta.get(0);
						BigDecimal valor = cupomTroca.getValor();
						valor = valor.subtract(valorPago);
						cupomTroca.setAtivo(false);
						//Se houver algum pagamento com cupom que não utilizou todo o saldo, gera um novo cupom
						if(valorPago.doubleValue() < cupomTroca.getValor().doubleValue()) {
							CupomTroca cupomTrocaNovo = new CupomTroca();
							cupomTrocaNovo.setAtivo(true);
							cupomTrocaNovo.setCliente(pedido.getCliente());
							IStrategy strategy = new GerarCodigoCupom();
							strategy.processar(cupomTrocaNovo);
							cupomTrocaNovo.setValor(valor);
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							Date validade = Calendar.getInstance().getTime();
							try {
								validade = df.parse("01/01/2099");
							} catch (ParseException e) {
								e.printStackTrace();
							}
							cupomTrocaNovo.setValidade(validade);
							dao.salvar(cupomTrocaNovo);
						}
						cupomTroca.setAtivo(false);
						try {
							boolean alterado = dao.alterar(cupomTroca);
							if(!alterado) {
								sb.append("Problema na inativação do cupom:");
							}
						} catch (SQLException e) {
							e.printStackTrace();
							sb.append("Problema na transação SQL:");
						}
					}
					else {
						sb.append("Problema na consulta de cupom:");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
