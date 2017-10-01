package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Autor;
import ebooks.modelo.EntidadeDominio;

public class AutorDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) {
		Autor autor = (Autor) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into pessoa(nome, dt_cadastro) values(?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, autor.getNome());
			ps.setDate(2, new Date(autor.getDataCadastro().getTime()));
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			Long idPessoa = Long.valueOf(0);
			while(generatedKeys.next()) {
				idPessoa = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into pessoa_fisica(cpf, dt_nascimento, id_pessoa) value(?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, autor.getCpf());
			ps.setDate(2, new Date(autor.getDataNascimento().getTime()));
			ps.setLong(3, idPessoa);
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idPessoaFisica = Long.valueOf(0);
			while(generatedKeys.next()) {
				idPessoaFisica = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into autor(id_pessoa_fisica) values(?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setLong(1, idPessoaFisica);
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idAutor = Long.valueOf(0);
			while(generatedKeys.next()) {
				idAutor = generatedKeys.getLong(1);
			}
			autor.setId(idAutor);
			ps.close();
			conexao.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
			try {
				conexao.rollback();
				return false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
		}
		finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) {
		Autor autor = (Autor) entidade;
		try {
			String sql = "select * from autor a "
					+ "join pessoa_fisica pf on (a.id_pessoa_fisica = pf.id_pessoa_fisica) "
					+ "join pessoa p on (pf.id_pessoa = p.id_pessoa) "
					+ "where id_autor = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, autor.getId());
			ResultSet rs = ps.executeQuery();
			Long idPessoaAutor = Long.valueOf(0);
			Long idPessoaFisicaAutor = Long.valueOf(0);;
			while(rs.next()) {
				idPessoaAutor = rs.getLong("p.id_pessoa");
				idPessoaFisicaAutor = rs.getLong("pf.id_pessoa_fisica");
			}
			ps.close();
			
			sql = "update pessoa set nome =? where id_pessoa = ?";
			ps = conexao.prepareStatement(sql);
			ps.setString(1, autor.getNome());
			ps.setLong(2, idPessoaAutor);
			ps.execute();
			ps.close();
			
			sql = "update pessoa_fisica set cpf = ?, dt_nascimento = ? where id_pessoa_fisica = ?";
			ps = conexao.prepareStatement(sql);
			ps.setString(1, autor.getCpf());
			ps.setDate(2, new Date(autor.getDataNascimento().getTime()));
			ps.setLong(3, idPessoaFisicaAutor);
			ps.execute();
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		List<EntidadeDominio> consulta = new ArrayList<>();
		Autor autorConsulta = (Autor) entidade;
		conexao = factory.getConnection();
		String sql = "select * from autor a "
				+ "join pessoa_fisica pfa on (a.id_pessoa_fisica = pfa.id_pessoa_fisica) "
				+ "join pessoa pa on (pfa.id_pessoa = pa.id_pessoa) "
				+ "where pa.nome like ? ";
		if(autorConsulta.getId() != null) {
			sql += "and a.id_autor = ?";
		}
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, "%%");
			if(autorConsulta.getNome() != null) {
				ps.setString(1, "%" + autorConsulta.getNome() +"%");
			}
			if(autorConsulta.getId() != null) {
				ps.setLong(2, autorConsulta.getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Autor autor = new Autor();
				autor.setId(rs.getLong("a.id_autor"));
				autor.setCpf(rs.getString("pfa.cpf"));
				autor.setDataNascimento(rs.getDate("pfa.dt_nascimento"));
				autor.setDataCadastro(rs.getDate("pa.dt_cadastro"));
				autor.setNome(rs.getString("pa.nome"));
				consulta.add(autor);
			}
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return consulta;
	}

}
