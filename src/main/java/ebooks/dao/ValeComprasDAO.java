package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.ValeCompras;
import ebooks.modelo.EntidadeDominio;

public class ValeComprasDAO extends AbstractDAO {
	
	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		ValeCompras valeCompras = (ValeCompras) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into vale_compras(codigo, valor, validade, ativo) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, valeCompras.getCodigo());
			ps.setBigDecimal(2, valeCompras.getValor());
			ps.setDate(3, new Date(valeCompras.getValidade().getTime()));
			ps.setBoolean(4, valeCompras.getAtivo());
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
		ValeCompras valeCompras = (ValeCompras) entidade;
		List<EntidadeDominio> consulta = consultar(valeCompras);
		if(consulta.isEmpty()) {
			return false;
		}
		ValeCompras valeComprasOld = (ValeCompras) consulta.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update vale_compras set codigo=?, valor=?, validade=?, ativo=? where id_vale_compras=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, valeCompras.getCodigo() != null ? valeCompras.getCodigo() : valeComprasOld.getCodigo());
			ps.setBigDecimal(2, valeCompras.getValor() != null ?
					valeCompras.getValor() : valeComprasOld.getValor());
			ps.setDate(3, valeCompras.getValidade() != null ? 
					new Date(valeCompras.getValidade().getTime()) : new Date(valeComprasOld.getValidade().getTime()));
			ps.setBoolean(4, valeCompras.getAtivo() != null ? valeCompras.getAtivo() : valeComprasOld.getAtivo());
			ps.setLong(5, valeCompras.getId());
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
		ValeCompras valeCompras = (ValeCompras) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from vale_compras where id_vale_compras=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, valeCompras.getId());
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
		ValeCompras valeComprasConsulta = (ValeCompras) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idValeComprasConsulta = valeComprasConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idValeComprasConsulta != null) {
				sql = "select * from vale_compras where id_vale_compras=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, valeComprasConsulta.getId());
			}
			else {
				sql = "select * from vale_compras where codigo=?";
				
				ps = conexao.prepareStatement(sql);
				ps.setString(1, valeComprasConsulta.getCodigo());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ValeCompras valeCompras = new ValeCompras();
				valeCompras.setId(rs.getLong("id_vale_compras"));
				valeCompras.setCodigo(rs.getString("codigo"));
				valeCompras.setValor(rs.getBigDecimal("valor"));
				valeCompras.setAtivo(rs.getBoolean("ativo"));
				consulta.add(valeCompras);
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
