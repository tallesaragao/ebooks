package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;

public class ClienteDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Cliente cliente = (Cliente) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into pessoa (nome, dt_cadastro) values(?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getNome());
			ps.setDate(2, new Date(cliente.getDataCadastro().getTime()));
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			Long idPessoa = Long.valueOf(0);
			while(generatedKeys.next()) {
				idPessoa = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into pessoa_fisica(cpf, dt_nascimento, id_pessoa) values(?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getCpf());
			ps.setDate(2, new Date(cliente.getDataNascimento().getTime()));
			ps.setLong(3, idPessoa);
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idPessoaFisica = Long.valueOf(0);
			while(generatedKeys.next()) {
				idPessoaFisica = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into telefone(ddd, numero, id_tipo_telefone) values(?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getTelefone().getDdd());
			ps.setString(2, cliente.getTelefone().getNumero());
			ps.setLong(3, cliente.getTelefone().getTipoTelefone().getId());
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idTelefone = Long.valueOf(0);
			while(generatedKeys.next()) {
				idTelefone = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into cliente (email, fl_ativo, genero, id_pessoa_fisica, id_login, id_telefone) values(?,?,?,?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getEmail());
			ps.setBoolean(2, cliente.isAtivo());
			ps.setString(3, String.valueOf(cliente.getGenero()));
			ps.setLong(4, idPessoaFisica);
			ps.setLong(5, cliente.getLogin().getId());
			ps.setLong(6, idTelefone);
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			while(generatedKeys.next()) {
				cliente.setId(generatedKeys.getLong(1));
			}
			ps.close();
			conexao.commit();
			Endereco endereco = cliente.getEnderecos().get(0);
			endereco.setPessoa(cliente);
			EnderecoDAO endDAO = new EnderecoDAO();
			endDAO.salvar(endereco);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
