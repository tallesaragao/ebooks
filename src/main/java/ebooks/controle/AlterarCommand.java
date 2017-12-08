package ebooks.controle;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.EntidadeDominio;

public class AlterarCommand extends AbstractCommand {

	@Override
	public Resultado executar(EntidadeDominio entidade) {
		return fachada.alterar(entidade);
	}

}
