package ebooks.modelo;

import java.util.List;

public class FormaPagamento extends EntidadeDominio {
	private List<Pagamento> pagamentos;
	private Long parcelas;

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public Long getParcelas() {
		return parcelas;
	}

	public void setParcelas(Long parcelas) {
		this.parcelas = parcelas;
	}
}
