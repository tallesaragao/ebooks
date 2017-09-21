package ebooks.controle;

import ebooks.modelo.EntidadeDominio;

public class SalvarCommand extends AbstractCommand {

	@Override
	public String executar(EntidadeDominio entidade) {
		return fachada.salvar(entidade);
	}

}
