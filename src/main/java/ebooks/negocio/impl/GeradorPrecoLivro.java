package ebooks.negocio.impl;

import java.util.List;

import ebooks.dao.GrupoPrecificacaoDAO;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.GrupoPrecificacao;
import ebooks.modelo.Livro;
import ebooks.modelo.Precificacao;
import ebooks.negocio.IStrategy;

public class GeradorPrecoLivro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Livro livro = (Livro) entidade;
		Precificacao precificacao = livro.getPrecificacao();
		GrupoPrecificacao grupoPrecificacao = livro.getGrupoPrecificacao();
		GrupoPrecificacaoDAO dao = new GrupoPrecificacaoDAO();
		List<EntidadeDominio> consulta = dao.consultar(grupoPrecificacao);
		GrupoPrecificacao grupoPrecificacaoConsultado = (GrupoPrecificacao) consulta.get(0);
		if(grupoPrecificacaoConsultado == null) {
			return "Grupo de precificação não encontrado:";
		}
		double precoVenda = 0;
		double margemLucro = grupoPrecificacaoConsultado.getMargemLucro();
		double precoCusto = precificacao.getPrecoCusto();
		precoVenda = precoCusto + (precoCusto * (margemLucro / 100));
		precificacao.setPrecoVenda(precoVenda);
		livro.setPrecificacao(precificacao);
		entidade = livro;
		return null;
	}
	
}
