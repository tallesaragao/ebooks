package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;

public class CategoriaVH implements IViewHelper {
	
	private static final String SALVAR = "SALVAR";
	private static final String CONSULTAR = "CONSULTAR";
	private static final String ALTERAR = "ALTERAR";
	private static final String EXCLUIR = "EXCLUIR";

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Categoria categoria = new Categoria();
		String operacao = request.getParameter("operacao");
		
		if(operacao.equals(CONSULTAR)) {
			String id = request.getParameter("id");
			String busca = request.getParameter("busca");
			categoria.setNome("");
			if(busca != null) {
				categoria.setNome(busca);
			}
			if(id != null) {
				categoria.setId(Long.valueOf(id));
			}
		}
		else if(operacao.equals(EXCLUIR)) {
			String id = request.getParameter("id");
			if(id != null && !id.equals("")) {
				categoria.setId(Long.valueOf(id));
			}
		}
		else if(operacao.equals(ALTERAR)) {
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			categoria.setId(Long.valueOf(id));
			categoria.setNome(nome);
		}
		else {
			String nome = request.getParameter("nome");
			categoria.setNome(nome);
		}
		
		return (EntidadeDominio) categoria;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		String contexto = request.getContextPath();
		
		if(uri.equals(contexto + "/categoriaForm")) {
			request.getRequestDispatcher("WEB-INF/jsp/categoria/form.jsp").forward(request, response);	
		}
		
		if(uri.equals(contexto + "/categoriaList")) {
			request.getRequestDispatcher("WEB-INF/jsp/categoria/list.jsp").forward(request, response);	
		}
		
		if(uri.equals(contexto + "/categoriaEdit")) {
			List<Categoria> lista = (List<Categoria>) object;
			Categoria categoria = lista.get(0);
			request.setAttribute("categoria", categoria);
			request.setAttribute("operacao", ALTERAR);
			request.getRequestDispatcher("WEB-INF/jsp/categoria/form.jsp").forward(request, response);
		}
		
		if(uri.equals(contexto + "/livroFormCategorias")) {
			List<Categoria> categorias = (List<Categoria>) object;
			HttpSession session = request.getSession();
			session.setAttribute("categorias", categorias);
			request.getRequestDispatcher("livroFormGruposPrecificacao?operacao=CONSULTAR").forward(request, response);
		}
		
		if(uri.equals(contexto + "/vendasAnaliseCategorias")) {
			List<Categoria> categorias = (List<Categoria>) object;
			request.setAttribute("categorias", categorias);
			request.getRequestDispatcher("vendasAnalise?operacao=").forward(request, response);
		}
		
		if(uri.equals(contexto + "/categoriaSalvar")) {
			if(object == null) {
				String sucesso = "Categoria cadastrada com sucesso";
				request.setAttribute("sucesso", sucesso);
				request.getRequestDispatcher("WEB-INF/jsp/categoria/list.jsp").forward(request, response);
				return;
			}
			String mensagem = (String) object;
			String[] mensagens = mensagem.split(":");
			Categoria categoria = (Categoria) this.getEntidade(request);
			request.setAttribute("categoria", categoria);
			request.setAttribute("mensagens", mensagens);
			request.getRequestDispatcher("WEB-INF/jsp/categoria/form.jsp").forward(request, response);
		}
		
		if(uri.equals(contexto + "/categoriaConsultar")) {
			if(object == null) {
				String erro = "Nenhuma categoria encontrada";
				request.setAttribute("erro", erro);
				request.getRequestDispatcher("WEB-INF/jsp/categoria/list.jsp").forward(request, response);
				return;
			}
			List<Categoria> consulta = (List<Categoria>) object;
			request.setAttribute("consulta", consulta);
			request.getRequestDispatcher("WEB-INF/jsp/categoria/list.jsp").forward(request, response);
		}
		
		if(uri.equals(contexto + "/categoriaAlterar")) {
			if (object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				Categoria categoria = (Categoria) this.getEntidade(request);
				request.setAttribute("mensagens", mensagens);
				request.setAttribute("categoria", categoria);
				request.setAttribute("operacao", ALTERAR);
				request.getRequestDispatcher("WEB-INF/jsp/categoria/form.jsp").forward(request, response);
				return;
			}
			String sucesso = "Alteração efetuada com sucesso";
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("WEB-INF/jsp/categoria/list.jsp").forward(request, response);
			return;
		}
		
		if(uri.equals(contexto + "/categoriaExcluir")) {
			String sucesso = (String) object;
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("WEB-INF/jsp/categoria/list.jsp").forward(request, response);
		}
	}

}
