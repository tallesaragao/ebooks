package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.TipoEndereco;
import ebooks.modelo.EntidadeDominio;

public class TipoEnderecoDAO extends AbstractDAO {

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
		TipoEndereco tipoEndereco = (TipoEndereco) entidade;
		List<EntidadeDominio> tiposEndereco = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			String sql = "select * from tipo_endereco ";
			if(tipoEndereco.getId() != null) {
				sql += "where id_tipo_endereco = ?";
			}
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(tipoEndereco.getId() != null) {
				ps.setLong(1, tipoEndereco.getId());				
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				TipoEndereco tipoEnderecoConsultado = new TipoEndereco();
				tipoEnderecoConsultado.setId(rs.getLong("id_tipo_endereco"));
				tipoEnderecoConsultado.setNome(rs.getString("nome"));
				tipoEnderecoConsultado.setDataCadastro(rs.getDate("dt_cadastro"));
				tiposEndereco.add(tipoEnderecoConsultado);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}
		return tiposEndereco;
	}

}
