package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.IDAO;
import ebooks.dao.CupomTrocaDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.Pedido;
import ebooks.modelo.CupomTroca;
import ebooks.negocio.IStrategy;

public class AdicionarValeComprasPagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		if(!carrinho.isPedidoFinalizado()) {
			Pedido pedido = carrinho.getPedido();
			FormaPagamento formaPagamento = pedido.getFormaPagamento();
			if(formaPagamento != null) {
				List<Pagamento> pagamentos = formaPagamento.getPagamentos();
				if(pagamentos != null && !pagamentos.isEmpty()) {
					for(Pagamento pagamento : pagamentos) {
						if(pagamento.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
							PagamentoValeCompras pagValeCompras = (PagamentoValeCompras) pagamento;
							CupomTroca cupomTrocaConsulta = pagValeCompras.getCupomTroca();
							if(cupomTrocaConsulta != null) {
								HttpSession session = carrinho.getSession();
								Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
								IDAO dao = new CupomTrocaDAO();
								try {
									List<EntidadeDominio> consulta = dao.consultar(cupomTrocaConsulta);
									if(!consulta.isEmpty()) {
										CupomTroca cupomTroca = (CupomTroca) consulta.get(0);
										if(cupomTroca.getAtivo()) {
											FormaPagamento formaPagamentoSession = pedidoSession.getFormaPagamento();
											if(formaPagamentoSession == null) {
												formaPagamentoSession = new FormaPagamento();
												formaPagamentoSession.setPagamentos(new ArrayList<Pagamento>());
											}
											List<Pagamento> pagamentosSession = formaPagamentoSession.getPagamentos();
											PagamentoValeCompras pagamentoValeCompras = new PagamentoValeCompras();
											pagamentoValeCompras.setCupomTroca(cupomTroca);
											boolean valeComprasIgual = false;
											for(Pagamento pagamentoSession : pagamentosSession) {
												if(pagamentoSession.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
													PagamentoValeCompras pagamentoValeComprasSession = (PagamentoValeCompras) pagamentoSession;
													String codigoValeComprasSession = pagamentoValeComprasSession.getCupomTroca().getCodigo();
													String codigoValeCompras = pagamentoValeCompras.getCupomTroca().getCodigo();
													if(codigoValeCompras.toUpperCase().equals(codigoValeComprasSession.toUpperCase())) {
														valeComprasIgual = true;
														break;
													}
												}
											}
											if(!valeComprasIgual) {
												Iterator<Pagamento> iterator = pagamentosSession.iterator();
												while(iterator.hasNext()) {
													Pagamento pagamentoSession = iterator.next();
													if(pagamentoSession.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
														iterator.remove();
													}
												}
												pagamentosSession.add(pagamentoValeCompras);
												formaPagamentoSession.setPagamentos(pagamentosSession);
												pedidoSession.setFormaPagamento(formaPagamentoSession);
												session.setAttribute("pedido", pedidoSession);
											}
										}
										else {
											sb.append("O vale-compras está inativo:");
										}
										session.setAttribute("pedido", pedidoSession);	
									}
									else {
										sb.append("Código de vale-compras inválido:");
									}
								} catch (SQLException e) {
									e.printStackTrace();
									sb.append("Problema na consulta do vale-compras:");
								}
							}
						}
					}
				}
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
