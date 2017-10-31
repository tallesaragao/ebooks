package ebooks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.GrupoPrecificacao;

public class GrupoPrecificacaoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		GrupoPrecificacao grupoPrecificacao = (GrupoPrecificacao) entidade;
		List<EntidadeDominio> gruposPrecificacao = new ArrayList<>();
		try {
			conexao = factory.getConnection();
			String sql = "select * from grupo_precificacao ";
			if(entidade.getId() != null) {
				sql += "where id_grupo_precificacao = ?";
			}
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(grupoPrecificacao.getId() != null) {
				ps.setLong(1, grupoPrecificacao.getId());				
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GrupoPrecificacao grupoPrecificacaoConsultado = new GrupoPrecificacao();
				grupoPrecificacaoConsultado.setId(rs.getLong("id_grupo_precificacao"));
				grupoPrecificacaoConsultado.setNome(rs.getString("nome"));
				grupoPrecificacaoConsultado.setMargemLucro(rs.getBigDecimal("margem_lucro"));
				grupoPrecificacaoConsultado.setDataCadastro(rs.getDate("dt_cadastro"));
				gruposPrecificacao.add(grupoPrecificacaoConsultado);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return gruposPrecificacao;
	}

}
