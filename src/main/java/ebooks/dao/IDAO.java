package ebooks.dao;

import java.util.List;

import ebooks.modelo.EntidadeDominio;

public interface IDAO {
	public boolean salvar(EntidadeDominio entidade);

	public boolean alterar(EntidadeDominio entidade);

	public boolean excluir(EntidadeDominio entidade);

	public List<EntidadeDominio> consultar(EntidadeDominio entidade);
}
