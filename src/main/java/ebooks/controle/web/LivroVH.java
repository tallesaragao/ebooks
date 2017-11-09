package ebooks.controle.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.dao.CategoriaDAO;
import ebooks.dao.GrupoPrecificacaoDAO;
import ebooks.modelo.Autor;
import ebooks.modelo.Categoria;
import ebooks.modelo.Dimensoes;
import ebooks.modelo.Editora;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Estoque;
import ebooks.modelo.GrupoPrecificacao;
import ebooks.modelo.Livro;
import ebooks.modelo.Precificacao;

public class LivroVH implements IViewHelper {

	private static final String SALVAR = "SALVAR";
	private static final String CONSULTAR = "CONSULTAR";
	private static final String ALTERAR = "ALTERAR";
	private static final String EXCLUIR = "EXCLUIR";

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Livro livro = new Livro();
		String operacao = request.getParameter("operacao");
		if (operacao == null) {
			operacao = "";
		}
		if (operacao.equals(EXCLUIR)) {
			String id = request.getParameter("id");
			livro.setId(Long.valueOf(id));
			List<Categoria> categorias = new ArrayList<>();
			Categoria categoria = new Categoria();
			categoria.setNome("");
			categorias.add(categoria);
			livro.setCategorias(categorias);
			List<Autor> autores = new ArrayList<>();
			Autor autor = new Autor();
			autor.setNome("");
			autores.add(autor);
			livro.setAutores(autores);
			Editora editora = new Editora();
			editora.setNome("");
			livro.setEditora(editora);
		}
		if (operacao.equals(CONSULTAR)) {
			String id = request.getParameter("id");
			String titulo = request.getParameter("titulo");
			String codigo = request.getParameter("codigo");
			String isbn = request.getParameter("isbn");
			String categoriaNome = request.getParameter("categoria");
			String autorNome = request.getParameter("autor");
			String editoraNome = request.getParameter("editora");

			if (id != null && !id.equals("")) {
				livro.setId(Long.valueOf(id));
			}

			livro.setTitulo("");
			if (titulo != null) {
				livro.setTitulo(titulo);
			}
			livro.setCodigo("");
			if (codigo != null) {
				livro.setCodigo(codigo);
			}
			livro.setIsbn("");
			if (isbn != null) {
				livro.setIsbn(isbn);
			}
			List<Categoria> categorias = new ArrayList<>();
			Categoria categoria = new Categoria();
			categoria.setNome("");
			if (categoriaNome != null) {
				categoria.setNome(categoriaNome);
			}
			categorias.add(categoria);
			livro.setCategorias(categorias);

			List<Autor> autores = new ArrayList<>();
			Autor autor = new Autor();
			autor.setNome("");
			if (autorNome != null) {
				autor.setNome(autorNome);
			}
			autores.add(autor);
			livro.setAutores(autores);

			Editora editora = new Editora();
			editora.setNome("");
			if (editoraNome != null) {
				editora.setNome(editoraNome);
			}
			livro.setEditora(editora);

		}
		if (operacao.equals(SALVAR) || operacao.equals(ALTERAR)) {
			if (operacao.equals(ALTERAR)) {
				String id = request.getParameter("id");
				livro.setId(Long.valueOf(id));
			}
			String titulo = request.getParameter("titulo");
			String ano = request.getParameter("ano");
			String edicao = request.getParameter("edicao");
			String numeroPaginas = request.getParameter("numeroPaginas");
			String grupoPrecificacaoId = request.getParameter("grupoPrecificacao");
			String isbn = request.getParameter("isbn");
			String precoCusto = request.getParameter("precoCusto");
			String quantMin = request.getParameter("quantMin");
			String quantMax = request.getParameter("quantMax");
			String quantAtual = request.getParameter("quantAtual");
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

			// Se nenhum valor for selecionado, seta o id do grupoPrecificacao
			// como zero
			GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();
			grupoPrecificacao.setId(Long.valueOf(0));
			if (grupoPrecificacaoId != null && !grupoPrecificacaoId.equals("")) {
				grupoPrecificacao.setId(Long.valueOf(grupoPrecificacaoId));
			}

			livro.setGrupoPrecificacao(grupoPrecificacao);
			livro.setIsbn(isbn);

			Precificacao precificacao = new Precificacao();
			if (precoCusto != null && !precoCusto.equals("")) {
				precificacao.setPrecoCusto(new BigDecimal(precoCusto));
			}
			livro.setPrecificacao(precificacao);

			Estoque estoque = new Estoque();
			// Se nenhuma quantidade for informada, seta o valor como zero
			estoque.setQuantidadeAtual(Long.valueOf(0));
			if (quantAtual != null && !quantAtual.equals("")) {
				estoque.setQuantidadeAtual(Long.valueOf(quantAtual));
			}
			estoque.setQuantidadeMinima(Long.valueOf(0));
			if (quantMin != null && !quantMin.equals("")) {
				estoque.setQuantidadeMinima(Long.valueOf(quantMin));
			}
			estoque.setQuantidadeMaxima(Long.valueOf(0));
			if (quantMax != null && !quantMax.equals("")) {
				estoque.setQuantidadeMaxima(Long.valueOf(quantMax));
			}
			estoque.setQuantidadeReservada(Long.valueOf(0));
			livro.setEstoque(estoque);

			Dimensoes dimensoes = new Dimensoes();
			// Se nenhuma altura for informada, seta o valor como zero
			dimensoes.setAltura(new BigDecimal("0.0"));
			if (altura != null && !altura.equals("")) {
				dimensoes.setAltura(new BigDecimal(altura));
			}
			// Se nenhuma altura for informada, seta o valor como zero
			dimensoes.setLargura(new BigDecimal("0"));
			if (largura != null && !largura.equals("")) {
				dimensoes.setLargura(new BigDecimal(largura));
			}
			// Se nenhuma profundidade for informada, seta o valor como zero
			dimensoes.setProfundidade(new BigDecimal("0"));
			if (profundidade != null && !profundidade.equals("")) {
				dimensoes.setProfundidade(new BigDecimal(profundidade));
			}
			// Se nenhum peso for informado, seta o valor como zero
			dimensoes.setPeso(new BigDecimal("0"));
			if (peso != null && !peso.equals("")) {
				dimensoes.setPeso(new BigDecimal(peso));
			}

			livro.setDimensoes(dimensoes);

			// Criando as categorias com base nos IDs selecionados
			List<Categoria> categorias = new ArrayList<Categoria>();
			for (String idCat : idsCategorias) {
				Categoria categoria = new Categoria();
				categoria.setId(Long.valueOf(idCat));
				categorias.add(categoria);
			}

			livro.setCategorias(categorias);
			livro.setSinopse(sinopse);

			Autor autor = new Autor();
			autor.setNome(nomeAutor);

			// Evitando exceção ao converter a data
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
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();

		if (uri.equals(contexto + "/livroForm")) {
			request.getRequestDispatcher("livroFormCategorias?operacao=CONSULTAR").forward(request, response);
		}
		if (uri.equals(contexto + "/livroList")) {
			request.getRequestDispatcher("WEB-INF/jsp/livro/list.jsp").forward(request, response);
		}
		if (uri.equals(contexto + "/livroEdit")) {
			List<Livro> listaLivros = (List<Livro>) object;
			Livro livro = listaLivros.get(0);
			request.setAttribute("livro", livro);
			request.setAttribute("autor", livro.getAutores().get(0));
			request.setAttribute("operacao", ALTERAR);
			request.getRequestDispatcher("WEB-INF/jsp/livro/form.jsp").forward(request, response);
		}
		if (uri.equals(contexto + "/livroSalvar")) {
			if (object == null) {
				String sucesso = "Livro cadastrado com sucesso";
				request.setAttribute("sucesso", sucesso);
				request.getRequestDispatcher("WEB-INF/jsp/livro/list.jsp").forward(request, response);
				return;
			}
			CategoriaDAO catDAO = new CategoriaDAO();
			Categoria categoria = new Categoria();
			categoria.setNome("");
			List<EntidadeDominio> categorias = catDAO.consultar(categoria);
			request.setAttribute("categorias", categorias);
			GrupoPrecificacaoDAO gpDAO = new GrupoPrecificacaoDAO();
			GrupoPrecificacao gpConsulta = new GrupoPrecificacao();
			gpConsulta.setNome("");
			List<EntidadeDominio> gruposPrecificacao = gpDAO.consultar(gpConsulta);
			request.setAttribute("gruposPrecificacao", gruposPrecificacao);
			String mensagem = (String) object;
			String[] mensagens = mensagem.split(":");
			Livro livro = (Livro) this.getEntidade(request);
			request.setAttribute("livro", livro);
			request.setAttribute("autor", livro.getAutores().get(0));
			request.setAttribute("mensagens", mensagens);
			request.getRequestDispatcher("WEB-INF/jsp/livro/form.jsp").forward(request, response);
		}
		if (uri.equals(contexto + "/livroConsultar")) {

			if (object == null) {
				String erro = "Nenhum livro encontrado";
				request.setAttribute("erro", erro);
				request.getRequestDispatcher("WEB-INF/jsp/livro/list.jsp").forward(request, response);
				return;
			}
			List<Livro> consulta = (List<Livro>) object;
			request.setAttribute("consulta", consulta);
			request.getRequestDispatcher("WEB-INF/jsp/livro/list.jsp").forward(request, response);
		}
		if (uri.equals(contexto + "/livroAlterar")) {
			if (object != null) {
				Categoria categoria = new Categoria();
				categoria.setNome("");
				CategoriaDAO dao = new CategoriaDAO();
				GrupoPrecificacaoDAO gpDAO = new GrupoPrecificacaoDAO();
				GrupoPrecificacao gpConsulta = new GrupoPrecificacao();
				gpConsulta.setNome("");
				List<EntidadeDominio> gruposPrecificacao = gpDAO.consultar(gpConsulta);
				request.setAttribute("gruposPrecificacao", gruposPrecificacao);
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				Livro livro = (Livro) this.getEntidade(request);
				request.setAttribute("livro", livro);
				request.setAttribute("autor", livro.getAutores().get(0));
				request.setAttribute("mensagens", mensagens);
				request.getRequestDispatcher("WEB-INF/jsp/livro/form.jsp").forward(request, response);
				return;
			}
			String sucesso = "Alteração efetuada com sucesso";
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("WEB-INF/jsp/livro/list.jsp").forward(request, response);
			return;
		}
		if (uri.equals(contexto + "/livroExcluir")) {
			String sucesso = (String) object;
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("WEB-INF/jsp/livro/list.jsp").forward(request, response);
		}
		if (uri.equals(contexto + "/carrinhoConsultarEstoque")) {
			List<Livro> listaLivros = (List<Livro>) object;
			Livro livro = listaLivros.get(0);
			request.setAttribute("livro", livro);
			request.setAttribute("operacao", "");
			request.getRequestDispatcher("carrinhoAlterar?operacao=").forward(request, response);
		}
	}

}
