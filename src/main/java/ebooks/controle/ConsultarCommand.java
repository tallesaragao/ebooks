 package ebooks.controle;

import java.util.List;

import ebooks.modelo.EntidadeDominio;

public class ConsultarCommand extends AbstractCommand {

	@Override
	public List<EntidadeDominio> executar(EntidadeDominio entidade) {
		return fachada.consultar(entidade);
	}

}
