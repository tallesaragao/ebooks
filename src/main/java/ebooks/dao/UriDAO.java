package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Uri;

public class UriDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Uri uri = (Uri) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into uri(caminho) values(?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, uri.getCaminho());
			ps.execute();
			ps.close();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();
		}
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) throws SQLException {
		Uri uri = (Uri) entidade;
		List<EntidadeDominio> consulta = consultar(uri);
		if(consulta.isEmpty()) {
			return false;
		}
		Uri uriOld = (Uri) consulta.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update uri set caminho=? where id_uri=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, uri.getCaminho() != null ? uri.getCaminho() : uriOld.getCaminho());
			ps.setLong(2, uri.getId());
			ps.execute();
			ps.close();
			conexao.commit();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();
		}
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		Uri uri = (Uri) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from uri where id_uri=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, uri.getId());
			ps.execute();
			ps.close();
			conexao.commit();
			conexao.close();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		Uri uri = (Uri) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idUri = uri.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idUri != null) {
				sql = "select * from uri where id_uri=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, uri.getId());
			}
			else {
				sql = "select * from uri";	
				ps = conexao.prepareStatement(sql);
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Uri status = new Uri();
				status.setId(rs.getLong("id_uri"));
				status.setCaminho(rs.getString("caminho"));
				consulta.add(status);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}
		return consulta;
	}

}
