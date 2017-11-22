package ebooks.modelo;

public class ItemTroca extends EntidadeDominio {
	private ItemPedido itemPedido;
	private Long quantidadeTrocada;
	private Long quantidadeRetornavel;
	private Troca troca;

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}

	public Long getQuantidadeTrocada() {
		return quantidadeTrocada;
	}

	public void setQuantidadeTrocada(Long quantidadeTrocada) {
		this.quantidadeTrocada = quantidadeTrocada;
	}

	public Long getQuantidadeRetornavel() {
		return quantidadeRetornavel;
	}

	public void setQuantidadeRetornavel(Long quantidadeRetornavel) {
		this.quantidadeRetornavel = quantidadeRetornavel;
	}

	public Troca getTroca() {
		return troca;
	}

	public void setTroca(Troca troca) {
		this.troca = troca;
	}
}
