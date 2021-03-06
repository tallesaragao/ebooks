package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ebooks.dao.ClienteDAO;
import ebooks.dao.IDAO;
import ebooks.dao.LivroDAO;
import ebooks.modelo.Ativacao;
import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Livro;
import ebooks.negocio.IStrategy;

public class AtivadorCadastro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Map<String, IDAO> daos = new HashMap<>();
		daos.put(Cliente.class.getName(), new ClienteDAO());
		daos.put(Livro.class.getName(), new LivroDAO());
		Ativacao ativacao = (Ativacao) entidade;
		EntidadeDominio entidadeAtivacao = ativacao.getEntidade();
		IDAO dao = daos.get(entidadeAtivacao.getClass().getName());
		try {
			if(!dao.alterar(entidadeAtivacao)) {
				sb.append("Problema na ativação");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			sb.append("Problema na transação SQL");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
