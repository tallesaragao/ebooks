package ebooks.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ebooks.modelo.Analise;
import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Pedido;

public class AnaliseDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		Analise analise = (Analise) entidade;
		List<Categoria> categoriasAnalise = analise.getCategorias();
		Date dataInicial = analise.getDataInicial();
		Date dataFinal = analise.getDataFinal();
		List<EntidadeDominio> consulta = new ArrayList<>();
		IDAO dao = new PedidoDAO();
		List<EntidadeDominio> pedidos = dao.consultar(new Pedido());
		if(!pedidos.isEmpty()) {
			for(EntidadeDominio ent : pedidos) {
				Pedido pedido = (Pedido) ent;
				Date dataPedido = pedido.getDataCadastro();
				if(dataPedido.getTime() < dataInicial.getTime() || dataPedido.getTime() > dataFinal.getTime()) {
					continue;
				}
				List<Categoria> categoriasPedido = new ArrayList<>();
				List<ItemPedido> itensPedido = pedido.getItensPedido();
				for(ItemPedido item : itensPedido) {
					Livro livro = item.getLivro();
					List<Categoria> categoriasLivro = livro.getCategorias();
					for(Categoria categoria : categoriasLivro) {
						categoriasPedido.add(categoria);
					}
				}
				for(Categoria categoria : categoriasPedido) {
					for(Categoria categoriaAnalise : categoriasAnalise) {
						if(categoria.getId() == categoriaAnalise.getId()) {
							boolean pedidoAdicionado = false;
							for(EntidadeDominio entPedido : consulta) {
								if(pedido.getId() == entPedido.getId()) {
									pedidoAdicionado = true;
									break;
								}
							}
							if(!pedidoAdicionado) {
								consulta.add(pedido);
							}
							continue;
						}
					}
				}
			}
		}
		return consulta;
	}

}
