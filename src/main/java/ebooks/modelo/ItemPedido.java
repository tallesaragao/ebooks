package ebooks.modelo;

public class ItemPedido extends EntidadeDominio {
	private Livro livro;
	private Long quantidade;

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
}
