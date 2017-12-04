package ebooks.negocio.impl;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ebooks.dao.CategoriaDAO;
import ebooks.dao.IDAO;
import ebooks.dao.PedidoDAO;
import ebooks.modelo.Analise;
import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class GerarGraficoAnalise implements IStrategy {
	
	private class ItemGrafico {
		private long quantidade = 0;
		private long mes;
		private long ano;
		private Categoria categoria;
		public ItemGrafico itemGrafico;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Analise analise = (Analise) entidade;
		Date dataInicial = analise.getDataInicial();
		Date dataFinal = analise.getDataFinal();
		IDAO pedidoDAO = new PedidoDAO();
		if(analise.getCategorias() != null && !analise.getCategorias().isEmpty()) {
			try {
				List<ItemGrafico> itensGrafico = new ArrayList<>();
				List<EntidadeDominio> pedidos = pedidoDAO.consultar(new Pedido());
				if(!pedidos.isEmpty()) {
					DefaultCategoryDataset defDataset = new DefaultCategoryDataset();
					XYSeriesCollection dataset = new XYSeriesCollection();
					for(Categoria categoria : analise.getCategorias()) {
						IDAO categoriaDAO = new CategoriaDAO();
						List<EntidadeDominio> consulta = categoriaDAO.consultar(categoria);
						if(!consulta.isEmpty()) {
							categoria = (Categoria) consulta.get(0);
							String tipo = categoria.getNome();
							XYSeries series = new XYSeries(categoria.getNome());
							long[] quantidadesVendidasCategoriaMes = new long[12];
							for(int i = 0; i < quantidadesVendidasCategoriaMes.length; i++) {
								quantidadesVendidasCategoriaMes[i] = 0;
							}
							for(EntidadeDominio ent : pedidos) {
								Pedido pedido = (Pedido) ent;
								Calendar cal = Calendar.getInstance();
								cal.setTime(pedido.getDataCadastro());
								int mes = cal.get(Calendar.MONTH);
								int ano = cal.get(Calendar.YEAR);
								ItemGrafico itemGrafico = null;
								for(ItemGrafico ig : itensGrafico) {
									if(ig.mes == mes && ig.ano == ano) {
										itemGrafico = ig;
										break;
									}
								}
								if(itemGrafico == null) {
									itemGrafico = new ItemGrafico();
									itemGrafico.ano = ano;
									itemGrafico.mes = mes;
								}
								List<ItemPedido> itensPedido = pedido.getItensPedido();
								for(ItemPedido item : itensPedido) {
									List<Categoria> categoriasItem = item.getLivro().getCategorias();
									for(Categoria categoriaItem : categoriasItem) {
										if(categoria.getId().longValue() == categoriaItem.getId().longValue()) {
											quantidadesVendidasCategoriaMes[mes] += item.getQuantidade();
											itemGrafico.quantidade += item.getQuantidade();
										}
									}
									
								}
							}
							String[] meses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
									"Jul", "Ago", "Set", "Out", "Nov", "Dez"};
							for(int i = 0; i < quantidadesVendidasCategoriaMes.length; i++) {
								String nomeMes = meses[i];
								long quantidadeVendida = quantidadesVendidasCategoriaMes[i];
								series.add(i+1, quantidadeVendida);
								defDataset.addValue(quantidadeVendida, tipo, nomeMes);
							}
							dataset.addSeries(series);
						}
					}
					String title = "Volume de vendas por categoria e período";
					String xAxisLabel = "Meses do ano de 2017";
					String yAxisLabel = "Volume de vendas (unidade)";
					//JFreeChart grafico = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, 
					//		dataset, PlotOrientation.VERTICAL, true, true, false);
					JFreeChart grafico = ChartFactory.createLineChart(title, xAxisLabel, yAxisLabel, 
							defDataset, PlotOrientation.VERTICAL, true, true, false);
					BufferedImage image = grafico.createBufferedImage(1000, 550);
					HttpSession session = analise.getSession();
					session.setAttribute("grafico", image);
					ChartUtilities.encodeAsPNG(image);
				}
				else {
					sb.append("Nenhum pedido encontrado");
				}
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
