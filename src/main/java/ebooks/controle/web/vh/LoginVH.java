package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;
import ebooks.modelo.Pedido;

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
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/loginSite")) {
			request.getRequestDispatcher("WEB-INF/jsp/login/login.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/logoutSite")) {
			HttpSession session = request.getSession();
			Pedido pedido = (Pedido) session.getAttribute("pedido");
			if(pedido != null) {
				request.setAttribute("pedido", pedido);
				request.getRequestDispatcher("carrinhoPedidoRemover?operacao=EXCLUIR").forward(request, response);
			}
			else {
				session.invalidate();
				response.sendRedirect("loginSite");				
			}
		}
		if(uri.equals(contexto + "/loginForm")) {
			request.getRequestDispatcher("WEB-INF/jsp/login/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/loginSalvar")) {
			if(resultado.getResposta() == null) {
				String sucesso = "Login cadastrado com sucesso";
				request.setAttribute("sucesso", sucesso);
				request.getRequestDispatcher("WEB-INF/jsp/login/login.jsp").forward(request, response);
				return;
			}
			String mensagem = resultado.getResposta();
			String[] mensagens = mensagem.split(":");
			Login login = (Login) this.getEntidade(request);
			request.setAttribute("login", login);
			request.setAttribute("mensagens", mensagens);
			request.getRequestDispatcher("WEB-INF/jsp/login/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/loginConsultar")) {
			if(resultado.getEntidades() != null) {
				response.reset();
				List<EntidadeDominio> consulta = resultado.getEntidades();
				Login login = (Login) consulta.get(0);
				HttpSession session = request.getSession();
				session.setAttribute("login", login);
				request.setAttribute("operacao", "");
				response.sendRedirect("livroList");
				return;
			}
			request.setAttribute("erro", "Usuário ou senha incorretos");
			request.getRequestDispatcher("WEB-INF/jsp/login/login.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/loginAlterar")) {
			request.getRequestDispatcher("WEB-INF/jsp/login/login.jsp").forward(request, response);
		}
	}

}
