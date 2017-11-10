package ebooks.modelo;

import java.util.List;

public class PerfilAcesso extends EntidadeDominio {
	private String nome;
	private List<Uri> urisAcessiveis;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Uri> getUrisAcessiveis() {
		return urisAcessiveis;
	}

	public void setUrisAcessiveis(List<Uri> urisAcessiveis) {
		this.urisAcessiveis = urisAcessiveis;
	}
}
