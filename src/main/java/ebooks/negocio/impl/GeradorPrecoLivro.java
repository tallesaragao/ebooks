package ebooks.negocio.impl;

import java.math.BigDecimal;
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
		BigDecimal precoVenda = new BigDecimal("0.0");
		BigDecimal margemLucro = grupoPrecificacaoConsultado.getMargemLucro();
		BigDecimal precoCusto = precificacao.getPrecoCusto();
		BigDecimal valorLucro = new BigDecimal("0.0");
		valorLucro = valorLucro.add(precoCusto);
		valorLucro = valorLucro.multiply(margemLucro);
		valorLucro = valorLucro.divide(new BigDecimal("100"));
		valorLucro = valorLucro.setScale(2, BigDecimal.ROUND_CEILING);
		precoVenda = precoVenda.add(precoCusto);
		precoVenda = precoVenda.add(valorLucro);
		precoVenda = precoVenda.setScale(2, BigDecimal.ROUND_CEILING);
		precificacao.setPrecoVenda(precoVenda);
		livro.setPrecificacao(precificacao);
		entidade = livro;
		return null;
	}
	
}
