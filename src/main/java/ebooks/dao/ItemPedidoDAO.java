package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Pagamento;

public class ItemPedidoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		ItemPedido item = (ItemPedido) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into item_pedido(quantidade, subtotal, id_pedido, id_livro) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setLong(1, item.getQuantidade());
			ps.setBigDecimal(2, item.getSubtotal());
			ps.setLong(3, item.getPedido().getId());
			ps.setLong(4, item.getLivro().getId());
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
		ItemPedido itemConsulta = (ItemPedido) entidade;
		conexao = factory.getConnection();
		String sql = "select * from item_pedido ip ";
		if(itemConsulta.getId() != null) {
			sql += " where ip.id_item_pedido = ?";
		}
		else {
			if(itemConsulta.getPedido() != null && itemConsulta.getPedido().getId() != null) {
				sql += " where ip.id_pedido = ?";
			}
		}
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(itemConsulta.getId() != null) {
				ps.setLong(1, itemConsulta.getId());
			}
			else {
				if(itemConsulta.getPedido() != null && itemConsulta.getPedido().getId() != null) {
					ps.setLong(1, itemConsulta.getPedido().getId());
				}
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ItemPedido item = new ItemPedido();
				item.setId(rs.getLong("ip.id_item_pedido"));
				item.setQuantidade(rs.getLong("ip.quantidade"));
				item.setSubtotal(rs.getBigDecimal("ip.subtotal"));
				Livro livro = new Livro();
				livro.setId(rs.getLong("ip.id_livro"));
				item.setLivro(livro);
				consulta.add(item);
			}
			ps.close();
			
			for(EntidadeDominio ent : consulta) {
				ItemPedido item = (ItemPedido) ent;
				Livro livro = item.getLivro();
				IDAO livroDAO = new LivroDAO();
				List<EntidadeDominio> consultaLivro = livroDAO.consultar(livro);
				livro = (Livro) consultaLivro.get(0);
				item.setLivro(livro);
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
