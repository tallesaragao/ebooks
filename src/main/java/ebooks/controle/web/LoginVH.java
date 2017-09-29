package ebooks.controle.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;

public class LoginVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Login login = new Login();
		if(operacao != null) {
			if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				if(operacao.equals("ALTERAR")) {
					String id = request.getParameter("id");
					login.setId(Long.valueOf(id));
				}
				String usuario = request.getParameter("usuario");
				String senha = request.getParameter("senha");
				String senhaConfirmacao = request.getParameter("senhaConfirmacao");
				login.setUsuario(usuario);
				login.setSenha(senha);
				login.setSenhaConfirmacao(senhaConfirmacao);
			}
			if(operacao.equals("CONSULTAR")) {
				String id = request.getParameter("id");
				String usuario = request.getParameter("usuario");
				String senha = request.getParameter("senha");
				if(id != null) {
					login.setId(Long.valueOf(id));
				}
				login.setUsuario(usuario);
				login.setSenha(senha);
			}
		}
		return login;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/loginCliente")) {
			request.getRequestDispatcher("WEB-INF/jsp/login/login.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/loginForm")) {
			request.getRequestDispatcher("WEB-INF/jsp/login/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/loginSalvar")) {
			if(object == null) {
				String sucesso = "Categoria cadastrada com sucesso";
				request.setAttribute("sucesso", sucesso);
				request.getRequestDispatcher("WEB-INF/jsp/categoria/list.jsp").forward(request, response);
				return;
			}
			String mensagem = (String) object;
			String[] mensagens = mensagem.split(":");
			Login login = (Login) this.getEntidade(request);
			request.setAttribute("login", login);
			request.setAttribute("mensagens", mensagens);
			request.getRequestDispatcher("WEB-INF/jsp/login/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/loginConsultar")) {
			request.getRequestDispatcher("WEB-INF/jsp/login/login.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/loginAlterar")) {
			request.getRequestDispatcher("WEB-INF/jsp/login/login.jsp").forward(request, response);
		}
	}

}
