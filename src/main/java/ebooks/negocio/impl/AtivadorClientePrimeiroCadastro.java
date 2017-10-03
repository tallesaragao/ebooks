package ebooks.negocio.impl;

import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class AtivadorClientePrimeiroCadastro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		if(cliente.getId() == null) {
			cliente.setAtivo(true);
		}
		return null;
	}

}
