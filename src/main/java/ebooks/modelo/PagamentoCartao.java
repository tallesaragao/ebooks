package ebooks.modelo;

public class PagamentoCartao extends Pagamento {
	private CartaoCredito cartaoCredito;

	public CartaoCredito getCartaoCredito() {
		return cartaoCredito;
	}

	public void setCartaoCredito(CartaoCredito cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}
}
