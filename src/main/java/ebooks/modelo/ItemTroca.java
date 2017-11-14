package ebooks.modelo;

public class ItemTroca extends EntidadeDominio {
	private ItemPedido itemPedido;
	private Long quantidadeRetornavel;

	public ItemPedido getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}

	public Long getQuantidadeRetornavel() {
		return quantidadeRetornavel;
	}

	public void setQuantidadeRetornavel(Long quantidadeRetornavel) {
		this.quantidadeRetornavel = quantidadeRetornavel;
	}
}
