package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import ebooks.dao.IDAO;
import ebooks.dao.LivroDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class AlterarQuantidadeItemCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		List<ItemPedido> itensPedido = pedido.getItensPedido();
		IDAO dao = new LivroDAO();
		for(ItemPedido item : itensPedido) {
			Livro livro = item.getLivro();
			try {
				//Consulta a quantidade disponível em estoque do livro desejado
				List<EntidadeDominio> consulta = dao.consultar(livro);
				if(!consulta.isEmpty()) {
					Livro livroConsultado = (Livro) consulta.get(0);
					if(item.getQuantidade() > livroConsultado.getEstoque().getQuantidadeAtual()) {
						sb.append("Quantidade desejada do livro (" 
								+ livroConsultado.getCodigo() 
								+ ") não disponível em estoque (Max. " 
								+ livroConsultado.getEstoque().getQuantidadeAtual() 
								+ " un):");
					}
					else {
						HttpSession session = carrinho.getSession();
						Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
						itensPedido = pedidoSession.getItensPedido();
						for(ItemPedido itemSession : itensPedido) {
							if(itemSession.getLivro().getId() == item.getLivro().getId()) {
								itemSession.setQuantidade(item.getQuantidade());
								break;
							}
						}
						pedidoSession.setItensPedido(itensPedido);
						session.setAttribute("pedido", pedidoSession);
					}
				}		
			} catch (SQLException e) {
				e.printStackTrace();
				sb.append("Problema na consulta SQL:");
			}
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
