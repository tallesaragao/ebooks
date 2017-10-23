package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import ebooks.dao.EnderecoDAO;
import ebooks.modelo.Carrinho;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Frete;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Livro;
import ebooks.modelo.Pedido;
import ebooks.negocio.IStrategy;

public class CalcularFrete implements IStrategy {
	
	private final Map<String, Double> tabelaPrecosEstado;
	private final Map<Double, Double> tabelaPrecosPeso;
	private final Map<String, Integer> tabelaPrazosEstado;
	
	public CalcularFrete() {
		tabelaPrecosEstado = new HashMap<>();
		tabelaPrecosEstado.put("AC", 28.0);
		tabelaPrecosEstado.put("AL", 19.0);
		tabelaPrecosEstado.put("AM", 29.0);
		tabelaPrecosEstado.put("AP", 27.0);
		tabelaPrecosEstado.put("BA", 14.0);
		tabelaPrecosEstado.put("CE", 23.0);
		tabelaPrecosEstado.put("DF", 9.5);
		tabelaPrecosEstado.put("ES", 8.5);
		tabelaPrecosEstado.put("GO", 11.0);
		tabelaPrecosEstado.put("MA", 21.0);
		tabelaPrecosEstado.put("MG", 7.0);
		tabelaPrecosEstado.put("MS", 10.0);
		tabelaPrecosEstado.put("MT", 16.0);
		tabelaPrecosEstado.put("PA", 26.0);
		tabelaPrecosEstado.put("PB", 22.0);
		tabelaPrecosEstado.put("PE", 20.0);
		tabelaPrecosEstado.put("PI", 18.0);
		tabelaPrecosEstado.put("PR", 6.5);
		tabelaPrecosEstado.put("RJ", 4.0);
		tabelaPrecosEstado.put("RN", 25.0);
		tabelaPrecosEstado.put("RO", 24.0);
		tabelaPrecosEstado.put("RR", 30.0);
		tabelaPrecosEstado.put("RS", 9.0);
		tabelaPrecosEstado.put("SC", 5.0);
		tabelaPrecosEstado.put("SE", 17.0);
		tabelaPrecosEstado.put("SP", 2.0);
		tabelaPrecosEstado.put("TO", 15.0);
		
		tabelaPrazosEstado = new HashMap<>();
		tabelaPrazosEstado.put("AC", 10);
		tabelaPrazosEstado.put("AL", 10);
		tabelaPrazosEstado.put("AM", 10);
		tabelaPrazosEstado.put("AP", 9);
		tabelaPrazosEstado.put("BA", 8);
		tabelaPrazosEstado.put("CE", 8);
		tabelaPrazosEstado.put("DF", 4);
		tabelaPrazosEstado.put("ES", 3);
		tabelaPrazosEstado.put("GO", 5);
		tabelaPrazosEstado.put("MA", 9);
		tabelaPrazosEstado.put("MG", 3);
		tabelaPrazosEstado.put("MS", 6);
		tabelaPrazosEstado.put("MT", 8);
		tabelaPrazosEstado.put("PA", 9);
		tabelaPrazosEstado.put("PB", 8);
		tabelaPrazosEstado.put("PE", 8);
		tabelaPrazosEstado.put("PI", 8);
		tabelaPrazosEstado.put("PR", 4);
		tabelaPrazosEstado.put("RJ", 3);
		tabelaPrazosEstado.put("RN", 10);
		tabelaPrazosEstado.put("RO", 10);
		tabelaPrazosEstado.put("RR", 10);
		tabelaPrazosEstado.put("RS", 5);
		tabelaPrazosEstado.put("SC", 4);
		tabelaPrazosEstado.put("SE", 7);
		tabelaPrazosEstado.put("SP", 2);
		tabelaPrazosEstado.put("TO", 7);
		
		tabelaPrecosPeso = new HashMap<>();
		tabelaPrecosPeso.put(100.0, 2.0);
		tabelaPrecosPeso.put(250.0, 3.0);
		tabelaPrecosPeso.put(500.0, 4.0);
		tabelaPrecosPeso.put(1000.0, 5.0);
		tabelaPrecosPeso.put(2000.0, 7.0);
		tabelaPrecosPeso.put(5000.0, 10.0);
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		Endereco enderecoConsulta = pedido.getEnderecoEntrega();
		
		if(enderecoConsulta != null) {
			Double pesoPedido = 0.0;
			
			for(ItemPedido item : pedido.getItensPedido()) {
				Livro livro = item.getLivro();
				pesoPedido += Double.valueOf(livro.getDimensoes().getPeso()) * item.getQuantidade();
			}
			
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			try {
				List<EntidadeDominio> consulta = enderecoDAO.consultar(enderecoConsulta);
				if(!consulta.isEmpty()) {
					Endereco endereco = (Endereco) consulta.get(0);
					String uf = endereco.getEstado();
					Double precoEstado = tabelaPrecosEstado.get(uf);
					
					if(pesoPedido <= 100) {
						pesoPedido = 100.0;
					}
					else if (pesoPedido <= 250) {
						pesoPedido = 250.0;
					}
					else if (pesoPedido <= 500) {
						pesoPedido = 500.0;
					}
					else if (pesoPedido <= 1000) {
						pesoPedido = 1000.0;
					}
					else if (pesoPedido <= 250) {
						pesoPedido = 2000.0;
					}
					else {
						pesoPedido = 5000.0;
					}
					
					Double precoPeso = tabelaPrecosPeso.get(pesoPedido);
					Integer diasEntrega = tabelaPrazosEstado.get(uf);
					Frete frete = new Frete();
					frete.setValor(precoEstado + precoPeso);
					frete.setDiasEntrega(Long.valueOf(diasEntrega));
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, diasEntrega);
					Date prazoEstimado = cal.getTime();
					frete.setPrazoEstimado(prazoEstimado);
					new ComplementarDtCadastro().processar(frete);
					pedido.setFrete(frete);
					HttpSession session = carrinho.getSession();
					Pedido pedidoSession = (Pedido) session.getAttribute("pedido");
					pedidoSession.setEnderecoEntrega(endereco);
					pedidoSession.setFrete(frete);
					session.setAttribute("pedido", pedidoSession);
				}
				else {
					sb.append("Endereço não encontrado");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				sb.append("Problema na consulta de endereço");
			}
		}
				
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
