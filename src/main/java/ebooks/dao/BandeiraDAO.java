package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Bandeira;

public class BandeiraDAO extends AbstractDAO {

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
		Bandeira bandeira = (Bandeira) entidade;
		List<EntidadeDominio> bandeiras = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			String sql = "select * from bandeira ";
			if(bandeira.getId() != null) {
				sql += "where id_bandeira = ?";
			}
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(bandeira.getId() != null) {
				ps.setLong(1, bandeira.getId());				
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Bandeira bandeiraConsultada = new Bandeira();
				bandeiraConsultada.setId(rs.getLong("id_bandeira"));
				bandeiraConsultada.setNome(rs.getString("nome"));
				bandeiras.add(bandeiraConsultada);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}
		return bandeiras;
	}

}
