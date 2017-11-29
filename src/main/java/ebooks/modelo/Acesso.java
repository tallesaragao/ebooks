package ebooks.modelo;

import javax.servlet.ServletRequest;

public class Acesso extends EntidadeDominio {
	private ServletRequest request;
	private Login login;
	private boolean paginaEncontrada;
	private boolean liberado;

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public boolean isPaginaEncontrada() {
		return paginaEncontrada;
	}

	public void setPaginaEncontrada(boolean paginaEncontrada) {
		this.paginaEncontrada = paginaEncontrada;
	}

	public boolean isLiberado() {
		return liberado;
	}

	public void setLiberado(boolean liberado) {
		this.liberado = liberado;
	}
}
