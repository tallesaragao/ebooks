package ebooks.dao;

import java.sql.SQLException;
import java.util.List;

import ebooks.modelo.EntidadeDominio;

public interface IDAO {
	public boolean salvar(EntidadeDominio entidade) throws SQLException;

	public boolean alterar(EntidadeDominio entidade) throws SQLException;

	public boolean excluir(EntidadeDominio entidade) throws SQLException;

	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException;
}
