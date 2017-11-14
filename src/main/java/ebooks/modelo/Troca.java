package ebooks.modelo;

import java.util.List;

public class Troca extends EntidadeDominio {
	private Pedido pedido;
	private List<ItemTroca> itensTroca;
	private CupomTroca cupomTroca;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<ItemTroca> getItensTroca() {
		return itensTroca;
	}

	public void setItensTroca(List<ItemTroca> itensTroca) {
		this.itensTroca = itensTroca;
	}

	public CupomTroca getCupomTroca() {
		return cupomTroca;
	}

	public void setCupomTroca(CupomTroca cupomTroca) {
		this.cupomTroca = cupomTroca;
	}
}
