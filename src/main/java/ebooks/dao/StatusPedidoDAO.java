package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.modelo.Status;
import ebooks.modelo.StatusPedido;

public class StatusPedidoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		StatusPedido statusPedido = (StatusPedido) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into status_pedido(atual, id_pedido, id_status, dt_cadastro) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setBoolean(1, statusPedido.getAtual());
			ps.setLong(2, statusPedido.getPedido().getId());
			ps.setLong(3, statusPedido.getStatus().getId());
			ps.setDate(4, new Date(statusPedido.getDataCadastro().getTime()));
			ps.execute();
			ps.close();
			if(statusPedido.getPedido().getNumero() == null || statusPedido.getPedido().getNumero().equals("")) {
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
			if(statusPedido.getPedido().getNumero() == null || statusPedido.getPedido().getNumero().equals("")) {
				conexao.close();
			}
		}
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) throws SQLException {
		StatusPedido statusPedido = (StatusPedido) entidade;
		List<EntidadeDominio> consulta = consultar(statusPedido);
		if(consulta.isEmpty()) {
			return false;
		}
		StatusPedido statusPedidoOld = (StatusPedido) consulta.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update status_pedido set atual=?, id_pedido=?, id_status=? where id_status_pedido=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setBoolean(1, statusPedido.getAtual() != null ? statusPedido.getAtual() : statusPedidoOld.getAtual());
			ps.setLong(2, statusPedido.getPedido() == null ? statusPedido.getPedido().getId() : statusPedidoOld.getPedido().getId());
			ps.setLong(3, statusPedido.getStatus() == null ? statusPedido.getStatus().getId() : statusPedidoOld.getStatus().getId());
			ps.setLong(4, statusPedido.getId());
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
			Long idStatusPedidoConsulta = statusPedidoConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idStatusPedidoConsulta != null) {
				sql = "select * from status_pedido where id_status_pedido=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, statusPedidoConsulta.getId());
			}
			else if(statusPedidoConsulta.getPedido() != null && statusPedidoConsulta.getPedido().getId() != null){
				sql = "select * from status_pedido where id_pedido=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, statusPedidoConsulta.getPedido().getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StatusPedido statusPedido = new StatusPedido();
				statusPedido.setId(rs.getLong("id_status_pedido"));
				statusPedido.setAtual(rs.getBoolean("atual"));
				Pedido pedido = new Pedido();
				pedido.setId(rs.getLong("id_pedido"));
				statusPedido.setPedido(pedido);
				Status status = new Status();
				status.setId(rs.getLong("id_status"));
				statusPedido.setStatus(status);
				statusPedido.setDataCadastro(rs.getDate("dt_cadastro"));
				consulta.add(statusPedido);
			}
			IDAO dao = new StatusDAO();
			for(EntidadeDominio ent : consulta) {
				StatusPedido statusPedido = (StatusPedido) ent;
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
