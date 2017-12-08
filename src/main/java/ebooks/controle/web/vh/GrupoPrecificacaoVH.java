package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.GrupoPrecificacao;

public class GrupoPrecificacaoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();
		return grupoPrecificacao;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/livroFormGruposPrecificacao")) {
			List<EntidadeDominio> gruposPrecificacao = resultado.getEntidades();
			HttpSession session = request.getSession();
			session.setAttribute("gruposPrecificacao", gruposPrecificacao);
			request.getRequestDispatcher("WEB-INF/jsp/livro/form.jsp").forward(request, response);
		}

	}

}
