package ebooks.modelo;

public class Log extends EntidadeDominio {
	private String nomeEntidade;
	private String usuario;
	private String operacao;
	private String estadoAnterior;
	private EntidadeDominio entidade;

	public String getNomeEntidade() {
		return nomeEntidade;
	}

	public void setNomeEntidade(String nomeEntidade) {
		this.nomeEntidade = nomeEntidade;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public EntidadeDominio getEntidade() {
		return entidade;
	}

	public void setEntidade(EntidadeDominio entidade) {
		this.entidade = entidade;
	}
}
