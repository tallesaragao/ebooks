package ebooks.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionFactory {
	private DataSource dataSource;
	private static Connection conexao;
	
	public ConnectionFactory() {
		try {
			Context initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/ebooks_les");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Connection getConnection() {
		try {
			if(conexao == null || conexao.isClosed()) {
				conexao = dataSource.getConnection();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conexao;
	}
}
