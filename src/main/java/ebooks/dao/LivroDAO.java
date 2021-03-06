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
import ebooks.modelo.Estoque;
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
		Estoque estoque = livro.getEstoque();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into dimensoes(altura, largura, peso, profundidade) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setBigDecimal(1, dimensoes.getAltura());
			ps.setBigDecimal(2, dimensoes.getLargura());
			ps.setBigDecimal(3, dimensoes.getPeso());
			ps.setBigDecimal(4, dimensoes.getProfundidade());
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			Long idDimensoes = Long.valueOf(0);
			while(generatedKeys.next()) {
				idDimensoes = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into estoque(quant_min, quant_max, quant_atual, quant_reserva) values(?,?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setLong(1, estoque.getQuantidadeMinima());
			ps.setLong(2, estoque.getQuantidadeMaxima());
			ps.setLong(3, estoque.getQuantidadeAtual());
			ps.setLong(4, estoque.getQuantidadeReservada());
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idEstoque = Long.valueOf(0);
			while(generatedKeys.next()) {
				idEstoque = generatedKeys.getLong(1);
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
			ps.setBigDecimal(1, precificacao.getPrecoCusto());
			ps.setBigDecimal(2, precificacao.getPrecoVenda());
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
			sql = "insert into livro(ano, titulo, edicao, isbn, num_paginas, fl_ativo, codigo, sinopse, id_dimensoes, "
					+ "id_grupo_precificacao, id_precificacao, id_editora, id_estoque, dt_cadastro) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, livro.getAno());
			ps.setString(2, livro.getTitulo());
			ps.setString(3, livro.getEdicao());
			ps.setString(4, livro.getIsbn());
			ps.setString(5, livro.getNumeroPaginas());
			ps.setBoolean(6, livro.getAtivo());
			ps.setString(7, livro.getCodigo());
			ps.setString(8, livro.getSinopse());
			ps.setLong(9, idDimensoes);
			ps.setLong(10, grupoPrecificacao.getId());
			ps.setLong(11, idPrecificacao);
			ps.setLong(12, idEditora);
			ps.setLong(13, idEstoque);
			ps.setDate(14, new Date(livro.getDataCadastro().getTime()));
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
		Livro livro = (Livro) entidade;
		List<EntidadeDominio> listaLivroOld = consultar(livro);
		Livro livroOld = (Livro) listaLivroOld.get(0);
		List<Autor> autores = livro.getAutores();
		if(autores == null) {
			autores = livroOld.getAutores();
		}
		List<Categoria> categorias = livro.getCategorias();
		if(categorias == null) {
			categorias = livroOld.getCategorias();
		}
		GrupoPrecificacao grupoPrecificacao = livro.getGrupoPrecificacao();
		if(grupoPrecificacao == null) {
			grupoPrecificacao = livroOld.getGrupoPrecificacao();
		}
		Precificacao precificacao = livro.getPrecificacao();
		if(precificacao == null) {
			precificacao = livroOld.getPrecificacao();
		}
		Dimensoes dimensoes = livro.getDimensoes();
		if(dimensoes == null) {
			dimensoes = livroOld.getDimensoes();
		}
		Editora editora = livro.getEditora();
		if(editora == null) {
			editora = livroOld.getEditora();
		}
		Estoque estoque = livro.getEstoque();
		if(estoque == null) {
			estoque = livroOld.getEstoque();
		}
		try {
			conexao = factory.getConnection();
			conexao.setAutoCommit(false);
			String sql = "update dimensoes d set altura = ?, largura = ?, peso = ?, profundidade = ? where d.id_dimensoes = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			dimensoes.setId(livroOld.getDimensoes().getId());
			ps.setBigDecimal(1, dimensoes.getAltura());
			ps.setBigDecimal(2, dimensoes.getLargura());
			ps.setBigDecimal(3, dimensoes.getPeso());
			ps.setBigDecimal(4, dimensoes.getProfundidade());
			ps.setLong(5, dimensoes.getId());
			ps.execute();
			ps.close();
			
			sql = "update estoque e set quant_min = ?, quant_max = ?, quant_atual = ?, quant_reserva = ? where e.id_estoque = ?";
			ps = conexao.prepareStatement(sql);
			estoque.setId(livroOld.getEstoque().getId());
			ps.setLong(1, estoque.getQuantidadeMinima());
			ps.setLong(2, estoque.getQuantidadeMaxima());
			ps.setLong(3, estoque.getQuantidadeAtual());
			ps.setLong(4, estoque.getQuantidadeReservada());
			ps.setLong(5, estoque.getId());
			ps.execute();
			ps.close();
			
			sql = "select * from editora e "
					+ "join pessoa_juridica pj on (e.id_pessoa_juridica = pj.id_pessoa_juridica) "
					+ "join pessoa p on (pj.id_pessoa = p.id_pessoa) "
					+ " where id_editora = ?";
			ps = conexao.prepareStatement(sql);
			editora.setId(livroOld.getEditora().getId());
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
			
			sql = "update precificacao set preco_custo = ?, preco_venda = ? where id_precificacao = ?";
			ps = conexao.prepareStatement(sql);
			precificacao.setId(livroOld.getPrecificacao().getId());
			ps.setBigDecimal(1, precificacao.getPrecoCusto());
			ps.setBigDecimal(2, precificacao.getPrecoVenda());
			ps.setLong(3, precificacao.getId());
			ps.execute();
			ps.close();
			
			for(Autor autor : autores) {
				for(Autor autorOld : livroOld.getAutores()) {
					if(autor.getCpf().equals(autorOld.getCpf())) {
						autor.setId(autorOld.getId());
					}
				}
				
				sql = "select * from autor a "
						+ "join pessoa_fisica pf on (a.id_pessoa_fisica = pf.id_pessoa_fisica) "
						+ "join pessoa p on (pf.id_pessoa = p.id_pessoa) "
						+ "where id_autor = ?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, autor.getId());
				rs = ps.executeQuery();
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
			
			sql = "update livro set ano = ?, titulo = ?, edicao = ?, isbn = ?, num_paginas = ?, sinopse = ?, "
					+ "id_dimensoes = ?, id_grupo_precificacao = ?, id_precificacao = ?, id_editora = ? where id_livro = ?";
			ps = conexao.prepareStatement(sql);
			ps.setString(1, livro.getAno());
			ps.setString(2, livro.getTitulo());
			ps.setString(3, livro.getEdicao());
			ps.setString(4, livro.getIsbn());
			ps.setString(5, livro.getNumeroPaginas());
			ps.setString(6, livro.getSinopse());
			ps.setLong(7, dimensoes.getId());
			ps.setLong(8, grupoPrecificacao.getId());
			ps.setLong(9, precificacao.getId());
			ps.setLong(10, editora.getId());
			ps.setLong(11, livro.getId());
			ps.execute();
			ps.close();
			
			sql = "delete from livro_categoria where id_livro = ?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, livro.getId());
			ps.execute();
			ps.close();
			
			for(Categoria categoria : categorias) {
				sql = "insert into livro_categoria(id_livro, id_categoria) values(?,?)";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, livro.getId());
				ps.setLong(2, categoria.getId());
				ps.execute();
				ps.close();
			}
			
			sql = "delete from livro_autor where id_livro = ?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, livro.getId());
			ps.execute();
			ps.close();
			
			for(Autor autor : autores) {
				sql = "insert into livro_autor(id_livro, id_autor) values(?,?)";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, livro.getId());
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
	public boolean excluir(EntidadeDominio entidade) {
		Livro livro = (Livro) consultar(entidade).get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from livro_autor where id_livro = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, livro.getId());
			ps.execute();
			ps.close();
			
			sql = "delete from livro_categoria where id_livro = ?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, livro.getId());
			ps.execute();
			ps.close();
			
			sql = "delete from livro where id_livro = ?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, livro.getId());
			ps.execute();
			ps.close();

			sql = "delete from precificacao where id_precificacao = ?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, livro.getPrecificacao().getId());
			ps.execute();
			ps.close();
			
			sql = "delete from dimensoes where id_dimensoes = ?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, livro.getDimensoes().getId());
			ps.execute();
			ps.close();
			
			sql = "delete from estoque where id_estoque = ?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, livro.getEstoque().getId());
			ps.execute();
			ps.close();

			conexao.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			return false;
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
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		Livro livroConsulta = (Livro) entidade;
		Editora editoraConsulta = livroConsulta.getEditora();
		if(editoraConsulta == null) {
			editoraConsulta = new Editora();
			editoraConsulta.setNome("");
		}
		Categoria categoriaConsulta = new Categoria();
		categoriaConsulta.setNome("");
		if(livroConsulta.getCategorias() != null && !livroConsulta.getCategorias().isEmpty()) {
			categoriaConsulta = livroConsulta.getCategorias().get(0);
		}
		Autor autorConsulta = new Autor();
		autorConsulta.setNome("");
		if(livroConsulta.getAutores() != null && !livroConsulta.getAutores().isEmpty()) {
			autorConsulta = livroConsulta.getAutores().get(0);
		}
		List<Autor> autores = new ArrayList<>();
		List<Categoria> categorias = new ArrayList<>();
		List<Livro> livros = new ArrayList<>();
		List<EntidadeDominio> resultado = new ArrayList<>();
		conexao = factory.getConnection();
		String sql = "select * from autor a "
				+ "join pessoa_fisica pfa on (a.id_pessoa_fisica = pfa.id_pessoa_fisica) "
				+ "join pessoa pa on (pfa.id_pessoa = pa.id_pessoa) "
				+ "where pa.nome like ?";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, "%" + autorConsulta.getNome() +"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Autor autor = new Autor();
				autor.setId(rs.getLong("a.id_autor"));
				autor.setCpf(rs.getString("pfa.cpf"));
				autor.setDataNascimento(rs.getDate("pfa.dt_nascimento"));
				autor.setDataCadastro(rs.getDate("pa.dt_cadastro"));
				autor.setNome(rs.getString("pa.nome"));
				autores.add(autor);
			}
			ps.close();
			
			if(conexao.isClosed()) {
				conexao = factory.getConnection();
			}
			sql = "select * from categoria c where c.nome like ? ";
			ps = conexao.prepareStatement(sql);
			if(categoriaConsulta.getNome() == null) {
				categoriaConsulta.setNome("");
			}
			ps.setString(1, "%" + categoriaConsulta.getNome() + "%");
			rs = ps.executeQuery();
			while(rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setId(rs.getLong("c.id_categoria"));
				categoria.setNome(rs.getString("c.nome"));
				categoria.setDataCadastro(rs.getDate("c.dt_cadastro"));
				categorias.add(categoria);
			}
			ps.close();

			sql = "select * from livro l "
				+ " join dimensoes d on (l.id_dimensoes = d.id_dimensoes)"
				+ " join editora ed on (l.id_editora = ed.id_editora)"
				+ " join pessoa_juridica pjEd on (ed.id_pessoa_juridica = pjEd.id_pessoa_juridica)"
				+ " join pessoa pEd on (pjEd.id_pessoa = pEd.id_pessoa)"
				+ " join grupo_precificacao gp on (l.id_grupo_precificacao = gp.id_grupo_precificacao)"
				+ " join precificacao p on (l.id_precificacao = p.id_precificacao)"
				+ " join estoque e on (l.id_estoque = e.id_estoque)"
				+ " where l.titulo like ? and l.codigo like ? and l.isbn like ? and pEd.nome like ?";
			if(livroConsulta.getId() != null) {
				sql += " and l.id_livro = ?";
			}
			ps = conexao.prepareStatement(sql);
			ps.setString(1, "%" + livroConsulta.getTitulo() + "%");
			ps.setString(2, "%" + livroConsulta.getCodigo() + "%");
			ps.setString(3, "%" + livroConsulta.getIsbn() + "%");
			ps.setString(4, "%" + editoraConsulta.getNome() + "%");
			if(livroConsulta.getId() != null) {
				ps.setString(1, "%%");
				ps.setString(2, "%%");
				ps.setString(3, "%%");
				ps.setString(4, "%%");
				ps.setLong(5, livroConsulta.getId());
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				Livro livro = new Livro();
				livro.setId(rs.getLong("l.id_livro"));
				livro.setAno(rs.getString("l.ano"));
				livro.setAtivo(rs.getBoolean("l.fl_ativo"));
				livro.setCodigo(rs.getString("l.codigo"));
				livro.setDataCadastro(rs.getDate("l.dt_cadastro"));
				livro.setEdicao(rs.getString("l.edicao"));
				livro.setIsbn(rs.getString("l.isbn"));
				livro.setNumeroPaginas(rs.getString("l.num_paginas"));
				livro.setSinopse(rs.getString("l.sinopse"));
				livro.setTitulo(rs.getString("l.titulo"));
				
				Dimensoes dimensoes = new Dimensoes();
				dimensoes.setId(rs.getLong("d.id_dimensoes"));
				dimensoes.setAltura(rs.getBigDecimal("d.altura"));
				dimensoes.setLargura(rs.getBigDecimal("d.largura"));
				dimensoes.setProfundidade(rs.getBigDecimal("d.profundidade"));
				dimensoes.setPeso(rs.getBigDecimal("d.peso"));
				livro.setDimensoes(dimensoes);
				
				Estoque estoque = new Estoque();
				estoque.setId(rs.getLong("e.id_estoque"));
				estoque.setQuantidadeMinima(rs.getLong("e.quant_min"));
				estoque.setQuantidadeMaxima(rs.getLong("e.quant_max"));
				estoque.setQuantidadeAtual(rs.getLong("e.quant_atual"));
				estoque.setQuantidadeReservada(rs.getLong("e.quant_reserva"));
				livro.setEstoque(estoque);
				
				Precificacao precificacao = new Precificacao();
				precificacao.setId(rs.getLong("p.id_precificacao"));
				precificacao.setPrecoCusto(rs.getBigDecimal("p.preco_custo"));
				precificacao.setPrecoVenda(rs.getBigDecimal("p.preco_venda"));
				livro.setPrecificacao(precificacao);
				
				GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();
				grupoPrecificacao.setId(rs.getLong("gp.id_grupo_precificacao"));
				grupoPrecificacao.setMargemLucro(rs.getBigDecimal("gp.margem_lucro"));
				grupoPrecificacao.setNome(rs.getString("gp.nome"));
				livro.setGrupoPrecificacao(grupoPrecificacao);
				
				Editora editora = new Editora();
				editora.setId(rs.getLong("ed.id_editora"));
				editora.setNome(rs.getString("pEd.nome"));
				editora.setDataCadastro(rs.getDate("pEd.dt_cadastro"));
				editora.setCnpj(rs.getString("pjEd.cnpj"));
				editora.setRazaoSocial(rs.getString("pjEd.razao_social"));
				livro.setEditora(editora);
				
				List<Categoria> categoriasInitialize = new ArrayList<>();
				livro.setCategorias(categoriasInitialize);
				
				List<Autor> autoresInitialize = new ArrayList<>();
				livro.setAutores(autoresInitialize);
				
				livros.add(livro);
			}
			ps.close();
			
			sql = "select * from livro_autor";
			ps = conexao.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				long idLivro = rs.getLong("id_livro");
				long idAutor = rs.getLong("id_autor");
				for(Livro livro : livros) {
					if(livro.getId() == idLivro) {
						for(Autor autor : autores) {
							if(autor.getId() == idAutor) {
								livro.getAutores().add(autor);
							}
						}
					}
				}				
			}
			ps.close();
			
			sql = "select * from livro_categoria";
			ps = conexao.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				long idLivro = rs.getLong("id_livro");
				long idCategoria = rs.getLong("id_categoria");
				for(Livro livro : livros) {
					if(livro.getId() == idLivro) {
						for(Categoria categoria : categorias) {
							if(categoria.getId() == idCategoria) {
								livro.getCategorias().add(categoria);
							}
						}
					}
				}				
			}
			ps.close();
			
			for(Livro livro : livros) {
				if(!livro.getAutores().isEmpty() && !livro.getCategorias().isEmpty()) {
					resultado.add(livro);					
				}
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}
	
}
