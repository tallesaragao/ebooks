package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ebooks.modelo.StatusPedido;
import ebooks.modelo.EntidadeDominio;

public class StatusPedidoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		StatusPedido statusPedido = (StatusPedido) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into status_pedido(nome, dt_cadastro) values(?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, statusPedido.getNome());
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
		StatusPedido statusPedido = (StatusPedido) entidade;
		List<EntidadeDominio> consulta = consultar(statusPedido);
		if(consulta.isEmpty()) {
			return false;
		}
		StatusPedido cupomPromocionalOld = (StatusPedido) consulta.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update status_pedido set nome=? where id_status_pedido=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, statusPedido.getNome() != null ? statusPedido.getNome() : cupomPromocionalOld.getNome());
			ps.setLong(2, statusPedido.getId());
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
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		StatusPedido statusPedido = (StatusPedido) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from status_pedido where id_status_pedido=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, statusPedido.getId());
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
		StatusPedido statusPedidoConsulta = (StatusPedido) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idCupomPromocionalConsulta = statusPedidoConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idCupomPromocionalConsulta != null) {
				sql = "select * from status_pedido where id_status_pedido=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, statusPedidoConsulta.getId());
			}
			else {
				sql = "select * from status_pedido where nome=?";
				
				ps = conexao.prepareStatement(sql);
				ps.setString(1, statusPedidoConsulta.getNome());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StatusPedido statusPedido = new StatusPedido();
				statusPedido.setId(rs.getLong("id_status_pedido"));
				statusPedido.setNome(rs.getString("nome"));
				statusPedido.setDataCadastro(rs.getDate("dt_cadastro"));
				consulta.add(statusPedido);
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
