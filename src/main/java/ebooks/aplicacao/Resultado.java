package ebooks.aplicacao;

import java.util.List;

import ebooks.modelo.EntidadeDominio;

public class Resultado extends EntidadeAplicacao {
	private List<EntidadeDominio> entidades;
	private String resposta;

	public List<EntidadeDominio> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<EntidadeDominio> entidades) {
		this.entidades = entidades;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
}
