package ebooks.controle;

import ebooks.modelo.EntidadeDominio;

public class ExcluirCommand extends AbstractCommand {

	@Override
	public String executar(EntidadeDominio entidade) {
		return fachada.excluir(entidade);
	}

}
