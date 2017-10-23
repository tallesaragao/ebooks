package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.ClienteDAO;
import ebooks.dao.IDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class ConsultarClienteCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		Cliente clienteConsulta = pedido.getCliente();
		IDAO clienteDAO = new ClienteDAO();
		if(clienteConsulta != null) {
			try {
				List<EntidadeDominio> consulta = clienteDAO.consultar(clienteConsulta);
				if(!consulta.isEmpty()) {
					Cliente cliente = (Cliente) consulta.get(0);
					pedido.setCliente(cliente);
					HttpSession session = carrinho.getSession();
					session.setAttribute("pedido", pedido);
				}
				else {
					sb.append("Cliente não encontrado");
				}
			} catch (SQLException e) {
				sb.append("Problema na consulta");
				e.printStackTrace();
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
