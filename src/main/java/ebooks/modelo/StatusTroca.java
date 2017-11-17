package ebooks.modelo;

public class StatusTroca extends EntidadeDominio {
	private Boolean atual;
	private Status status;
	private Troca troca;

	public Boolean getAtual() {
		return atual;
	}

	public void setAtual(Boolean atual) {
		this.atual = atual;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Troca getTroca() {
		return troca;
	}

	public void setTroca(Troca troca) {
		this.troca = troca;
	}
}
