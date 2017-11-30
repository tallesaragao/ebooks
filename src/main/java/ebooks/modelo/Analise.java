package ebooks.modelo;

import java.util.Date;
import java.util.List;

import org.jfree.chart.JFreeChart;

public class Analise extends EntidadeDominio {
	private JFreeChart grafico;
	private List<Categoria> categorias;
	private Date dataInicial;
	private Date dataFinal;

	public JFreeChart getGrafico() {
		return grafico;
	}

	public void setGrafico(JFreeChart grafico) {
		this.grafico = grafico;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
}
