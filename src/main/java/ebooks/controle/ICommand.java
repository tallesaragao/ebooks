package ebooks.controle;

import ebooks.modelo.EntidadeDominio;

public interface ICommand {
	public Object executar(EntidadeDominio entidade);
}
