package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.CartaoCredito;
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
					+ " estado, pais, fl_principal, dt_cadastro, id_tipo_endereco, excluido) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, endereco.getIdentificacao());
			ps.setString(2, endereco.getLogradouro());
			ps.setString(3, endereco.getNumero());
			ps.setString(4, endereco.getComplemento());
			ps.setString(5, endereco.getBairro());
			ps.setString(6, endereco.getCep());
			ps.setString(7, endereco.getCidade());
			ps.setString(8, endereco.getEstado());
			ps.setString(9, endereco.getPais());
			ps.setBoolean(10, endereco.getPrincipal());
			ps.setDate(11, new Date(endereco.getDataCadastro().getTime()));
			ps.setLong(12, endereco.getTipoEndereco().getId());
			ps.setBoolean(13, endereco.getExcluido());
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			while(generatedKeys.next()) {
				endereco.setId(generatedKeys.getLong(1));
			}
			ps.close();
			
			sql = "insert into cliente_endereco(id_cliente, id_endereco) values(?,?)";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, endereco.getPessoa().getId());
			ps.setLong(2, endereco.getId());
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
		Endereco enderecoOld = new Endereco();
		List<EntidadeDominio> consulta = consultar(endereco);
		if(!consulta.isEmpty()) {
			enderecoOld = (Endereco) consulta.get(0);
		}
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update endereco set identificacao=?, logradouro=?, numero=?, complemento=?, bairro=?, cep=?, cidade=?,"
					+ " estado=?, pais=?, fl_principal=?, id_tipo_endereco=?, excluido=? where id_endereco=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, endereco.getIdentificacao() != null ? endereco.getIdentificacao() : enderecoOld.getIdentificacao());
			ps.setString(2, endereco.getLogradouro() != null ? endereco.getLogradouro() : enderecoOld.getLogradouro());
			ps.setString(3, endereco.getNumero() != null ? endereco.getNumero() : enderecoOld.getNumero());
			ps.setString(4, endereco.getComplemento() != null ? endereco.getComplemento() : enderecoOld.getComplemento());
			ps.setString(5, endereco.getBairro() != null ? endereco.getBairro() : enderecoOld.getBairro());
			ps.setString(6, endereco.getCep() != null ? endereco.getCep() : enderecoOld.getCep());
			ps.setString(7, endereco.getCidade() != null ? endereco.getCidade() : enderecoOld.getCidade());
			ps.setString(8, endereco.getEstado() != null ? endereco.getEstado() : enderecoOld.getEstado());
			ps.setString(9, endereco.getPais() != null ? endereco.getPais() : enderecoOld.getPais());
			ps.setBoolean(10, endereco.getPrincipal() != null ? endereco.getPrincipal() : enderecoOld.getPrincipal());
			ps.setLong(11, endereco.getTipoEndereco() != null ?
					endereco.getTipoEndereco().getId() : enderecoOld.getTipoEndereco().getId());
			ps.setBoolean(12, endereco.getExcluido() != null ? endereco.getExcluido() : enderecoOld.getExcluido());
			ps.setLong(13, endereco.getId());
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
		try {
			endereco.setExcluido(true);
			boolean alterar = alterar(endereco);
			return alterar;
		}
		catch(SQLException e) {
			return false;
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		Endereco enderecoConsulta = (Endereco) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		PreparedStatement ps = null;
		try {
			if(enderecoConsulta.getPessoa() != null && enderecoConsulta.getPessoa().getId() != null) {
				String sql = "select * from endereco e "
							+ " join cliente_endereco ce on(e.id_endereco = ce.id_endereco)"
							+ " where ce.id_cliente=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, enderecoConsulta.getPessoa().getId());
			}
			else if(enderecoConsulta.getId() != null) {
				String sql = "select * from endereco e "
						+ " join cliente_endereco ce on(e.id_endereco = ce.id_endereco)"
						+ " where e.id_endereco=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, enderecoConsulta.getId());
			}
			else {
				Long idEnderecoConsulta = enderecoConsulta.getId();
				String sql = "select * from endereco e"
						+ " join tipo_endereco te on (e.id_tipo_endereco = te.id_tipo_endereco) "
						+ " where e.identificacao like ? and e.logradouro like ? and e.complemento like ? and e.bairro like ? "
						+ " and e.cep like ? and e.cidade like ? and e.estado like ? and e.pais like ? ";
				if(idEnderecoConsulta != null) {
					sql += " and e.id_endereco=? ";				
				}
				ps = conexao.prepareStatement(sql);
				ps.setString(1, "%%");
				if(enderecoConsulta.getIdentificacao() != null) {
					ps.setString(1, "%" + enderecoConsulta.getIdentificacao() + "%");
				}
				ps.setString(2, "%%");
				if(enderecoConsulta.getLogradouro() != null) {
					ps.setString(1, "%" + enderecoConsulta.getLogradouro() + "%");
				}
				ps.setString(3, "%%");
				if(enderecoConsulta.getComplemento() != null) {
					ps.setString(3, "%" + enderecoConsulta.getComplemento() + "%");
				}
				ps.setString(4, "%%");
				if(enderecoConsulta.getBairro() != null) {
					ps.setString(4, "%" + enderecoConsulta.getBairro() + "%");
				}
				ps.setString(5, "%%");
				if(enderecoConsulta.getCep() != null) {
					ps.setString(5, "%" + enderecoConsulta.getCep() + "%");
				}
				ps.setString(6, "%%");
				if(enderecoConsulta.getCidade() != null) {
					ps.setString(6, "%" + enderecoConsulta.getCidade() + "%");
				}
				ps.setString(7, "%%");
				if(enderecoConsulta.getEstado() != null) {
					ps.setString(7, "%" + enderecoConsulta.getEstado() + "%");
				}
				ps.setString(8, "%%");
				if(enderecoConsulta.getPais() != null) {
					ps.setString(8, "%" + enderecoConsulta.getPais() + "%");
				}
				if(idEnderecoConsulta != null) {
					ps.setLong(9, enderecoConsulta.getId());
				}
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Endereco endereco = new Endereco();
				endereco.setId(rs.getLong("e.id_endereco"));
				endereco.setIdentificacao(rs.getString("e.identificacao"));
				endereco.setLogradouro(rs.getString("e.logradouro"));
				endereco.setNumero(rs.getString("e.numero"));
				endereco.setComplemento(rs.getString("e.complemento"));
				endereco.setBairro(rs.getString("e.bairro"));
				endereco.setCep(rs.getString("e.cep"));
				endereco.setCidade(rs.getString("e.cidade"));
				endereco.setEstado(rs.getString("e.estado"));
				endereco.setPais(rs.getString("e.pais"));
				endereco.setDataCadastro(rs.getDate("e.dt_cadastro"));
				endereco.setPrincipal(rs.getBoolean("e.fl_principal"));
				endereco.setExcluido(rs.getBoolean("e.excluido"));
				
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
