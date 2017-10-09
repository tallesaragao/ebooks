package ebooks.controle.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.Bandeira;
import ebooks.modelo.EntidadeDominio;

public class BandeiraVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Bandeira bandeira = new Bandeira();
		return bandeira;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/cartaoCreditoFormBandeiras") || uri.equals(contexto + "/cartaoCreditoEditBandeiras")) {
			List<Bandeira> bandeiras = (List<Bandeira>) object;
			HttpSession session = request.getSession();
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idCliente", idCliente);
			session.setAttribute("bandeiras", bandeiras);
			request.getRequestDispatcher("WEB-INF/jsp/cartaoCredito/form.jsp").forward(request, response);
		}
	}
}
