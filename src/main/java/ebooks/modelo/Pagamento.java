package ebooks.modelo;

import java.math.BigDecimal;

public class Pagamento extends EntidadeDominio {
	private BigDecimal valorPago;
	private FormaPagamento formaPagamento;

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
}
