package ebooks.controle.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ebooks.controle.ConsultarCommand;
import ebooks.controle.ICommand;
import ebooks.modelo.Acesso;
import ebooks.modelo.Login;

public class AcessoFilter implements Filter {
	
	private ICommand command;
	
	public AcessoFilter() {
		command = new ConsultarCommand();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		Login login = (Login) session.getAttribute("login");
		Acesso acesso = new Acesso();
		acesso.setRequest(request);
		acesso.setLogin(login);
		Object obj = command.executar(acesso);
		if(obj != null) {
			String mensagem = (String) obj;
			httpRequest.getRequestDispatcher("404").forward(request, response);
		}
		else {
			if(acesso.isLiberado()) {
				chain.doFilter(request, response);
			}
			else {
				String erro = "Acesso negado. Faça login para continuar";
				httpRequest.setAttribute("erro", erro);
				httpRequest.getRequestDispatcher("loginSite").forward(httpRequest, response);
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
