package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.TipoEndereco;

public class EnderecoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Endereco endereco = (Endereco) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into endereco(identificacao, logradouro, numero, complemento, bairro, cep, cidade,"
					+ " estado, pais, fl_principal, dt_cadastro, id_tipo_endereco) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, endereco.getIdentificacao());
			ps.setString(2, endereco.getLogradouro());
			ps.setString(3, endereco.getNumero());
			ps.setString(4, endereco.getComplemento());
			ps.setString(5, endereco.getBairro());
			ps.setString(6, endereco.getCep());
			ps.setString(7, endereco.getCidade());
			ps.setString(8, endereco.getEstado());
			ps.setString(9, endereco.getPais());
			ps.setBoolean(9, endereco.isPrincipal());
			ps.setDate(10, new Date(endereco.getDataCadastro().getTime()));
			ps.setLong(11, endereco.getTipoEndereco().getId());
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
		Endereco endereco = (Endereco) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update endereco set identificacao=?, logradouro=?, numero=?, complemento=?, bairro=?, cep=?, cidade=?,"
					+ " estado=?, pais=?, fl_principal=?, id_tipo_endereco=?) values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, endereco.getIdentificacao());
			ps.setString(2, endereco.getLogradouro());
			ps.setString(3, endereco.getNumero());
			ps.setString(4, endereco.getComplemento());
			ps.setString(5, endereco.getBairro());
			ps.setString(6, endereco.getCep());
			ps.setString(7, endereco.getCidade());
			ps.setString(8, endereco.getEstado());
			ps.setString(9, endereco.getPais());
			ps.setBoolean(9, endereco.isPrincipal());
			ps.setLong(10, endereco.getTipoEndereco().getId());
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
		Endereco endereco = (Endereco) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from endereco where id_endereco=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, endereco.getId());
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
		Endereco enderecoConsulta = (Endereco) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idEnderecoConsulta = enderecoConsulta.getId();
			String sql = "select * from endereco e"
					+ " join tipo_endereco te on (e.id_tipo_endereco = te.id_tipo_endereco) "
					+ " where identificacao like ? and logradouro like ? and complemento like ? and bairro = ? "
					+ " and cep like ? and cidade like ? and estado like ? and pais like ? ";
			if(idEnderecoConsulta != null) {
				sql += " and id_endereco=? ";				
			}
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, enderecoConsulta.getIdentificacao());
			ps.setString(2, enderecoConsulta.getLogradouro());
			ps.setString(3, enderecoConsulta.getComplemento());
			ps.setString(4, enderecoConsulta.getBairro());
			ps.setString(5, enderecoConsulta.getCep());
			ps.setString(6, enderecoConsulta.getCidade());
			ps.setString(7, enderecoConsulta.getEstado());
			ps.setString(8, enderecoConsulta.getPais());
			if(idEnderecoConsulta != null) {
				ps.setLong(9, enderecoConsulta.getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Endereco endereco = new Endereco();
				endereco.setId(rs.getLong("e.id_endereco"));
				endereco.setIdentificacao(rs.getString("e.identificacao"));
				endereco.setLogradouro(rs.getString("e.logradouro"));
				endereco.setComplemento(rs.getString("e.complemento"));
				endereco.setBairro(rs.getString("e.bairro"));
				endereco.setCep(rs.getString("e.cep"));
				endereco.setCidade(rs.getString("e.cidade"));
				endereco.setEstado(rs.getString("e.estado"));
				endereco.setPais(rs.getString("e.pais"));
				endereco.setDataCadastro(rs.getDate("e.dt_cadastro"));
				endereco.setPrincipal(rs.getBoolean("e.fl_principal"));
				
				TipoEndereco tipoEndereco = new TipoEndereco();
				tipoEndereco.setId(rs.getLong("te.id_tipo_endereco"));
				tipoEndereco.setNome(rs.getString("te.nome"));
				tipoEndereco.setDataCadastro(rs.getDate("te.dt_cadastro"));
				
				endereco.setTipoEndereco(tipoEndereco);
				consulta.add(endereco);
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