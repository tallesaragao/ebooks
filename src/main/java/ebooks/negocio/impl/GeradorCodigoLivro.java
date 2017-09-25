package ebooks.negocio.impl;

import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Livro;
import ebooks.negocio.IStrategy;

public class GeradorCodigoLivro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Livro livro = (Livro) entidade;
		String codigo = new String();
		codigo = "" + livro.getGrupoPrecificacao().getId();
		for(Categoria cat : livro.getCategorias()) {
			codigo += cat.getId();
		}
		codigo += "-" + livro.hashCode();
		livro.setCodigo(codigo);
		entidade = livro;
		return null;
	}

}
