package ebooks.modelo;

public class GrupoPrecificacao extends EntidadeDominio {
	private Double margemLucro;
	private String nome;

	public Double getMargemLucro() {
		return margemLucro;
	}

	public void setMargemLucro(Double margemLucro) {
		this.margemLucro = margemLucro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
