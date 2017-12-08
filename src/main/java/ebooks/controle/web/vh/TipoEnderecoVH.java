package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.TipoEndereco;

public class TipoEnderecoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		TipoEndereco tipoEndereco = new TipoEndereco();
		return tipoEndereco;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/clienteFormTiposEndereco") || uri.equals(contexto + "/clienteEditTiposEndereco")) {
			List<EntidadeDominio> tiposEndereco = resultado.getEntidades();
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
