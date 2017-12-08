package ebooks.controle;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.EntidadeDominio;

public class SalvarCommand extends AbstractCommand {

	@Override
	public Resultado executar(EntidadeDominio entidade) {
		return fachada.salvar(entidade);
	}

}
