package ebooks.negocio.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ebooks.dao.IDAO;
import ebooks.dao.PedidoDAO;
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
		IDAO dao = new PedidoDAO();
		try {
			List<EntidadeDominio> pedidos = dao.consultar(new Pedido());
			if(!pedidos.isEmpty()) {
				XYSeriesCollection dataset = new XYSeriesCollection();
				for(Categoria categoria : analise.getCategorias()) {
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
						List<ItemPedido> itensPedido = pedido.getItensPedido();
						for(ItemPedido item : itensPedido) {
							List<Categoria> categoriasItem = item.getLivro().getCategorias();
							for(Categoria categoriaItem : categoriasItem) {
								if(categoria.getId().longValue() == categoriaItem.getId().longValue()) {
									quantidadesVendidasCategoriaMes[mes] += item.getQuantidade();
								}
							}
							
						}
					}
					for(int i = 0; i < quantidadesVendidasCategoriaMes.length; i++) {
						long quantidadeVendida = quantidadesVendidasCategoriaMes[i];
						series.add(i+1, quantidadeVendida);
					}
					dataset.addSeries(series);
				}
				String title = "Volume de vendas por categoria e período";
				String xAxisLabel = "Mês";
				String yAxisLabel = "Volume de vendas";
				JFreeChart grafico = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, 
						dataset, PlotOrientation.HORIZONTAL, true, true, false);
				OutputStream out = new FileOutputStream("D:\\Users\\tallesaragao\\Downloads\\grafico.png");
				ChartUtilities.writeChartAsPNG(out, grafico, 550, 400);
			}
			else {
				sb.append("Nenhum pedido encontrado");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sb.append("Problema na geração do gráfico:");
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
