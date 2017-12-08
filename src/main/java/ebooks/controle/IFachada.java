package ebooks.controle;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.EntidadeDominio;

public interface IFachada {
	public Resultado salvar(EntidadeDominio entidade);

	public Resultado alterar(EntidadeDominio entidade);

	public Resultado excluir(EntidadeDominio entidade);

	public Resultado consultar(EntidadeDominio entidade);
}
