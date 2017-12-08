package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.TipoTelefone;

public class TipoTelefoneVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		TipoTelefone tipoTelefone = new TipoTelefone();
		return tipoTelefone;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/clienteFormTiposTelefone") || uri.equals(contexto + "/clienteEditTiposTelefone")) {
			List<EntidadeDominio> tiposTelefone = resultado.getEntidades();
			HttpSession session = request.getSession();
			session.setAttribute("tiposTelefone", tiposTelefone);
			if(uri.equals(contexto + "/clienteEditTiposTelefone")) {
				request.setAttribute("operacao", "ALTERAR");
			}
			request.getRequestDispatcher("WEB-INF/jsp/cliente/form.jsp").forward(request, response);
		}
	}

}
