package ebooks.modelo;

public class Estoque extends EntidadeDominio {
	private Long quantidadeMinima;
	private Long quantidadeMaxima;
	private Long quantidadeAtual;

	public Long getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(Long quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}

	public Long getQuantidadeMaxima() {
		return quantidadeMaxima;
	}

	public void setQuantidadeMaxima(Long quantidadeMaxima) {
		this.quantidadeMaxima = quantidadeMaxima;
	}

	public Long getQuantidadeAtual() {
		return quantidadeAtual;
	}

	public void setQuantidadeAtual(Long quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
}
