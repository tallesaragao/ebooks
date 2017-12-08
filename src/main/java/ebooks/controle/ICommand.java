package ebooks.controle;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.EntidadeDominio;

public interface ICommand {
	public Resultado executar(EntidadeDominio entidade);
}
