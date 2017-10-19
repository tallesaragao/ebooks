package ebooks.modelo;

import javax.servlet.http.HttpSession;

public class Carrinho extends EntidadeDominio {
	private HttpSession session;
	private Pedido pedido;
	private boolean pedidoFinalizado;

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public boolean isPedidoFinalizado() {
		return pedidoFinalizado;
	}

	public void setPedidoFinalizado(boolean pedidoFinalizado) {
		this.pedidoFinalizado = pedidoFinalizado;
	}
}
