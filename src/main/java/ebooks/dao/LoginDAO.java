package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;

public class LoginDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Login login = (Login) entidade;
		Cliente clienteLogin = login.getCliente();
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into login(usuario, senha, dt_cadastro) values(?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, login.getUsuario());
			ps.setString(2, login.getSenha());
			ps.setDate(3, new Date(clienteLogin.getDataCadastro().getTime()));
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
		Login login = (Login) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "update login set usuario=?, senha=? where id_login=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, login.getUsuario());
			ps.setString(2, login.getSenha());
			ps.setLong(3, login.getId());
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
		Login login = (Login) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "delete from login where id_login=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, login.getId());
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
		Login loginConsulta = (Login) entidade;
		List<EntidadeDominio> consulta = new ArrayList<>();
		conexao = factory.getConnection();
		try {
			Long idLoginConsulta = loginConsulta.getId();
			String sql = "";
			PreparedStatement ps = null;
			if(idLoginConsulta != null) {
				sql = "select * from login where id_login=?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, loginConsulta.getId());
			}
			else {
				sql = "select * from login where usuario=? AND senha=?";
				
				ps = conexao.prepareStatement(sql);
				ps.setString(1, "%%");
				if(loginConsulta.getUsuario() != null) {
					ps.setString(1, loginConsulta.getUsuario());				
				}
				ps.setString(2, "%%");
				if(loginConsulta.getSenha() != null) {
					ps.setString(2, loginConsulta.getSenha());
				}
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Login login = new Login();
				login.setId(rs.getLong("id_login"));
				login.setUsuario(rs.getString("usuario"));
				login.setSenha(rs.getString("senha"));
				consulta.add(login);
			}
			

			ClienteDAO cliDAO = new ClienteDAO();
			for(EntidadeDominio ent : consulta) {
				Login login = (Login) ent;
				Cliente clienteConsulta = new Cliente();
				clienteConsulta.setLogin(login);
				List<EntidadeDominio> consultaCliente = cliDAO.consultar(clienteConsulta);
				if(!consultaCliente.isEmpty()) {
					Cliente cliente = (Cliente) consultaCliente.get(0);
					login.setCliente(cliente);
				}
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
