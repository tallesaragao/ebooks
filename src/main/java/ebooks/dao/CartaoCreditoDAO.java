package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Bandeira;
import ebooks.modelo.CartaoCredito;
import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.TipoEndereco;

public class CartaoCreditoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		CartaoCredito cartaoCredito = (CartaoCredito) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into cartao_credito(numero, nome_titular, dt_vencimento, "
					+ "codigo_seguranca, id_cliente, id_bandeira, dt_cadastro values(?,?,?,?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cartaoCredito.getNumero());
			ps.setString(2, cartaoCredito.getNomeTitular());
			ps.setDate(3, new Date(cartaoCredito.getDataVencimento().getTime()));
			ps.setString(4, cartaoCredito.getCodigoSeguranca());
			ps.setLong(5, cartaoCredito.getCliente().getId());
			ps.setLong(6, cartaoCredito.getBandeira().getId());
			ps.setDate(7, new Date(cartaoCredito.getDataCadastro().getTime()));
			ps.execute();
			ps.close();
			conexao.commit();
			return true;
		}
		catch(SQLException e) {
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();
		}
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) throws SQLException {
		CartaoCredito cartaoCredito = (CartaoCredito) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update cartao_credito set numero=?, nome_titular=?, dt_vencimento=?, "
					+ "codigo_seguranca=?, id_cliente=?, id_bandeira=? where id_cartao_credito=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cartaoCredito.getNumero());
			ps.setString(2, cartaoCredito.getNomeTitular());
			ps.setDate(3, new Date(cartaoCredito.getDataVencimento().getTime()));
			ps.setString(4, cartaoCredito.getCodigoSeguranca());
			ps.setLong(5, cartaoCredito.getCliente().getId());
			ps.setLong(6, cartaoCredito.getBandeira().getId());
			ps.setLong(7, cartaoCredito.getId());
			ps.execute();
			ps.close();
			conexao.commit();
			return true;
		}
		catch(SQLException e) {
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();			
		}
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		CartaoCredito cartaoCredito = (CartaoCredito) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from cartao_credito where id_cartao_credito=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, cartaoCredito.getId());
			ps.execute();
			ps.close();
			conexao.commit();
			conexao.close();
			return true;
		}
		catch(SQLException e) {
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();			
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		CartaoCredito cartaoCreditoConsulta = (CartaoCredito) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idCartaoCreditoConsulta = cartaoCreditoConsulta.getId();
			String sql = "select * from cartao_credito c"
					+ " join bandeira b on (c.id_bandeira = b.id_bandeira) "
					+ " where c.numero like ? and c.nome_titular like ? and c.codigo_seguranca like ? ";
			if(idCartaoCreditoConsulta != null) {
				sql += " and id_cartao_credito=? ";			
			}
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cartaoCreditoConsulta.getNumero());
			ps.setString(2, cartaoCreditoConsulta.getNomeTitular());
			ps.setString(3, cartaoCreditoConsulta.getCodigoSeguranca());
			if(idCartaoCreditoConsulta != null) {
				ps.setLong(4, cartaoCreditoConsulta.getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CartaoCredito cartaoCredito = new CartaoCredito();
				cartaoCredito.setId(rs.getLong("c.id_cartao_credito"));
				cartaoCredito.setNumero(rs.getString("c.numero"));
				cartaoCredito.setDataVencimento(rs.getDate("c.dt_vencimento"));
				cartaoCredito.setCodigoSeguranca(rs.getString("c.codigo_seguranca"));
				cartaoCredito.setDataCadastro(rs.getDate("c.dt_cadastro"));
				
				Cliente cliente = new Cliente();
				cliente.setId(rs.getLong("c.id_cliente"));
				cartaoCredito.setCliente(cliente);
				
				Bandeira bandeira = new Bandeira();
				bandeira.setId(rs.getLong("b.id_bandeira"));
				bandeira.setNome(rs.getString("b.nome"));
				bandeira.setDataCadastro(rs.getDate("b.dt_cadastro"));
				cartaoCredito.setBandeira(bandeira);
				
				consulta.add(cartaoCredito);
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
