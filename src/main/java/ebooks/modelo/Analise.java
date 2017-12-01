package ebooks.modelo;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jfree.chart.JFreeChart;

public class Analise extends EntidadeDominio {
	private HttpSession session;
	private JFreeChart grafico;
	private List<Categoria> categorias;
	private Date dataInicial;
	private Date dataFinal;
	private BufferedImage imagem;

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

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

	public BufferedImage getImagem() {
		return imagem;
	}

	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
	}
}
