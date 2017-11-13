package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Uri;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.PerfilAcesso;

public class PerfilAcessoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		PerfilAcesso perfiAcesso = (PerfilAcesso) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into perfil_acesso(nome) values(?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, perfiAcesso.getNome());
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
		PerfilAcesso perfilAcesso = (PerfilAcesso) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update perfil_acesso set nome=? id_perfil_acesso=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, perfilAcesso.getNome());
			ps.setLong(2, perfilAcesso.getId());
			ps.execute();
			ps.close();
			conexao.commit();
			return true;
		}
		catch(SQLException e) {
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();			
		}
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		PerfilAcesso perfilAcesso = (PerfilAcesso) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from perfil_acesso where id_perfil_acesso=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, perfilAcesso.getId());
			ps.execute();
			ps.close();
			conexao.commit();
			conexao.close();
			return true;
		}
		catch(SQLException e) {
			conexao.rollback();
			return false;
		}
		finally {
			conexao.close();			
		}
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		PerfilAcesso perfilAcessoConsulta = (PerfilAcesso) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idPerfilAcessoConsulta = perfilAcessoConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idPerfilAcessoConsulta != null) {
				sql = "select * from perfil_acesso where id_perfil_acesso=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, perfilAcessoConsulta.getId());
			}
			else {
				sql = "select * from perfil_acesso where nome=?";
				
				ps = conexao.prepareStatement(sql);
				ps.setString(1, "%%");
				if(perfilAcessoConsulta.getNome() != null) {
					ps.setString(1, perfilAcessoConsulta.getNome());				
				}
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PerfilAcesso perfilAcesso = new PerfilAcesso();
				perfilAcesso.setId(rs.getLong("id_perfil_acesso"));
				perfilAcesso.setNome(rs.getString("nome"));
				consulta.add(perfilAcesso);
			}

			UriDAO uriDAO = new UriDAO();
			for(EntidadeDominio ent : consulta) {
				PerfilAcesso perfilAcesso = (PerfilAcesso) ent;
				sql = "select * from uri_perfil up where id_perfil_acesso=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, perfilAcesso.getId());
				rs = ps.executeQuery();
				List<Uri> urisAcessiveis = new ArrayList<>();
				while(rs.next()) {
					Uri uri = new Uri();
					uri.setId(rs.getLong("up.id_uri"));
					urisAcessiveis.add(uri);
				}
				
				List<Uri> urisConsultadas = new ArrayList<>();
				for(Uri uri : urisAcessiveis) {
					List<EntidadeDominio> consultaUri = uriDAO.consultar(uri);
					if(!consultaUri.isEmpty()) {
						uri = (Uri) consultaUri.get(0);
						urisConsultadas.add(uri);
					}
				}
				perfilAcesso.setUrisAcessiveis(urisConsultadas);
			}
			
			if(conexao.isClosed()) {
				conexao = factory.getConnection();
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
