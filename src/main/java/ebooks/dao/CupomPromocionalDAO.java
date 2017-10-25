package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Cliente;
import ebooks.modelo.CupomPromocional;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.CupomPromocional;

public class CupomPromocionalDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		CupomPromocional cupomPromocional = (CupomPromocional) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into cupom_promo(codigo, porcent_desc, validade, ativo) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cupomPromocional.getCodigo());
			ps.setDouble(2, cupomPromocional.getPorcentagemDesconto());
			ps.setDate(3, new Date(cupomPromocional.getValidade().getTime()));
			ps.setBoolean(4, cupomPromocional.getAtivo());
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
		CupomPromocional cupomPromocional = (CupomPromocional) entidade;
		List<EntidadeDominio> consulta = consultar(cupomPromocional);
		if(consulta.isEmpty()) {
			return false;
		}
		CupomPromocional cupomPromocionalOld = (CupomPromocional) consulta.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update cupom_promo set codigo=?, porcent_desc=?, validade=?, ativo=? where id_cupom_promo=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cupomPromocional.getCodigo() != null ? cupomPromocional.getCodigo() : cupomPromocionalOld.getCodigo());
			ps.setDouble(2, cupomPromocional.getPorcentagemDesconto() != null ?
					cupomPromocional.getPorcentagemDesconto() : cupomPromocionalOld.getPorcentagemDesconto());
			ps.setDate(3, cupomPromocional.getValidade() != null ? 
					new Date(cupomPromocional.getValidade().getTime()) : new Date(cupomPromocionalOld.getValidade().getTime()));
			ps.setBoolean(4, cupomPromocional.getAtivo() != null ? cupomPromocional.getAtivo() : cupomPromocionalOld.getAtivo());
			ps.setLong(5, cupomPromocional.getId());
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
		CupomPromocional cupomPromocional = (CupomPromocional) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from cupom_promo where id_cupom_promo=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, cupomPromocional.getId());
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
		CupomPromocional cupomPromocionalConsulta = (CupomPromocional) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idCupomPromocionalConsulta = cupomPromocionalConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idCupomPromocionalConsulta != null) {
				sql = "select * from cupom_promo where id_cupom_promo=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, cupomPromocionalConsulta.getId());
			}
			else {
				sql = "select * from cupom_promo where codigo=?";
				
				ps = conexao.prepareStatement(sql);
				ps.setString(1, cupomPromocionalConsulta.getCodigo());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CupomPromocional cupomPromocional = new CupomPromocional();
				cupomPromocional.setId(rs.getLong("id_cupom_promo"));
				cupomPromocional.setCodigo(rs.getString("codigo"));
				cupomPromocional.setPorcentagemDesconto(rs.getDouble("porcent_desc"));
				cupomPromocional.setAtivo(rs.getBoolean("ativo"));
				consulta.add(cupomPromocional);
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
