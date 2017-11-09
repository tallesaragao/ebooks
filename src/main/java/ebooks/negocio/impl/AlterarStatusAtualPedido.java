package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import ebooks.dao.PedidoDAO;
import ebooks.dao.StatusDAO;
import ebooks.dao.StatusPedidoDAO;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pedido;
import ebooks.modelo.Status;
import ebooks.modelo.StatusPedido;
import ebooks.negocio.IStrategy;

public class AlterarStatusAtualPedido implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		StatusPedido statusPedido = (StatusPedido) entidade;
		Pedido pedido = statusPedido.getPedido();
		PedidoDAO pedidoDAO = new PedidoDAO();
		StatusDAO statusDAO = new StatusDAO();
		StatusPedidoDAO statusPedidoDAO = new StatusPedidoDAO();
		try {
			List<EntidadeDominio> consulta = pedidoDAO.consultar(pedido);
			if(!consulta.isEmpty()) {
				pedido = (Pedido) consulta.get(0);
				Status status = statusPedido.getStatus();
				consulta = statusDAO.consultar(status);
				if(!consulta.isEmpty()) {
					status = (Status) consulta.get(0);
					Status statusAtual = new Status();
					if(status.getNome().equals("Aprovada")) {
						for(StatusPedido sp : pedido.getStatusesPedido()) {
							if(sp.getAtual()) {
								statusAtual = sp.getStatus();
							}
						}
						if(statusAtual.getNome().equals("Em processamento")) {
							IStrategy strategy = new ComplementarDtCadastro();
							strategy.processar(statusPedido);
							strategy = new AprovarPedido();
							String mensagem = strategy.processar(pedido);
							if(mensagem == null) {
								//Pedido aprovado
								statusPedido.setStatus(status);
								statusPedido.setAtual(true);
								strategy = new DarBaixaEstoque();
								strategy.processar(statusPedido);
								Pedido p = new Pedido();
								p.setId(statusPedido.getPedido().getId());
								statusPedido.setPedido(p);
								for(StatusPedido sp : pedido.getStatusesPedido()) {
									sp.setAtual(false);
									statusPedidoDAO.alterar(sp);
								}
							}
							else if(mensagem.equals("Pedido reprovado:")) {
								status = new Status();
								status.setNome("Reprovada");
								consulta = statusDAO.consultar(status);
								if(!consulta.isEmpty()) {
									status = (Status) consulta.get(0);
									statusPedido.setStatus(status);
									statusPedido.setAtual(true);
									strategy = new DarBaixaEstoque();
									strategy.processar(statusPedido);
									Pedido p = new Pedido();
									p.setId(statusPedido.getPedido().getId());
									statusPedido.setPedido(p);
									for(StatusPedido sp : pedido.getStatusesPedido()) {
										sp.setAtual(false);
										statusPedidoDAO.alterar(sp);
									}
								}
								else {
									sb.append("Status não encontrado");
								}
							}
							else {
								sb.append("Não foi possível alterar o status");
							}
						}
					}
					else if(status.getNome().equals("Em transporte")) {
						for(StatusPedido sp : pedido.getStatusesPedido()) {
							if(sp.getAtual()) {
								statusAtual = sp.getStatus();
							}
						}
						if(statusAtual.getNome().equals("Aprovada")) {
							IStrategy strategy = new ComplementarDtCadastro();
							strategy.processar(statusPedido);
						}
					}
				}
				else {
					sb.append("Status não encontrado");
				}
			}
			else {
				sb.append("Pedido não encontrado:");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			sb.append("Problema na consulta:");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
