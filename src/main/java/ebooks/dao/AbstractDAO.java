package ebooks.dao;

import java.sql.Connection;

public abstract class AbstractDAO implements IDAO {

	protected Connection conexao;
	protected ConnectionFactory factory = new ConnectionFactory();
}
