package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import ebooks.dao.ClienteDAO;
import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class VerificarExistenciaCliente implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		ClienteDAO dao = new ClienteDAO();
		Cliente clienteCad = (Cliente) entidade;
		StringBuilder sb = new StringBuilder();
		try {
			Cliente cliente = new Cliente();
			cliente.setCpf(clienteCad.getCpf());
			if(cliente.getCpf() != null && !cliente.getCpf().equals("")) {
				List<EntidadeDominio> consulta = dao.consultar(cliente);
				if(!consulta.isEmpty()) {
					sb.append("CPF já cadastrado:");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			sb.append("Erro na verificação de existência do cliente:");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}
}
