package ebooks.negocio.impl;

import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class DefinirEnderecoPrincipal implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Endereco endereco = (Endereco) entidade;
		endereco.setPrincipal(true);
		return null;
	}

}
