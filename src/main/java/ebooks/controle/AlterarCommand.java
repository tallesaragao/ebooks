package ebooks.controle;

import ebooks.modelo.EntidadeDominio;

public class AlterarCommand extends AbstractCommand {

	@Override
	public String executar(EntidadeDominio entidade) {
		return fachada.alterar(entidade);
	}

}
