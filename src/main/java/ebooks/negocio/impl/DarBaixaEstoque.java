package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import ebooks.dao.IDAO;
import ebooks.dao.LivroDAO;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Estoque;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Pedido;
import ebooks.modelo.StatusPedido;
import ebooks.negocio.IStrategy;

public class DarBaixaEstoque implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		StatusPedido statusPedido = (StatusPedido) entidade;
		Pedido pedido = statusPedido.getPedido();
		List<ItemPedido> itensPedido = pedido.getItensPedido();
		IDAO dao = new LivroDAO();
		for(ItemPedido item : itensPedido) {
			Livro livro = item.getLivro();
			Long quantidade = item.getQuantidade();
			Estoque estoque = livro.getEstoque();
			Long quantidadeReservada = estoque.getQuantidadeReservada();
			quantidadeReservada -= quantidade;
			estoque.setQuantidadeReservada(quantidadeReservada);
			if(statusPedido.getStatus().getNome().equals("Aprovada")) {
				Long quantidadeAtual = estoque.getQuantidadeAtual();
				quantidadeAtual -= quantidade;
				estoque.setQuantidadeAtual(quantidadeAtual);
			}
			livro.setEstoque(estoque);
			try {
				boolean alterado = dao.alterar(livro);
				if(!alterado) {
					sb.append("Problema ao dar baixa no estoque:");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sb.append("Problema na transação SQL:");
			}
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
