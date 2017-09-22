package ebooks.controle.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.dao.CategoriaDAO;
import ebooks.modelo.Autor;
import ebooks.modelo.Categoria;
import ebooks.modelo.Dimensoes;
import ebooks.modelo.Editora;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.GrupoPrecificacao;
import ebooks.modelo.Livro;

public class LivroVH implements IViewHelper {
	
	private static final String SALVAR = "SALVAR";
	private static final String CONSULTAR = "CONSULTAR";
	private static final String ALTERAR = "ALTERAR";
	private static final String EXCLUIR = "EXCLUIR";

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Livro livro = new Livro();
		String operacao = request.getParameter("operacao");
		if(operacao == null) {
			operacao = "";
		}
		if(operacao.equals(SALVAR)) {
			String titulo = request.getParameter("titulo");
			String ano = request.getParameter("ano");
			String edicao = request.getParameter("edicao");
			String numeroPaginas = request.getParameter("numeroPaginas");
			String grupoPrecificacaoId = request.getParameter("grupoPrecificacao");
			String isbn = request.getParameter("isbn");
			String quantidade = request.getParameter("quantidade");
			String altura = request.getParameter("altura");
			String largura = request.getParameter("largura");
			String profundidade = request.getParameter("profundidade");
			String peso = request.getParameter("peso");
			String[] idsCategorias = request.getParameterValues("categorias");
			String sinopse = request.getParameter("sinopse");
			String nomeAutor = request.getParameter("nomeAutor");
			String dataNascimentoString = request.getParameter("dataNascimento");
			String cpf = request.getParameter("cpf");
			String nomeEditora = request.getParameter("nomeEditora");
			String cnpj = request.getParameter("cnpj");
			String razaoSocial = request.getParameter("razaoSocial");
			
			livro.setTitulo(titulo);
			livro.setAno(ano);
			livro.setEdicao(edicao);
			livro.setNumeroPaginas(numeroPaginas);
			
			//Se nenhum valor for selecionado, seta o id do grupoPrecificacao como zero
			GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();
			grupoPrecificacao.setId(Long.valueOf(0));
			if(grupoPrecificacaoId != null && !grupoPrecificacaoId.equals("")) {
				grupoPrecificacao.setId(Long.valueOf(grupoPrecificacaoId));				
			}
			
			livro.setGrupoPrecificacao(grupoPrecificacao);
			livro.setIsbn(isbn);
			
			//Se nenhuma quantidade for informada, seta o valor como zero
			livro.setQuantidade(Long.valueOf(0));
			if(quantidade != null && !quantidade.equals("")) {
				livro.setQuantidade(Long.valueOf(quantidade));				
			}
			
			Dimensoes dimensoes = new Dimensoes();
			//Se nenhuma altura for informada, seta o valor como zero
			dimensoes.setAltura(Double.valueOf(0));
			if(altura != null && !altura.equals("")) {
				dimensoes.setAltura(Double.valueOf(altura));				
			}
			//Se nenhuma altura for informada, seta o valor como zero
			dimensoes.setLargura(Double.valueOf(0));
			if(largura != null && !largura.equals("")) {
				dimensoes.setLargura(Double.valueOf(largura));				
			}
			//Se nenhuma profundidade for informada, seta o valor como zero
			dimensoes.setProfundidade(Double.valueOf(0));
			if(profundidade != null && !profundidade.equals("")) {
				dimensoes.setProfundidade(Double.valueOf(profundidade));				
			}
			//Se nenhum peso for informado, seta o valor como zero
			dimensoes.setPeso(Double.valueOf(0));
			if(peso != null && !peso.equals("")) {
				dimensoes.setPeso(Double.valueOf(peso));				
			}
			
			//Criando as categorias com base nos IDs selecionados
			List<Categoria> categorias = new ArrayList<Categoria>();
			for(String idCat : idsCategorias) {
				Categoria categoria = new Categoria();
				categoria.setId(Long.valueOf(idCat));
				categorias.add(categoria);
			}
			
			livro.setCategorias(categorias);
			livro.setSinopse(sinopse);

			Autor autor = new Autor();
			autor.setNome(nomeAutor);
			
			//Evitando exceção ao converter a data
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dataNascimento = null;
			try {
				dataNascimento = sdf.parse(dataNascimentoString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			autor.setDataNascimento(dataNascimento);
			autor.setCpf(cpf);
			List<Autor> autores = new ArrayList<Autor>();
			autores.add(autor);
			livro.setAutores(autores);
			
			Editora editora = new Editora();
			editora.setNome(nomeEditora);
			editora.setCnpj(cnpj);
			editora.setRazaoSocial(razaoSocial);
			livro.setEditora(editora);
			
		}
		return (EntidadeDominio) livro;
	}

	@Override
	public void setView(Object obj, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/livroForm")) {
			//Consulta as categorias para serem disponibilizadas no formulário
			CategoriaDAO catDAO = new CategoriaDAO();
			Categoria categoria = new Categoria();
			categoria.setNome("");
			List<EntidadeDominio> categorias = catDAO.consultar(categoria);
			request.setAttribute("categorias", categorias);
			request.getRequestDispatcher("WEB-INF/jsp/livro/form.jsp").forward(request, response);
		}
	}

}
