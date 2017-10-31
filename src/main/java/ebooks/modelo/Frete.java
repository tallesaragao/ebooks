package ebooks.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Frete extends EntidadeDominio {
	private BigDecimal valor;
	private Long diasEntrega;
	private Date prazoEstimado;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getDiasEntrega() {
		return diasEntrega;
	}

	public void setDiasEntrega(Long diasEntrega) {
		this.diasEntrega = diasEntrega;
	}

	public Date getPrazoEstimado() {
		return prazoEstimado;
	}

	public void setPrazoEstimado(Date prazoEstimado) {
		this.prazoEstimado = prazoEstimado;
	}
}
