package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.ItemTroca;

public class ItemTrocaDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		ItemTroca item = (ItemTroca) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into item_troca(quant_trocada, quant_retornavel, id_item_pedido, id_troca) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setLong(1, item.getQuantidadeTrocada());
			ps.setLong(2, item.getQuantidadeRetornavel() == null ? 0 : item.getQuantidadeRetornavel());
			ps.setLong(3, item.getItemPedido().getId());
			ps.setLong(4, item.getTroca().getId());
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			while(generatedKeys.next()) {
				entidade.setId(generatedKeys.getLong(1));
			}
			ps.close();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			conexao.rollback();
			return false;
		}
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) throws SQLException {
		//Implementar
		return false;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		//Implementar
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		List<EntidadeDominio> consulta = new ArrayList<>();
		ItemTroca itemConsulta = (ItemTroca) entidade;
		conexao = factory.getConnection();
		String sql = "select * from item_troca it ";
		if(itemConsulta.getId() != null) {
			sql += " where it.id_item_troca = ?";
		}
		else {
			if(itemConsulta.getTroca() != null && itemConsulta.getTroca().getId() != null) {
				sql += " where it.id_troca = ?";
			}
		}
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(itemConsulta.getId() != null) {
				ps.setLong(1, itemConsulta.getId());
			}
			else {
				if(itemConsulta.getTroca() != null && itemConsulta.getTroca().getId() != null) {
					ps.setLong(1, itemConsulta.getTroca().getId());
				}
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ItemTroca item = new ItemTroca();
				item.setId(rs.getLong("it.id_item_pedido"));
				item.setQuantidadeTrocada(rs.getLong("it.quant_trocada"));
				item.setQuantidadeRetornavel(rs.getLong("it.quant_retornavel"));
				ItemPedido itemPedido = new ItemPedido();
				itemPedido.setId(rs.getLong("it.id_item_pedido"));
				item.setItemPedido(itemPedido);
				consulta.add(item);
			}
			ps.close();
			
			for(EntidadeDominio ent : consulta) {
				ItemTroca item = (ItemTroca) ent;
				ItemPedido itemPedido = item.getItemPedido();
				IDAO itemPedidoDAO = new ItemPedidoDAO();
				List<EntidadeDominio> consultaItemPedido = itemPedidoDAO.consultar(itemPedido);
				itemPedido = (ItemPedido) consultaItemPedido.get(0);
				item.setItemPedido(itemPedido);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}
		return consulta;
	}

}
