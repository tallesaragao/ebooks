package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.CupomPromocionalDAO;
import ebooks.dao.IDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.CupomPromocional;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class AdicionarCupomPromocionalPagamento implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		CupomPromocional cupomPromocionalConsulta = pedido.getCupomPromocional();
		if(cupomPromocionalConsulta != null) {
			HttpSession session = carrinho.getSession();
			Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
			pedidoSession.setCupomPromocional(null);
			IDAO dao = new CupomPromocionalDAO();
			try {
				List<EntidadeDominio> consulta = dao.consultar(cupomPromocionalConsulta);
				if(!consulta.isEmpty()) {
					CupomPromocional cupomPromocional = (CupomPromocional) consulta.get(0);
					if(cupomPromocional.getAtivo()) {
						pedidoSession.setCupomPromocional(cupomPromocional);					
					}
					else {
						sb.append("O cupom está inativo:");
					}
					session.setAttribute("pedido", pedidoSession);	
				}
				else {
					sb.append("Cupom inválido:");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				sb.append("Problema na consulta do cupom:");
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
