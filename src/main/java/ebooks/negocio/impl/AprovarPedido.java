package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import ebooks.dao.IDAO;
import ebooks.dao.PedidoDAO;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class AprovarPedido implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Pedido pedido = (Pedido) entidade;
		IDAO dao = new PedidoDAO();
		try {
			List<EntidadeDominio> consulta = dao.consultar(pedido);
			if(!consulta.isEmpty()) {
				pedido = (Pedido) consulta.get(0);
				//método temporário para aprovação do pedido
				long aprovacao = Math.round(Math.random() * 10);
				if(aprovacao < 1) {
					sb.append("Pedido reprovado:");
				}
			}
			else {
				sb.append("Pedido não encontrado:");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			sb.append("Problema na consulta:");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
