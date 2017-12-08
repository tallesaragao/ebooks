package ebooks.negocio.impl;

import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ebooks.dao.AnaliseDAO;
import ebooks.dao.CategoriaDAO;
import ebooks.dao.IDAO;
import ebooks.modelo.Analise;
import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class GerarGraficoAnalise implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Analise analise = (Analise) entidade;
		Date dataInicial = analise.getDataInicial();
		Date dataFinal = analise.getDataFinal();
		IDAO analiseDAO = new AnaliseDAO();
		if(analise.getCategorias() != null && !analise.getCategorias().isEmpty()) {
			try {
				if(analise.getDataInicial() == null || analise.getDataFinal() == null) {
					sb.append("As datas devem ser informadas:");
				}
				else if(analise.getDataInicial().getTime() >= analise.getDataFinal().getTime()) {
					sb.append("A data inicial deve ser menor que a data final:");
				}
				else {
					Calendar calendarioInicial = Calendar.getInstance();
					calendarioInicial.setTime(analise.getDataInicial());
					Calendar calendarioFinal = Calendar.getInstance();
					calendarioFinal.setTime(analise.getDataFinal());

					int diferencaAnos = calendarioFinal.get(Calendar.YEAR) - calendarioInicial.get(Calendar.YEAR);
					int diferencaMeses = diferencaAnos * 12 + calendarioFinal.get(Calendar.MONTH) - calendarioInicial.get(Calendar.MONTH);
					if(diferencaMeses < 2) {
						sb.append("O intervalo de tempo mínimo é de 3 meses");
					}
					if(diferencaMeses > 11) {
						sb.append("O intervalo de tempo máximo é de 12 meses");
					}
				}
				if(sb.length() > 0) {
					return sb.toString();
				}
				List<EntidadeDominio> pedidos = analiseDAO.consultar(analise);
				DefaultCategoryDataset defDataset = new DefaultCategoryDataset();
				Calendar cal = Calendar.getInstance();
				for(Categoria categoria : analise.getCategorias()) {
					IDAO categoriaDAO = new CategoriaDAO();
					List<EntidadeDominio> consulta = categoriaDAO.consultar(categoria);
					if(!consulta.isEmpty()) {
						categoria = (Categoria) consulta.get(0);
						String[] meses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
								"Jul", "Ago", "Set", "Out", "Nov", "Dez"};
						cal.setTime(dataInicial);
						int ano = cal.get(Calendar.YEAR);
						int mes = cal.get(Calendar.MONTH);
						cal.setTime(dataFinal);
						int anoFinal = cal.get(Calendar.YEAR);
						int mesFinal = cal.get(Calendar.MONTH);
						while(true) {
							String rotulo = meses[mes] + "/" + ano;
							defDataset.addValue(0, categoria.getNome(), rotulo);
							if(ano == anoFinal && mes == mesFinal) {
								break;
							}
							if(mes < 11) {
								mes++;
							}
							else if(mes == 11) {
								if(ano != anoFinal) {
									mes = 0;
									ano++;
								}
							}
						}
						for(EntidadeDominio ent : pedidos) {
							Pedido pedido = (Pedido) ent;
							long quantidadeVendida = 0;
							List<ItemPedido> itensPedido = pedido.getItensPedido();
							for(ItemPedido item : itensPedido) {
								List<Categoria> categoriasItem = item.getLivro().getCategorias();
								for(Categoria categoriaItem : categoriasItem) {
									if(categoria.getId() == categoriaItem.getId()) {
										quantidadeVendida += item.getQuantidade();
									}
								}									
							}
							cal.setTime(pedido.getDataCadastro());
							int mesPedido = cal.get(Calendar.MONTH);
							int anoPedido = cal.get(Calendar.YEAR);
							String rotulo = meses[mesPedido] + "/" + anoPedido;
							Number quantidadeAnterior = defDataset.getValue(categoria.getNome(), rotulo);
							quantidadeVendida += quantidadeAnterior.longValue();
							defDataset.setValue(quantidadeVendida, categoria.getNome(), rotulo);
						}
					}
				}
				String title = "Volume de vendas por categoria e período";
				String xAxisLabel = "Período (Mês/Ano)";
				String yAxisLabel = "Volume de vendas (unidade)";
				JFreeChart grafico = ChartFactory.createLineChart(title, xAxisLabel, yAxisLabel, 
						defDataset, PlotOrientation.VERTICAL, true, true, false);
				BufferedImage imagem = grafico.createBufferedImage(1100, 600);
				analise.setGrafico(imagem);
			} catch (Exception e) {
				e.printStackTrace();
				sb.append("Problema na geração do gráfico:");
			}
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
