package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.EnderecoDAO;
import ebooks.dao.IDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class ValidarConsistenciaFrete implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		HttpSession session = carrinho.getSession();
		Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
		Endereco enderecoSession = pedidoSession.getEnderecoEntrega();
		List<ItemPedido> itensPedido = pedidoSession.getItensPedido();
		if(enderecoSession == null || itensPedido == null || itensPedido.isEmpty()) {
			pedidoSession.setEnderecoEntrega(null);
			pedidoSession.setFrete(null);
			session.setAttribute("pedido", pedidoSession);
		}
		else {
			IDAO dao = new EnderecoDAO();
			try {
				List<EntidadeDominio> consulta = dao.consultar(enderecoSession);
				if(consulta.isEmpty()) {
					pedidoSession.setEnderecoEntrega(null);
					pedidoSession.setFrete(null);
					session.setAttribute("pedido", pedidoSession);
				}
			} catch (SQLException e) {
				sb.append("Problema na consulta de endereço:");
			}
		}
		
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
