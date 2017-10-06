package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Log;

public class AuditoriaDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Log log = (Log) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into log(nome_entidade, usuario, operacao, dt_cadastro, estado_anterior) values(?,?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, log.getNomeEntidade());
			ps.setString(2, log.getUsuario());
			ps.setString(3, log.getOperacao());
			ps.setDate(4, new Date(log.getDataCadastro().getTime()));
			ps.setString(5, log.getEstadoAnterior());
			ps.execute();
			ps.close();
			conexao.commit();
			return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();
		}
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
		// TODO Auto-generated method stub
		return null;
	}

}
