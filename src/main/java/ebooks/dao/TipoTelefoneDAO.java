package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.TipoTelefone;

public class TipoTelefoneDAO extends AbstractDAO {

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
		TipoTelefone tipoTelefone = (TipoTelefone) entidade;
		List<EntidadeDominio> tiposTelefone = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			String sql = "select * from tipo_telefone ";
			if(tipoTelefone.getId() != null) {
				sql += "where id_tipo_telefone = ?";
			}
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(tipoTelefone.getId() != null) {
				ps.setLong(1, tipoTelefone.getId());				
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				TipoTelefone tipoTelefoneConsultado = new TipoTelefone();
				tipoTelefoneConsultado.setId(rs.getLong("id_tipo_telefone"));
				tipoTelefoneConsultado.setNome(rs.getString("nome"));
				tiposTelefone.add(tipoTelefoneConsultado);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}
		return tiposTelefone;
	}

}
