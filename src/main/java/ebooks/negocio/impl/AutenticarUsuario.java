package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import ebooks.dao.LoginDAO;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;
import ebooks.negocio.IStrategy;

public class AutenticarUsuario implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Login login = (Login) entidade;
		LoginDAO dao = new LoginDAO();
		try {
			List<EntidadeDominio> consulta = dao.consultar(login);
			if(consulta.isEmpty()) {
				sb.append("Usuário e/ou senha incorretos:");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			sb.append("Problema na consulta de login:");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
