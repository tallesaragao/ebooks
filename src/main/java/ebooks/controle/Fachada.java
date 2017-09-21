package ebooks.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ebooks.dao.CategoriaDAO;
import ebooks.dao.IDAO;
import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;
import ebooks.negocio.impl.ComplementarDtCadastro;
import ebooks.negocio.impl.ValidarCamposCategoria;

public class Fachada implements IFachada {

	private static final String SALVAR = "SALVAR";
	private static final String ALTERAR = "ALTERAR";
	private static final String CONSULTAR = "CONSULTAR";
	private static final String EXCLUIR = "EXCLUIR";

	private Map<String, Map<String, List<IStrategy>>> requisitos;
	private Map<String, IDAO> daos;

	public Fachada() {
		ComplementarDtCadastro compDtCad = new ComplementarDtCadastro();
		ValidarCamposCategoria valCampCat = new ValidarCamposCategoria();

		List<IStrategy> lSalvarCat = new ArrayList<IStrategy>();
		lSalvarCat.add(compDtCad);
		lSalvarCat.add(valCampCat);
		
		List<IStrategy> lAlterarCat = new ArrayList<IStrategy>();
		lAlterarCat.add(valCampCat);
		
		List<IStrategy> lExcluirCat = new ArrayList<IStrategy>();
		
		List<IStrategy> lConsultarCat = new ArrayList<IStrategy>();

		Map<String, List<IStrategy>> contextoCat = new HashMap<String, List<IStrategy>>();
		contextoCat.put(SALVAR, lSalvarCat);
		contextoCat.put(ALTERAR, lAlterarCat);
		contextoCat.put(EXCLUIR, lExcluirCat);
		contextoCat.put(CONSULTAR, lConsultarCat);

		requisitos = new HashMap<String, Map<String, List<IStrategy>>>();
		requisitos.put(Categoria.class.getName(), contextoCat);
		
		daos = new HashMap<String, IDAO>();
		daos.put(Categoria.class.getName(), new CategoriaDAO());
	}

	@Override
	public String salvar(EntidadeDominio entidade) {
		StringBuilder sb = executarRegras(entidade, SALVAR);
		if (sb.length() > 0) {
			return sb.toString();
		}
		IDAO dao = daos.get(entidade.getClass().getName());
		if(!dao.salvar(entidade)) {
			return "Erro ao persistir a entidade";
		}
		return null;
		
	}

	@Override
	public String alterar(EntidadeDominio entidade) {
		IDAO dao = daos.get(entidade.getClass().getName());
		StringBuilder sb = executarRegras(entidade, ALTERAR);
		if (sb.length() > 0) {
			return sb.toString();
		}
		if(!dao.alterar(entidade)) {
			return "Problema na alteração";
		}
		return null;
	}

	@Override
	public String excluir(EntidadeDominio entidade) {
		IDAO dao = daos.get(entidade.getClass().getName());
		if (!dao.excluir(entidade)) {
			return "Problema na exclusão";
		}
		return "Exclusão efetuada com sucesso";
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		IDAO dao = daos.get(entidade.getClass().getName());
		List<EntidadeDominio> consulta = dao.consultar(entidade);
		if(consulta.isEmpty()) {
			return null;
		}
		return consulta;
	}

	private StringBuilder executarRegras(EntidadeDominio entidade, String operacao) {
		StringBuilder sb = new StringBuilder();
		Map<String, List<IStrategy>> contextoEntidade = requisitos.get(entidade.getClass().getName());
		List<IStrategy> reqs = contextoEntidade.get(operacao);
		for (IStrategy req : reqs) {
			String msg = req.processar(entidade);
			if (msg != null) {
				sb.append(msg);
			}
		}
		return sb;
	}

}
