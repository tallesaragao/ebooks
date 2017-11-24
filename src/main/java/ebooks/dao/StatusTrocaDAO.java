package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Status;
import ebooks.modelo.StatusTroca;
import ebooks.modelo.Troca;

public class StatusTrocaDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		StatusTroca statusTroca = (StatusTroca) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into status_troca(atual, id_troca, id_status, dt_cadastro) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setBoolean(1, statusTroca.getAtual());
			ps.setLong(2, statusTroca.getTroca().getId());
			ps.setLong(3, statusTroca.getStatus().getId());
			ps.setDate(4, new Date(statusTroca.getDataCadastro().getTime()));
			ps.execute();
			ps.close();
			if(statusTroca.getStatus().getNome().equals("Trocado")) {
				conexao.commit();
			}
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			conexao.rollback();
			return false;
		}
		finally {
			/*
			if(statusTroca.getPedido().getNumero() == null || statusTroca.getPedido().getNumero().equals("")) {
				conexao.close();
			}
			*/
		}
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) throws SQLException {
		StatusTroca statusTroca = (StatusTroca) entidade;
		List<EntidadeDominio> consulta = consultar(statusTroca);
		if(consulta.isEmpty()) {
			return false;
		}
		StatusTroca statusTrocaOld = (StatusTroca) consulta.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update status_troca set atual=?, id_troca=?, id_status=? where id_status_troca=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setBoolean(1, statusTroca.getAtual() != null ? statusTroca.getAtual() : statusTrocaOld.getAtual());
			ps.setLong(2, statusTroca.getTroca() == null ? statusTroca.getTroca().getId() : statusTrocaOld.getTroca().getId());
			ps.setLong(3, statusTroca.getStatus() == null ? statusTroca.getStatus().getId() : statusTrocaOld.getStatus().getId());
			ps.setLong(4, statusTroca.getId());
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
		StatusTroca statusTroca = (StatusTroca) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from status_troca where id_status_troca=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, statusTroca.getId());
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
		StatusTroca statusTrocaConsulta = (StatusTroca) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idStatusTrocaConsulta = statusTrocaConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idStatusTrocaConsulta != null) {
				sql = "select * from status_troca where id_status_troca=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, statusTrocaConsulta.getId());
			}
			else if(statusTrocaConsulta.getTroca() != null && statusTrocaConsulta.getTroca().getId() != null){
				sql = "select * from status_troca where id_troca=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, statusTrocaConsulta.getTroca().getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StatusTroca statusTroca = new StatusTroca();
				statusTroca.setId(rs.getLong("id_status_troca"));
				statusTroca.setAtual(rs.getBoolean("atual"));
				Troca troca = new Troca();
				troca.setId(rs.getLong("id_troca"));
				statusTroca.setTroca(troca);
				Status status = new Status();
				status.setId(rs.getLong("id_status"));
				statusTroca.setStatus(status);
				statusTroca.setDataCadastro(rs.getDate("dt_cadastro"));
				consulta.add(statusTroca);
			}
			IDAO dao = new StatusDAO();
			for(EntidadeDominio ent : consulta) {
				StatusTroca statusPedido = (StatusTroca) ent;
				Status status = statusPedido.getStatus();
				List<EntidadeDominio> consultaEntidades = dao.consultar(status);
				status = (Status) consultaEntidades.get(0);
				statusPedido.setStatus(status);
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
