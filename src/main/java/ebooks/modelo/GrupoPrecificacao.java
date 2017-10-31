package ebooks.modelo;

import java.math.BigDecimal;

public class GrupoPrecificacao extends EntidadeDominio {
	private BigDecimal margemLucro;
	private String nome;

	public BigDecimal getMargemLucro() {
		return margemLucro;
	}

	public void setMargemLucro(BigDecimal margemLucro) {
		this.margemLucro = margemLucro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
