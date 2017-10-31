package ebooks.modelo;

import java.math.BigDecimal;

public class Pagamento extends EntidadeDominio {
	private BigDecimal valorPago;

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
}
