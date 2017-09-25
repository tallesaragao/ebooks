package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;

public class CategoriaDAO extends AbstractDAO {
	

	@Override
	public boolean salvar(EntidadeDominio entidade) {
		try {
			Categoria categoria = (Categoria) entidade;
			conexao = factory.getConnection();
			String sql = "insert into categoria(dt_cadastro, nome) values(?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			Timestamp dataSql = new Timestamp(categoria.getDataCadastro().getTime());
			ps.setTimestamp(1, dataSql);
			ps.setString(2, categoria.getNome());
			ps.execute();
			ps.close();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) {
		try {
			Categoria categoria = (Categoria) entidade;
			conexao = factory.getConnection();
			String sql = "update categoria set nome=? where id_categoria=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, categoria.getNome());
			ps.setLong(2, categoria.getId());
			ps.execute();
			ps.close();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		List<EntidadeDominio> categorias = new ArrayList<>();
		try {
			Categoria categoria = (Categoria) entidade;
			conexao = factory.getConnection();
			String sql = "select * from categoria where nome like ?";
			if(categoria.getId() != null) {
				sql += " and id_categoria = ?";
			}
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(categoria.getNome() == null) {
				categoria.setNome("");
			}
			ps.setString(1, "%" + categoria.getNome() + "%");
			if(categoria.getId() != null) {
				ps.setLong(2, categoria.getId());
			}
			ResultSet rs = ps.executeQuery();		
			while(rs.next()) {
				Categoria categoriaConsultada = new Categoria();
				categoriaConsultada.setId(rs.getLong("categoria.id_categoria"));
				Timestamp timestamp = rs.getTimestamp("categoria.dt_cadastro");
				categoriaConsultada.setDataCadastro(timestamp);
				categoriaConsultada.setNome(rs.getString("categoria.nome"));
				categorias.add(categoriaConsultada);
			}
			ps.close();	
			conexao.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return categorias;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) {
		conexao = factory.getConnection();
		Categoria categoria = (Categoria) entidade;
		String sql = "delete from categoria where id_categoria=?";
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, categoria.getId());
			ps.execute();
			ps.close();			
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
