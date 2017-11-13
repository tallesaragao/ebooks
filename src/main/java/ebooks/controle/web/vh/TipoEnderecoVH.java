package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.TipoEndereco;
import ebooks.modelo.EntidadeDominio;

public class TipoEnderecoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		TipoEndereco tipoEndereco = new TipoEndereco();
		return tipoEndereco;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/clienteFormTiposEndereco") || uri.equals(contexto + "/clienteEditTiposEndereco")) {
			List<TipoEndereco> tiposEndereco = (List<TipoEndereco>) object;
			HttpSession session = request.getSession();
			session.setAttribute("tiposEndereco", tiposEndereco);
			if(uri.equals(contexto + "/clienteFormTiposEndereco")) {
				request.getRequestDispatcher("clienteFormTiposTelefone?operacao=CONSULTAR").forward(request, response);				
			}
			else {
				request.getRequestDispatcher("clienteEditTiposTelefone?operacao=CONSULTAR").forward(request, response);				
			}
		}
	}

}
