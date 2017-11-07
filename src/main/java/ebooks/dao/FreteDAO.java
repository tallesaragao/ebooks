package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Frete;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Frete;

public class FreteDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Frete frete = (Frete) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into frete(valor, dias_entrega, prazo_estimado) values(?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setBigDecimal(1, frete.getValor());
			ps.setLong(2, frete.getDiasEntrega());
			ps.setDate(3, new Date(frete.getPrazoEstimado().getTime()));
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
		Frete freteConsulta = (Frete) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idFreteConsulta = freteConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idFreteConsulta != null) {
				sql = "select * from frete where id_frete=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, freteConsulta.getId());
			}
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Frete frete = new Frete();
				frete.setId(rs.getLong("id_frete"));
				frete.setValor(rs.getBigDecimal("valor"));
				frete.setDiasEntrega(rs.getLong("dias_entrega"));
				frete.setPrazoEstimado(rs.getDate("prazo_estimado"));
				consulta.add(frete);
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
