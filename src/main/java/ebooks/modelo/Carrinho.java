package ebooks.modelo;

import javax.servlet.http.HttpSession;

public class Carrinho extends EntidadeDominio {
	private Pedido pedido;
	private Pedido pedidoSession;
	private boolean pedidoFinalizado;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedidoSession() {
		return pedidoSession;
	}

	public void setPedidoSession(Pedido pedidoSession) {
		this.pedidoSession = pedidoSession;
	}

	public boolean isPedidoFinalizado() {
		return pedidoFinalizado;
	}

	public void setPedidoFinalizado(boolean pedidoFinalizado) {
		this.pedidoFinalizado = pedidoFinalizado;
	}
}
