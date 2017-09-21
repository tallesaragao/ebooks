package ebooks.negocio.impl;

import java.util.Date;

import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class ComplementarDtCadastro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		entidade.setDataCadastro(new Date());
		return null;
	}

}
