package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import ebooks.dao.ClienteDAO;
import ebooks.dao.IDAO;
import ebooks.dao.PedidoDAO;
import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.ItemTroca;
import ebooks.modelo.Pedido;
import ebooks.modelo.StatusTroca;
import ebooks.modelo.Troca;
import ebooks.negocio.IStrategy;

public class ValidarTroca implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Troca troca = (Troca) entidade;
		List<StatusTroca> statusesTroca = troca.getStatusesTroca();
		for(StatusTroca statusTroca : statusesTroca) {
			new ComplementarDtCadastro().processar(statusTroca);
		}
		IDAO dao;
		if(troca.getCompraToda() || troca.getItensTroca().size() > 0) {
			Pedido pedido = troca.getPedido();
			if(pedido != null) {
				dao = new PedidoDAO();
				try {
					List<EntidadeDominio> consulta = dao.consultar(pedido);
					if(!consulta.isEmpty()) {
						pedido = (Pedido) consulta.get(0);
						troca.setPedido(pedido);
						dao = new ClienteDAO();
						Cliente cliente = troca.getCliente();
						consulta = dao.consultar(cliente);
						if(!consulta.isEmpty()) {
							List<ItemPedido> itensPedido = pedido.getItensPedido();
							List<ItemTroca> itensTroca = troca.getItensTroca();
							if(!itensTroca.isEmpty()) {
								for(ItemTroca itemTroca : itensTroca) {
									long idItemTroca = itemTroca.getItemPedido().getId();
									for(ItemPedido itemPedido : itensPedido) {
										long idItemPedido = itemPedido.getId();
										if(idItemTroca == idItemPedido) {
											if(itemTroca.getQuantidadeTrocada() > itemPedido.getQuantidade()) {
												sb.append("Quantidade a trocar não pode ser maior que a quantidade comprada:");
											}
										}
									}
								}
							}
							else {
								if(troca.getCompraToda()) {
									for(ItemPedido itemPedido : itensPedido) {
										ItemTroca itemTroca = new ItemTroca();
										itemTroca.setItemPedido(itemPedido);
										itemTroca.setQuantidadeTrocada(itemPedido.getQuantidade());
										itemTroca.setTroca(troca);
										itensTroca.add(itemTroca);
									}
								}
								else {
									sb.append("Você deve selecionar a compra ou itens para solicitar uma troca:");
								}
							}
						}
						else {
							sb.append("Cliente não encontrado:");
						}
					}
					else {
						sb.append("Pedido não encontrado:");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					sb.append("Problema na consulta de pedido:");
				}
			}
			else {
				sb.append("Pedido não informado:");
			}
		}
		else {
			sb.append("Você deve selecionar a compra ou itens para solicitar uma troca:");
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
