package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Editora;
import ebooks.modelo.EntidadeDominio;

public class EditoraDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) {
		Editora editora = (Editora) entidade;
		conexao = factory.getConnection();
		try {
		String sql = "insert into pessoa(nome, dt_cadastro) values(?,?)";
		PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, editora.getNome());
		editora.setDataCadastro(editora.getDataCadastro());
		ps.setDate(2, new Date(editora.getDataCadastro().getTime()));
		ps.execute();
		ResultSet generatedKeys = ps.getGeneratedKeys();
		Long idPessoa = Long.valueOf(0);
		while(generatedKeys.next()) {
			idPessoa = generatedKeys.getLong(1);
		}
		ps.close();
		
		sql = "insert into pessoa_juridica(cnpj, razao_social, id_pessoa) value(?,?,?)";
		ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, editora.getCnpj());
		ps.setString(2, editora.getRazaoSocial());
		ps.setLong(3, idPessoa);
		ps.execute();
		generatedKeys = ps.getGeneratedKeys();
		Long idPessoaJuridica = Long.valueOf(0);
		while(generatedKeys.next()) {
			idPessoaJuridica = generatedKeys.getLong(1);
		}
		ps.close();
		
		sql = "insert into editora(id_pessoa_juridica) values(?)";
		ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, idPessoaJuridica);
		ps.execute();
		generatedKeys = ps.getGeneratedKeys();
		Long idEditora = Long.valueOf(0);
		while(generatedKeys.next()) {
			idEditora = generatedKeys.getLong(1);
		}
		ps.close();
		}
		catch (SQLException e) {
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
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) {
		Editora editoraOld = (Editora) consultar(entidade).get(0);
		conexao = factory.getConnection();
		Editora editora = (Editora) entidade;
		try {
			conexao.setAutoCommit(false);
			String sql = "select * from editora e "
					+ "join pessoa_juridica pj on (e.id_pessoa_juridica = pj.id_pessoa_juridica) "
					+ "join pessoa p on (pj.id_pessoa = p.id_pessoa) "
					+ " where id_editora = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			editora.setId(editoraOld.getId());
			ps.setLong(1, editora.getId());
			ResultSet rs = ps.executeQuery();
			Long idPessoaJuridicaEditora = Long.valueOf(0);
			Long idPessoaEditora = Long.valueOf(0);
			while(rs.next()) {
				idPessoaJuridicaEditora = rs.getLong("pj.id_pessoa_juridica");
				idPessoaEditora = rs.getLong("p.id_pessoa");
			}
			
			sql = "update pessoa set nome = ? where id_pessoa = ?";
			ps = conexao.prepareStatement(sql);
			ps.setString(1, editora.getNome());
			ps.setLong(2, idPessoaEditora);
			ps.execute();
			ps.close();
			
			sql = "update pessoa_juridica set cnpj = ?, razao_social = ? where id_pessoa_juridica = ?";
			ps = conexao.prepareStatement(sql);
			ps.setString(1, editora.getCnpj());
			ps.setString(2, editora.getRazaoSocial());
			ps.setLong(3, idPessoaJuridicaEditora);
			ps.execute();
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
		return true;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		Editora editoraConsulta = (Editora) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();	
		try {
			conexao = factory.getConnection();
			String sql = "select * from editora e "
					+ "join pessoa_juridica pj on (e.id_pessoa_juridica = pj.id_pessoa_juridica "
					+ "join pessoa p on (pj.id_pessoa = p.id_pessoa) "
					+ "where p.nome like ? ";
			if(editoraConsulta.getId() != null) {
				sql += "and e.id_editora = ?";
			}
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, "%%");
			if(editoraConsulta.getNome() != null) {
				ps.setString(1, "%" + editoraConsulta.getNome() + "%");
			}
			if(editoraConsulta.getId() != null) {
				ps.setLong(2, editoraConsulta.getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Editora editora = new Editora();
				editora.setId(rs.getLong("e.id_editora"));
				editora.setNome(rs.getString("p.nome"));
				editora.setDataCadastro(rs.getDate("p.dt_cadastro"));
				editora.setCnpj(rs.getString("pj.cnpj"));
				editora.setRazaoSocial(rs.getString("pj.razao_social"));
				consulta.add(editora);
			}
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
