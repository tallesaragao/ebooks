package ebooks.negocio.impl;

import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class ValidarCamposCategoria implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Categoria categoria = (Categoria) entidade;
		if(categoria.getNome() == null || categoria.getNome().equals("")) {
			sb.append("Nome da categoria é obrigatório:");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
