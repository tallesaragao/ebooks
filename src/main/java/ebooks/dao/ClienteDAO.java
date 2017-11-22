package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.CartaoCredito;
import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;
import ebooks.modelo.Pedido;
import ebooks.modelo.Telefone;
import ebooks.modelo.TipoTelefone;
import ebooks.modelo.Troca;

public class ClienteDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Cliente cliente = (Cliente) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into pessoa (nome, dt_cadastro) values(?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getNome());
			ps.setDate(2, new Date(cliente.getDataCadastro().getTime()));
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			Long idPessoa = Long.valueOf(0);
			while(generatedKeys.next()) {
				idPessoa = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into pessoa_fisica(cpf, dt_nascimento, id_pessoa) values(?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getCpf());
			ps.setDate(2, new Date(cliente.getDataNascimento().getTime()));
			ps.setLong(3, idPessoa);
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idPessoaFisica = Long.valueOf(0);
			while(generatedKeys.next()) {
				idPessoaFisica = generatedKeys.getLong(1);
			}
			ps.close();
			
			sql = "insert into telefone(ddd, numero, id_tipo_telefone) values(?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getTelefone().getDdd());
			ps.setString(2, cliente.getTelefone().getNumero());
			ps.setLong(3, cliente.getTelefone().getTipoTelefone().getId());
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			Long idTelefone = Long.valueOf(0);
			while(generatedKeys.next()) {
				idTelefone = generatedKeys.getLong(1);
			}
			ps.close();

			LoginDAO loginDAO = new LoginDAO();
			Login login = cliente.getLogin();
			login.setCliente(cliente);
			if(!loginDAO.salvar(login)) {
				conexao.rollback();
				return false;
			}
			
			sql = "insert into cliente (email, fl_ativo, genero, id_pessoa_fisica, id_telefone, id_login) values(?,?,?,?,?,?)";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getEmail());
			ps.setBoolean(2, cliente.isAtivo());
			ps.setString(3, String.valueOf(cliente.getGenero()));
			ps.setLong(4, idPessoaFisica);
			ps.setLong(5, idTelefone);
			ps.setLong(6, login.getId());
			ps.execute();
			generatedKeys = ps.getGeneratedKeys();
			while(generatedKeys.next()) {
				cliente.setId(generatedKeys.getLong(1));
			}
			ps.close();
			
			Endereco endereco = cliente.getEnderecos().get(0);
			endereco.setPessoa(cliente);
			EnderecoDAO endDAO = new EnderecoDAO();
			if(!endDAO.salvar(endereco)) {
				conexao.rollback();
				return false;
			}
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
		Cliente cliente = (Cliente) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "select pf.id_pessoa_fisica, p.id_pessoa from cliente c"
						+ " join pessoa_fisica pf on (c.id_pessoa_fisica = pf.id_pessoa_fisica)"
						+ " join pessoa p on (pf.id_pessoa = p.id_pessoa)"
						+ " where c.id_cliente = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, cliente.getId());
			ResultSet rs = ps.executeQuery();
			Long idPessoa = Long.valueOf(0);
			Long idPessoaFisica = Long.valueOf(0);
			while(rs.next()) {
				idPessoa = rs.getLong("p.id_pessoa");
				idPessoaFisica = rs.getLong("pf.id_pessoa_fisica");
			}
			
			sql = "update pessoa set nome=? where id_pessoa=?";
			ps = conexao.prepareStatement(sql);
			ps.setString(1, cliente.getNome());
			ps.setLong(2, idPessoa);
			ps.execute();
			ps.close();
			
			sql = "update pessoa_fisica set cpf=?, dt_nascimento=? where id_pessoa_fisica=?";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getCpf());
			ps.setDate(2, new Date(cliente.getDataNascimento().getTime()));
			ps.setLong(3, idPessoaFisica);
			ps.execute();
			ps.close();
			
			sql = "update telefone set ddd=?, numero=?, id_tipo_telefone=? where id_telefone=?";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getTelefone().getDdd());
			ps.setString(2, cliente.getTelefone().getNumero());
			ps.setLong(3, cliente.getTelefone().getTipoTelefone().getId());
			ps.setLong(4, cliente.getTelefone().getId());
			ps.execute();
			ps.close();
			
			sql = "update cliente set email=?, fl_ativo=?, genero=? where id_cliente=?";
			ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getEmail());
			ps.setBoolean(2, cliente.isAtivo());
			ps.setString(3, String.valueOf(cliente.getGenero()));
			ps.setLong(4, cliente.getId());
			ps.execute();
			ps.close();
			
			sql = "update telefone set ddd=?, numero=?, id_tipo_telefone=? where id_telefone=?";ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getTelefone().getDdd());
			ps.setString(2, cliente.getTelefone().getNumero());
			ps.setLong(3, cliente.getTelefone().getTipoTelefone().getId());
			ps.setLong(4, cliente.getTelefone().getId());
			ps.execute();
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
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		Cliente cliente = (Cliente) entidade;
		Cliente clienteOld = new Cliente();
		clienteOld.setId(cliente.getId());
		List<EntidadeDominio> resultado = consultar(clienteOld);
		clienteOld = (Cliente) resultado.get(0);
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "select pf.id_pessoa_fisica, p.id_pessoa, c.id_telefone from cliente c"
					+ " join pessoa_fisica pf on (c.id_pessoa_fisica = pf.id_pessoa_fisica)"
					+ " join pessoa p on (pf.id_pessoa = p.id_pessoa)"
					+ " where c.id_cliente = ?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, cliente.getId());
			ResultSet rs = ps.executeQuery();
			Long idPessoa = Long.valueOf(0);
			Long idPessoaFisica = Long.valueOf(0);
			Long idTelefone = Long.valueOf(0);
			while(rs.next()) {
				idPessoa = rs.getLong("p.id_pessoa");
				idPessoaFisica = rs.getLong("pf.id_pessoa_fisica");
				idTelefone = rs.getLong("c.id_telefone");
			}
			ps.close();
			
			CartaoCreditoDAO ccDAO = new CartaoCreditoDAO();
			for(CartaoCredito cartaoCredito : clienteOld.getCartoesCredito()) {
				cartaoCredito.setCliente(cliente);
				ccDAO.excluir(cartaoCredito);
			}
			EnderecoDAO endDAO = new EnderecoDAO();
			for(Endereco end : clienteOld.getEnderecos()) {
				end.setPessoa(cliente);
				endDAO.excluir(end);
			}
			if(conexao.isClosed()) {
				conexao = factory.getConnection();
				conexao.setAutoCommit(false);
			}

			
			sql = "delete from cliente where id_cliente=?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, cliente.getId());
			ps.execute();
			ps.close();
			
			sql = "delete from pessoa_fisica where id_pessoa_fisica=?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, idPessoaFisica);
			ps.execute();
			ps.close();
			
			sql = "delete from pessoa where id_pessoa=?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, idPessoa);
			ps.execute();
			ps.close();
			
			sql = "delete from telefone where id_telefone=?";
			ps = conexao.prepareStatement(sql);
			ps.setLong(1, idTelefone);
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
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		List<EntidadeDominio> consulta = new ArrayList<>();
		Cliente clienteConsulta = (Cliente) entidade;
		conexao = factory.getConnection();
		String sql = "select * from cliente c"
					+ " join pessoa_fisica pf on (c.id_pessoa_fisica = pf.id_pessoa_fisica)"
					+ " join pessoa p on (pf.id_pessoa = p.id_pessoa)"
					+ " join telefone t on (c.id_telefone = t.id_telefone)"
					+ " join tipo_telefone te on (te.id_tipo_telefone = t.id_tipo_telefone)"
					+ " join login l on (l.id_login = c.id_login)"
					+ " where p.nome like ? and pf.cpf like ? and c.genero like ? and c.email like ?";
		if(clienteConsulta.getId() != null) {
			sql += "and c.id_cliente=?";
		}
		else if(clienteConsulta.getLogin() != null && clienteConsulta.getLogin().getId() != null) {
			sql += "and c.id_login=?";
		}
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, "%%");
			if(clienteConsulta.getNome() != null) {
				ps.setString(1, "%" + clienteConsulta.getNome() +"%");
			}
			ps.setString(2, "%%");
			if(clienteConsulta.getCpf() != null) {
				ps.setString(2, "%" + clienteConsulta.getCpf() +"%");
			}
			ps.setString(3, "%%");
			if(clienteConsulta.getGenero() != null) {
				ps.setString(3, "%" + String.valueOf(clienteConsulta.getGenero()) +"%");
			}
			ps.setString(4, "%%");
			if(clienteConsulta.getEmail() != null) {
				ps.setString(4, "%" + clienteConsulta.getEmail() +"%");
			}
			if(clienteConsulta.getId() != null) {
				ps.setLong(5, clienteConsulta.getId());
			}
			else if(clienteConsulta.getLogin() != null && clienteConsulta.getLogin().getId() != null) {
				ps.setLong(5, clienteConsulta.getLogin().getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getLong("c.id_cliente"));
				cliente.setEmail(rs.getString("c.email"));
				cliente.setAtivo(rs.getBoolean("c.fl_ativo"));
				cliente.setGenero(rs.getString("c.genero").charAt(0));
				cliente.setCpf(rs.getString("pf.cpf"));
				cliente.setDataNascimento(rs.getDate("pf.dt_nascimento"));
				cliente.setDataCadastro(rs.getDate("p.dt_cadastro"));
				cliente.setNome(rs.getString("p.nome"));
				
				Telefone telefone = new Telefone();
				TipoTelefone tipoTelefone = new TipoTelefone();
				tipoTelefone.setId(rs.getLong("te.id_tipo_telefone"));
				tipoTelefone.setNome(rs.getString("te.nome"));
				telefone.setTipoTelefone(tipoTelefone);
				telefone.setId(rs.getLong("t.id_telefone"));
				telefone.setDdd(rs.getString("t.ddd"));
				telefone.setNumero(rs.getString("t.numero"));
				cliente.setTelefone(telefone);
				
				Login login = new Login();
				login.setId(rs.getLong("l.id_login"));
				login.setUsuario(rs.getString("l.usuario"));
				login.setSenha(rs.getString("l.senha"));
				login.setDataCadastro(rs.getDate("l.dt_cadastro"));
				cliente.setLogin(login);
				
				consulta.add(cliente);
			}
			ps.close();

			List<Cliente> clientes = new ArrayList<>();
			for(EntidadeDominio ent : consulta) {
				clientes.add((Cliente) ent);
			}
			
			CartaoCreditoDAO ccDAO = new CartaoCreditoDAO();
			EnderecoDAO endDAO = new EnderecoDAO();
			PedidoDAO pedidoDAO = new PedidoDAO();
			TrocaDAO trocaDAO = new TrocaDAO();
			for(Cliente cliente : clientes) {
				CartaoCredito cartaoCredito = new CartaoCredito();
				cartaoCredito.setCliente(cliente);
				List<EntidadeDominio> cartoesCreditoConsulta = ccDAO.consultar(cartaoCredito);
				List<CartaoCredito> cartoesCredito = new ArrayList<>();
				for(EntidadeDominio ent : cartoesCreditoConsulta) {
					cartoesCredito.add((CartaoCredito) ent);
				}
				cliente.setCartoesCredito(cartoesCredito);
				
				Endereco endereco = new Endereco();
				endereco.setPessoa(cliente);
				List<EntidadeDominio> enderecosConsulta = endDAO.consultar(endereco);
				List<Endereco> enderecos = new ArrayList<>();
				for(EntidadeDominio ent : enderecosConsulta) {
					enderecos.add((Endereco) ent);
				}
				cliente.setEnderecos(enderecos);
				
				Pedido pedido = new Pedido();
				pedido.setCliente(cliente);
				List<EntidadeDominio> pedidosConsulta = pedidoDAO.consultar(pedido);
				List<Pedido> pedidos = new ArrayList<>();
				for(EntidadeDominio ent : pedidosConsulta) {
					pedidos.add((Pedido) ent);
				}
				cliente.setPedidos(pedidos);
				
				Troca troca = new Troca();
				troca.setCliente(cliente);
				List<EntidadeDominio> trocasConsulta = trocaDAO.consultar(troca);
				List<Troca> trocas = new ArrayList<>();
				for(EntidadeDominio ent : trocasConsulta) {
					trocas.add((Troca) ent);
				}
				cliente.setTrocas(trocas);
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
