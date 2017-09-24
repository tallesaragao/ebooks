package ebooks.negocio.impl;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Livro;
import ebooks.negocio.IStrategy;

public class AtivadorLivroPrimeiroCadastro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Livro livro = (Livro) entidade;
		livro.setAtivo(true);
		entidade = livro;
		return null;
	}

}
