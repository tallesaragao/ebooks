package ebooks.modelo;

import java.util.List;

public class Troca extends EntidadeDominio {
	private Pedido pedido;
	private Boolean compraToda;
	private List<ItemTroca> itensTroca;
	private CupomTroca cupomTroca;
	private StatusTroca status;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Boolean getCompraToda() {
		return compraToda;
	}

	public void setCompraToda(Boolean compraToda) {
		this.compraToda = compraToda;
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

	public StatusTroca getStatus() {
		return status;
	}

	public void setStatus(StatusTroca status) {
		this.status = status;
	}
}
