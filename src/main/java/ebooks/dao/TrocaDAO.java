package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.Cliente;
import ebooks.modelo.CupomTroca;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemTroca;
import ebooks.modelo.Pedido;
import ebooks.modelo.StatusPedido;
import ebooks.modelo.StatusTroca;
import ebooks.modelo.Troca;

public class TrocaDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Troca troca = (Troca) entidade;
		Pedido pedido = troca.getPedido();
		IDAO dao;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into troca(fl_compra_toda, id_pedido, id_cliente, dt_cadastro) values(?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, troca.getCompraToda());
			ps.setLong(2, troca.getPedido().getId());
			ps.setLong(3, troca.getCliente().getId());
			ps.setDate(4, new Date(troca.getDataCadastro().getTime()));
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			while (generatedKeys.next()) {
				troca.setId(generatedKeys.getLong(1));
			}
			ps.close();
			
			dao = new ItemTrocaDAO();
			boolean itensSalvos = false;
			for(ItemTroca item : troca.getItensTroca()) {
				item.setTroca(troca);
				itensSalvos = dao.salvar(item);
				if(!itensSalvos) {
					break;
				}
			}
			
			dao = new StatusTrocaDAO();
			boolean statusesSalvos = false;
			for(StatusTroca statusTroca : troca.getStatusesTroca()) {
				statusTroca.setTroca(troca);
				statusesSalvos = dao.salvar(statusTroca);
				if(!statusesSalvos) {
					break;
				}
			}
			
			if(itensSalvos && statusesSalvos) {
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
		Troca trocaConsulta = (Troca) entidade;
		conexao = factory.getConnection();
		String sql = "select * from troca t ";
		if(trocaConsulta.getId() != null) {
			sql += " where t.id_troca = ?";
		}
		else if(trocaConsulta.getCliente() != null && trocaConsulta.getCliente().getId() != null) {
			sql += " where t.id_cliente = ?";
		}
		
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(trocaConsulta.getId() != null) {
				ps.setLong(1, trocaConsulta.getId());
			}
			else if(trocaConsulta.getCliente() != null && trocaConsulta.getCliente().getId() != null) {
				ps.setLong(1, trocaConsulta.getCliente().getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Troca troca = new Troca();
				troca.setId(rs.getLong("t.id_troca"));
				
				Cliente cliente = new Cliente();
				cliente.setId(rs.getLong("t.id_cliente"));
				troca.setCliente(cliente);
				
				Pedido pedido = new Pedido();
				pedido.setId(rs.getLong("t.id_pedido"));
				troca.setPedido(pedido);
				
				troca.setCompraToda(rs.getBoolean("t.fl_compra_toda"));
				
				Long idCupomTroca = rs.getLong("id_cupom_troca");
				if(idCupomTroca != null) {
					CupomTroca cupomTroca = new CupomTroca();
					cupomTroca.setId(idCupomTroca);
					troca.setCupomTroca(cupomTroca);
				}
				
				troca.setDataCadastro(rs.getDate("t.dt_cadastro"));
				
				consulta.add(troca);
			}
			ps.close();

			List<Troca> trocas = new ArrayList<>();
			for(EntidadeDominio ent : consulta) {
				trocas.add((Troca) ent);
			}
			
			PedidoDAO pedidoDAO = new PedidoDAO();
			StatusTrocaDAO statusTrocaDAO = new StatusTrocaDAO();
			ItemTrocaDAO itemDAO = new ItemTrocaDAO();
			CupomTrocaDAO cupomTrocaDAO = new CupomTrocaDAO();
			for(Troca troca : trocas) {
				
				List<EntidadeDominio> consultaEntidades = new ArrayList<>();
				
				if(trocaConsulta.getPedido() == null) {
					Pedido pedido = troca.getPedido();
					pedido.setCliente(troca.getCliente());
					consultaEntidades = pedidoDAO.consultar(pedido);
					if(!consultaEntidades.isEmpty()) {
						Pedido pedidoConsulta = (Pedido) consultaEntidades.get(0);
						troca.setPedido(pedidoConsulta);
						troca.setCliente(pedido.getCliente());
					}
				}
				
				StatusTroca statusTroca = new StatusTroca();
				statusTroca.setTroca(troca);
				consultaEntidades = statusTrocaDAO.consultar(statusTroca);
				List<StatusTroca> statusesTroca = new ArrayList<>();
				if(!consultaEntidades.isEmpty()) {
					for(EntidadeDominio ent : consultaEntidades) {
						statusesTroca.add((StatusTroca) ent);
					}
					troca.setStatusesTroca(statusesTroca);
				}
				
				ItemTroca item = new ItemTroca();
				item.setTroca(troca);
				consultaEntidades = itemDAO.consultar(item);
				if(!consultaEntidades.isEmpty()) {
					List<ItemTroca> itensTroca = new ArrayList<>();
					for(EntidadeDominio ent : consultaEntidades) {
						itensTroca.add((ItemTroca) ent);
					}
					troca.setItensTroca(itensTroca);
				}
				
				if(trocaConsulta.getCupomTroca() == null) {
					CupomTroca cupomTroca = troca.getCupomTroca();
					consultaEntidades = cupomTrocaDAO.consultar(cupomTroca);
					if(!consultaEntidades.isEmpty()) {
						troca.setCupomTroca((CupomTroca) consultaEntidades.get(0));
					}
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
