package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Cliente;
import ebooks.modelo.CupomTroca;
import ebooks.modelo.EntidadeDominio;

public class CupomTrocaDAO extends AbstractDAO {
	
	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		CupomTroca cupomTroca = (CupomTroca) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into cupom_troca(codigo, valor, validade, ativo, id_cliente) values(?,?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cupomTroca.getCodigo());
			ps.setBigDecimal(2, cupomTroca.getValor());
			ps.setDate(3, new Date(cupomTroca.getValidade().getTime()));
			ps.setBoolean(4, cupomTroca.getAtivo());
			ps.setLong(5, cupomTroca.getCliente().getId());
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			while (generatedKeys.next()) {
				entidade.setId(generatedKeys.getLong(1));
			}
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
	public boolean alterar(EntidadeDominio entidade) throws SQLException {
		CupomTroca cupomTroca = (CupomTroca) entidade;
		List<EntidadeDominio> consulta = consultar(cupomTroca);
		if(consulta.isEmpty()) {
			return false;
		}
		CupomTroca cupomTrocaOld = (CupomTroca) consulta.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update cupom_troca set codigo=?, valor=?, validade=?, ativo=?, id_cliente=? where id_cupom_troca=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cupomTroca.getCodigo() != null ? cupomTroca.getCodigo() : cupomTrocaOld.getCodigo());
			ps.setBigDecimal(2, cupomTroca.getValor() != null ?
					cupomTroca.getValor() : cupomTrocaOld.getValor());
			ps.setDate(3, cupomTroca.getValidade() != null ? 
					new Date(cupomTroca.getValidade().getTime()) : new Date(cupomTrocaOld.getValidade().getTime()));
			ps.setBoolean(4, cupomTroca.getAtivo() != null ? cupomTroca.getAtivo() : cupomTrocaOld.getAtivo());
			ps.setLong(5, cupomTroca.getCliente() != null ? cupomTroca.getCliente().getId() : cupomTrocaOld.getCliente().getId());
			ps.setLong(6, cupomTroca.getId());
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
		CupomTroca cupomTroca = (CupomTroca) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from cupom_troca where id_cupom_troca=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, cupomTroca.getId());
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
		CupomTroca cupomTrocaConsulta = (CupomTroca) entidade;
		Cliente clienteConsulta = cupomTrocaConsulta.getCliente();
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idCupomTrocaConsulta = cupomTrocaConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idCupomTrocaConsulta != null) {
				sql = "select * from cupom_troca where id_cupom_troca=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, cupomTrocaConsulta.getId());
			}
			else if(clienteConsulta != null && clienteConsulta.getId() != null) {
				sql = "select * from cupom_troca where id_cliente=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, clienteConsulta.getId());
			}
			else {
				sql = "select * from cupom_troca where codigo=?";
				
				ps = conexao.prepareStatement(sql);
				ps.setString(1, cupomTrocaConsulta.getCodigo());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CupomTroca cupomTroca = new CupomTroca();
				cupomTroca.setId(rs.getLong("id_cupom_troca"));
				cupomTroca.setCodigo(rs.getString("codigo"));
				cupomTroca.setValor(rs.getBigDecimal("valor"));
				cupomTroca.setAtivo(rs.getBoolean("ativo"));
				cupomTroca.setValidade(rs.getDate("validade"));
				Cliente cliente = new Cliente();
				cliente.setId(rs.getLong("id_cliente"));
				cupomTroca.setCliente(cliente);
				consulta.add(cupomTroca);
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
