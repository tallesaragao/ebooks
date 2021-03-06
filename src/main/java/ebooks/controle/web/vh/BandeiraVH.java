package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.Bandeira;
import ebooks.modelo.EntidadeDominio;

public class BandeiraVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Bandeira bandeira = new Bandeira();
		return bandeira;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/cartaoCreditoFormBandeiras") || uri.equals(contexto + "/cartaoCreditoEditBandeiras")) {
			List<EntidadeDominio> bandeiras = resultado.getEntidades();
			HttpSession session = request.getSession();
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idCliente", idCliente);
			session.setAttribute("bandeiras", bandeiras);
			request.getRequestDispatcher("WEB-INF/jsp/cartaoCredito/form.jsp").forward(request, response);
		}
	}
}
