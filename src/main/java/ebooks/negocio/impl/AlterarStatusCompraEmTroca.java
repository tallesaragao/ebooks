package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import ebooks.dao.IDAO;
import ebooks.dao.StatusDAO;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.modelo.Status;
import ebooks.modelo.StatusPedido;
import ebooks.modelo.Troca;
import ebooks.negocio.IStrategy;

public class AlterarStatusCompraEmTroca implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Troca troca = (Troca) entidade;
		if(troca.getCompraToda()) {
			Pedido pedido = troca.getPedido();
			IDAO dao = new StatusDAO();
			Status status = new Status();
			status.setNome("Em troca");
			try {
				List<EntidadeDominio> consulta = dao.consultar(status);
				if(!consulta.isEmpty()) {
					status = (Status) consulta.get(0);
					StatusPedido statusPedido = new StatusPedido();
					statusPedido.setAtual(true);
					statusPedido.setPedido(pedido);
					statusPedido.setStatus(status);
					IStrategy strategy = new AlterarStatusAtualPedido();
					String retorno = strategy.processar(statusPedido);
					if(retorno != null && retorno.length() > 0) {
						sb.append(retorno);
					}
				}
			} catch (SQLException e) {
				sb.append("Problema na consulta de status:");
				e.printStackTrace();
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
