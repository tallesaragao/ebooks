package ebooks.modelo;

import java.util.List;

public class Cliente extends PessoaFisica {
	private Character genero;
	private String email;
	private boolean ativo;
	private Login login;
	private Telefone telefone;
	private List<Endereco> enderecos;
	private List<CartaoCredito> cartoesCredito;
	private List<Ativacao> ativacoes;
	private List<Inativacao> inativacoes;
	private List<Pedido> pedidos;
	private List<Troca> trocas;
	private List<CupomTroca> cuponsTroca;

	public Character getGenero() {
		return genero;
	}

	public void setGenero(Character genero) {
		this.genero = genero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<CartaoCredito> getCartoesCredito() {
		return cartoesCredito;
	}

	public void setCartoesCredito(List<CartaoCredito> cartoesCredito) {
		this.cartoesCredito = cartoesCredito;
	}

	public List<Ativacao> getAtivacoes() {
		return ativacoes;
	}

	public void setAtivacoes(List<Ativacao> ativacoes) {
		this.ativacoes = ativacoes;
	}

	public List<Inativacao> getInativacoes() {
		return inativacoes;
	}

	public void setInativacoes(List<Inativacao> inativacoes) {
		this.inativacoes = inativacoes;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<Troca> getTrocas() {
		return trocas;
	}

	public void setTrocas(List<Troca> trocas) {
		this.trocas = trocas;
	}

	public List<CupomTroca> getCuponsTroca() {
		return cuponsTroca;
	}

	public void setCuponsTroca(List<CupomTroca> cuponsTroca) {
		this.cuponsTroca = cuponsTroca;
	}
}
