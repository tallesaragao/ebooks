package ebooks.negocio;

import ebooks.modelo.EntidadeDominio;

public interface IStrategy {
	public String processar(EntidadeDominio entidade);
}
