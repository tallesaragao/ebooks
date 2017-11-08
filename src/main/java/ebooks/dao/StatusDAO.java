package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ebooks.modelo.Status;
import ebooks.modelo.EntidadeDominio;

public class StatusDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Status status = (Status) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into status(nome, dt_cadastro) values(?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, status.getNome());
			ps.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
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
		Status status = (Status) entidade;
		List<EntidadeDominio> consulta = consultar(status);
		if(consulta.isEmpty()) {
			return false;
		}
		Status cupomPromocionalOld = (Status) consulta.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update status set nome=? where id_status=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, status.getNome() != null ? status.getNome() : cupomPromocionalOld.getNome());
			ps.setLong(2, status.getId());
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
		Status status = (Status) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from status where id_status=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, status.getId());
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
		Status statusConsulta = (Status) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idStatusConsulta = statusConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idStatusConsulta != null) {
				sql = "select * from status where id_status=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, statusConsulta.getId());
			}
			else {
				sql = "select * from status where nome=?";
				
				ps = conexao.prepareStatement(sql);
				ps.setString(1, statusConsulta.getNome());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Status status = new Status();
				status.setId(rs.getLong("id_status"));
				status.setNome(rs.getString("nome"));
				status.setDataCadastro(rs.getDate("dt_cadastro"));
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
