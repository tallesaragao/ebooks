package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Carrinho;
import ebooks.modelo.Cliente;
import ebooks.modelo.CupomPromocional;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Frete;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Pedido;
import ebooks.modelo.Status;
import ebooks.modelo.StatusPedido;

public class PedidoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		IDAO dao = new FreteDAO();
		boolean freteSalvo = dao.salvar(pedido.getFrete());
		dao = new FormaPagamentoDAO();
		boolean formaPagamentoSalva = dao.salvar(pedido.getFormaPagamento());
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into pedido(valor_total, numero, id_endereco_entrega, id_endereco_cobranca, id_cliente,"
						+ " id_cupom_promo, id_frete, id_forma_pag, dt_cadastro) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setBigDecimal(1, pedido.getValorTotal());
			ps.setString(2, pedido.getNumero());
			ps.setLong(3, pedido.getEnderecoEntrega().getId());
			// FALTA DEFINIR O ENDEREÇO DE COBRANÇA AO CADASTRAR O CLIENTE (ENDEREÇO PRINCIPAL) - SOLUÇÃO TEMPORÁRIA
			ps.setLong(4, pedido.getEnderecoCobranca().getId());
			ps.setLong(5, pedido.getCliente().getId());
			ps.setLong(6, pedido.getCupomPromocional().getId());
			ps.setLong(7, pedido.getFrete().getId());
			ps.setLong(8, pedido.getFormaPagamento().getId());
			ps.setDate(9, new Date(pedido.getDataCadastro().getTime()));
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			while (generatedKeys.next()) {
				pedido.setId(generatedKeys.getLong(1));
			}
			ps.close();
			
			dao = new ItemPedidoDAO();
			boolean itensSalvos = false;
			for(ItemPedido item : pedido.getItensPedido()) {
				item.setPedido(pedido);
				itensSalvos = dao.salvar(item);
				if(!itensSalvos) {
					break;
				}
			}
			
			dao = new StatusPedidoDAO();
			boolean statusesSalvos = false;
			for(StatusPedido statusPedido : pedido.getStatusesPedido()) {
				statusPedido.setPedido(pedido);
				statusesSalvos = dao.salvar(statusPedido);
				if(!statusesSalvos) {
					break;
				}
			}
			
			if(freteSalvo && formaPagamentoSalva && itensSalvos && statusesSalvos) {
				conexao.commit();
				return true;				
			}
			else {
				conexao.rollback();
				return false;
			}
		} catch (SQLException e) {
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
		List<EntidadeDominio> consulta = new ArrayList<>();
		Pedido pedidoConsulta = (Pedido) entidade;
		conexao = factory.getConnection();
		String sql = "select * from pedido p ";
		if(pedidoConsulta.getId() != null) {
			sql += " where p.id_pedido = ?";
		}
		else if(pedidoConsulta.getCliente() != null && pedidoConsulta.getCliente().getId() != null) {
			sql += " where p.id_cliente = ?";
		}
		
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(pedidoConsulta.getId() != null) {
				ps.setLong(1, pedidoConsulta.getId());
			}
			else if(pedidoConsulta.getCliente() != null && pedidoConsulta.getCliente().getId() != null) {
				ps.setLong(1, pedidoConsulta.getCliente().getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setId(rs.getLong("p.id_pedido"));
				pedido.setValorTotal(rs.getBigDecimal("p.valor_total"));
				pedido.setNumero(rs.getString("p.numero"));
				
				Endereco enderecoCobranca = new Endereco();
				enderecoCobranca.setId(rs.getLong("p.id_endereco_cobranca"));
				pedido.setEnderecoCobranca(enderecoCobranca);
				
				Endereco enderecoEntrega = new Endereco();
				enderecoEntrega.setId(rs.getLong("p.id_endereco_entrega"));
				pedido.setEnderecoEntrega(enderecoEntrega);
				
				Cliente cliente = new Cliente();
				cliente.setId(rs.getLong("p.id_cliente"));
				pedido.setCliente(cliente);
				
				CupomPromocional cupomPromocional = new CupomPromocional();
				cupomPromocional.setId(rs.getLong("p.id_cupom_promo"));
				pedido.setCupomPromocional(cupomPromocional);
				
				Frete frete = new Frete();
				frete.setId(rs.getLong("p.id_frete"));
				pedido.setFrete(frete);
				
				FormaPagamento formaPagamento = new FormaPagamento();
				formaPagamento.setId(rs.getLong("p.id_forma_pag"));
				pedido.setFormaPagamento(formaPagamento);
				
				pedido.setDataCadastro(rs.getDate("p.dt_cadastro"));
				
				consulta.add(pedido);
			}
			ps.close();

			List<Pedido> pedidos = new ArrayList<>();
			for(EntidadeDominio ent : consulta) {
				pedidos.add((Pedido) ent);
			}
			
			EnderecoDAO endDAO = new EnderecoDAO();
			ClienteDAO cliDAO = new ClienteDAO();
			CupomPromocionalDAO cupomDAO = new CupomPromocionalDAO();
			FreteDAO freteDAO = new FreteDAO();
			FormaPagamentoDAO formaPagDAO = new FormaPagamentoDAO();
			StatusPedidoDAO statusPedidoDAO = new StatusPedidoDAO();
			ItemPedidoDAO itemDAO = new ItemPedidoDAO();
			for(Pedido pedido : pedidos) {
				
				Endereco enderecoCobranca = pedido.getEnderecoCobranca();
				List<EntidadeDominio> consultaEntidades = endDAO.consultar(enderecoCobranca);
				if(!consultaEntidades.isEmpty()) {
					pedido.setEnderecoCobranca((Endereco) consultaEntidades.get(0));
				}
				
				Endereco enderecoEntrega = pedido.getEnderecoEntrega();
				consultaEntidades = endDAO.consultar(enderecoEntrega);
				if(!consultaEntidades.isEmpty()) {
					pedido.setEnderecoEntrega((Endereco) consultaEntidades.get(0));
				}
				if(pedidoConsulta.getCliente() == null) {
					Cliente cliente = pedido.getCliente();
					consultaEntidades = cliDAO.consultar(cliente);
					if(!consultaEntidades.isEmpty()) {
						pedido.setCliente((Cliente) consultaEntidades.get(0));
					}
				}
				
				CupomPromocional cupomPromocional = pedido.getCupomPromocional();
				consultaEntidades = cupomDAO.consultar(cupomPromocional);
				if(!consultaEntidades.isEmpty()) {
					pedido.setCupomPromocional((CupomPromocional) consultaEntidades.get(0));
				}
				
				Frete frete = pedido.getFrete();
				consultaEntidades = freteDAO.consultar(frete);
				if(!consultaEntidades.isEmpty()) {
					pedido.setFrete((Frete) consultaEntidades.get(0));
				}
				
				FormaPagamento formaPagamento = pedido.getFormaPagamento();
				consultaEntidades = formaPagDAO.consultar(formaPagamento);
				if(!consultaEntidades.isEmpty()) {
					pedido.setFormaPagamento((FormaPagamento) consultaEntidades.get(0));
				}
				
				StatusPedido statusPedido = new StatusPedido();
				statusPedido.setPedido(pedido);
				consultaEntidades = statusPedidoDAO.consultar(statusPedido);
				List<StatusPedido> statusesPedido = new ArrayList<>();
				if(!consultaEntidades.isEmpty()) {
					for(EntidadeDominio ent : consultaEntidades) {
						statusesPedido.add((StatusPedido) ent);
					}
					pedido.setStatusesPedido(statusesPedido);
				}
				
				ItemPedido item = new ItemPedido();
				item.setPedido(pedido);
				consultaEntidades = itemDAO.consultar(item);
				if(!consultaEntidades.isEmpty()) {
					List<ItemPedido> itensPedido = new ArrayList<>();
					for(EntidadeDominio ent : consultaEntidades) {
						itensPedido.add((ItemPedido) ent);
					}
					pedido.setItensPedido(itensPedido);
				}
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
