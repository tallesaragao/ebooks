package ebooks.modelo;

import java.math.BigDecimal;
import java.util.List;

public class Pedido extends EntidadeDominio {
	private String numero;
	private Cliente cliente;
	private Endereco enderecoCobranca;
	private Endereco enderecoEntrega;
	private List<ItemPedido> itensPedido;
	private Frete frete;
	private CupomPromocional cupomPromocional;
	private BigDecimal valorTotal;
	private FormaPagamento formaPagamento;
	private List<StatusPedido> statusesPedido;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoCobranca() {
		return enderecoCobranca;
	}

	public void setEnderecoCobranca(Endereco enderecoCobranca) {
		this.enderecoCobranca = enderecoCobranca;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public Frete getFrete() {
		return frete;
	}

	public void setFrete(Frete frete) {
		this.frete = frete;
	}

	public CupomPromocional getCupomPromocional() {
		return cupomPromocional;
	}

	public void setCupomPromocional(CupomPromocional cupomPromocional) {
		this.cupomPromocional = cupomPromocional;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public List<StatusPedido> getStatusesPedido() {
		return statusesPedido;
	}

	public void setStatusesPedido(List<StatusPedido> statusesPedido) {
		this.statusesPedido = statusesPedido;
	}
}
