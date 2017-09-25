package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Autor;
import ebooks.modelo.Categoria;
import ebooks.modelo.Dimensoes;
import ebooks.modelo.Editora;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.GrupoPrecificacao;
import ebooks.modelo.Livro;
import ebooks.modelo.Precificacao;

public class LivroDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) {
		conexao = factory.getConnection();
		Livro livro = (Livro) entidade;
		List<Autor> autores = livro.getAutores();
		List<Categoria> categorias = livro.getCategorias();
		GrupoPrecificacao grupoPrecificacao = livro.getGrupoPrecificacao();
		Precificacao precificacao = livro.getPrecificacao();
		Dimensoes dimensoes = livro.getDimensoes();
		Editora editora = livro.getEditora();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into dimensoes(altura, largura, peso, profundidade) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, dimensoes.getAltura());
			ps.setDouble(2, dimensoes.getLargura());
			ps.setDouble(3, dimensoes.getPeso());
			ps.setDouble(4, dimensoes.getProfundidade());
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			Long idDimensoes = Long.valueOf(0);
			while(generatedKeys.next()) {
				idDimensoes = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into pessoa(nome, dt_cadastro) values(?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, editora.getNome());
			editora.setDataCadastro(livro.getDataCadastro());
			ps.setDate(2, new Date(editora.getDataCadastro().getTime()));
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
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
			
			sql = "insert into precificacao(preco_custo, preco_venda) values(?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, precificacao.getPrecoCusto());
			ps.setDouble(2, precificacao.getPrecoVenda());
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idPrecificacao = Long.valueOf(0);
			while(generatedKeys.next()) {
				idPrecificacao = generatedKeys.getLong(1);
			}
			ps.close();
			
			for(Autor autor : autores) {
				sql = "insert into pessoa(nome, dt_cadastro) values(?,?)";
				ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, autor.getNome());
				autor.setDataCadastro(livro.getDataCadastro());
				ps.setDate(2, new Date(autor.getDataCadastro().getTime()));
				ps.execute();
				generatedKeys = ps.getGeneratedKeys();
				idPessoa = Long.valueOf(0);
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
			}
			sql = "insert into livro(ano, titulo, edicao, isbn, num_paginas, fl_ativo, quantidade, codigo, id_dimensoes, "
					+ "id_grupo_precificacao, id_precificacao, id_editora, dt_cadastro) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, livro.getAno());
			ps.setString(2, livro.getTitulo());
			ps.setString(3, livro.getEdicao());
			ps.setString(4, livro.getIsbn());
			ps.setString(5, livro.getNumeroPaginas());
			ps.setBoolean(6, livro.getAtivo());
			ps.setLong(7, livro.getQuantidade());
			ps.setString(8, livro.getCodigo());
			ps.setLong(9, idDimensoes);
			ps.setLong(10, grupoPrecificacao.getId());
			ps.setLong(11, idPrecificacao);
			ps.setLong(12, idEditora);
			ps.setDate(13, new Date(livro.getDataCadastro().getTime()));
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idLivro = Long.valueOf(0);
			while(generatedKeys.next()) {
				idLivro = generatedKeys.getLong(1);
			}
			ps.close();
			
			for(Categoria categoria : categorias) {
				sql = "insert into livro_categoria(id_livro, id_categoria) values(?,?)";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, idLivro);
				ps.setLong(2, categoria.getId());
				ps.execute();
				ps.close();
			}
			
			for(Autor autor : autores) {
				sql = "insert into livro_autor(id_livro, id_autor) values(?,?)";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, idLivro);
				ps.setLong(2, autor.getId());
				ps.execute();
				ps.close();
			}
			conexao.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				conexao.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
		}
		finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) {
		return false;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) {
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		String sql = "select l.titulo, d.peso, pEd.nome, pjEd.cnpj, gp.nome from livro l"
					+ "join dimensoes d on (l.id_dimensoes = d.id_dimensoes)"
					+ "join editora ed on (l.id_editora = ed.id_editora)"
					+ "join pessoa_juridica pjEd on (ed.id_pessoa_juridica = pjEd.id_pessoa_juridica)"
				    + "join pessoa pEd on (pjEd.id_pessoa = pEd.id_pessoa)"
				    + "join grupo_precificacao gp on (l.id_grupo_precificacao = gp.id_grupo_precificacao)";
		return null;
	}
	
}
