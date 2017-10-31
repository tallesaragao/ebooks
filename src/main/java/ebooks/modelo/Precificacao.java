package ebooks.modelo;

import java.math.BigDecimal;

public class Precificacao extends EntidadeDominio {
	private BigDecimal precoCusto;
	private BigDecimal precoVenda;

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}
}
