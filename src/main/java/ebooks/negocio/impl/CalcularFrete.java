package ebooks.negocio.impl;

import java.math.BigDecimal;
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
	
	private final Map<String, BigDecimal> tabelaPrecosEstado;
	private final Map<BigDecimal, BigDecimal> tabelaPrecosPeso;
	private final Map<String, Integer> tabelaPrazosEstado;
	
	public CalcularFrete() {
		tabelaPrecosEstado = new HashMap<>();
		tabelaPrecosEstado.put("AC", new BigDecimal("28.0"));
		tabelaPrecosEstado.put("AL", new BigDecimal("19.0"));
		tabelaPrecosEstado.put("AM", new BigDecimal("29.0"));
		tabelaPrecosEstado.put("AP", new BigDecimal("27.0"));
		tabelaPrecosEstado.put("BA", new BigDecimal("14.0"));
		tabelaPrecosEstado.put("CE", new BigDecimal("23.0"));
		tabelaPrecosEstado.put("DF", new BigDecimal("9.5"));
		tabelaPrecosEstado.put("ES", new BigDecimal("8.5"));
		tabelaPrecosEstado.put("GO", new BigDecimal("11.0"));
		tabelaPrecosEstado.put("MA", new BigDecimal("21.0"));
		tabelaPrecosEstado.put("MG", new BigDecimal("7.0"));
		tabelaPrecosEstado.put("MS", new BigDecimal("10.0"));
		tabelaPrecosEstado.put("MT", new BigDecimal("16.0"));
		tabelaPrecosEstado.put("PA", new BigDecimal("26.0"));
		tabelaPrecosEstado.put("PB", new BigDecimal("22.0"));
		tabelaPrecosEstado.put("PE", new BigDecimal("20.0"));
		tabelaPrecosEstado.put("PI", new BigDecimal("18.0"));
		tabelaPrecosEstado.put("PR", new BigDecimal("6.5"));
		tabelaPrecosEstado.put("RJ", new BigDecimal("4.0"));
		tabelaPrecosEstado.put("RN", new BigDecimal("25.0"));
		tabelaPrecosEstado.put("RO", new BigDecimal("24.0"));
		tabelaPrecosEstado.put("RR", new BigDecimal("30.0"));
		tabelaPrecosEstado.put("RS", new BigDecimal("9.0"));
		tabelaPrecosEstado.put("SC", new BigDecimal("5.0"));
		tabelaPrecosEstado.put("SE", new BigDecimal("17.0"));
		tabelaPrecosEstado.put("SP", new BigDecimal("2.0"));
		tabelaPrecosEstado.put("TO", new BigDecimal("15.0"));
		
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
		tabelaPrecosPeso.put(new BigDecimal("100.0"), new BigDecimal("2.0"));
		tabelaPrecosPeso.put(new BigDecimal("250.0"), new BigDecimal("3.0"));
		tabelaPrecosPeso.put(new BigDecimal("500.0"), new BigDecimal("4.0"));
		tabelaPrecosPeso.put(new BigDecimal("1000.0"), new BigDecimal("5.0"));
		tabelaPrecosPeso.put(new BigDecimal("2000.0"), new BigDecimal("7.0"));
		tabelaPrecosPeso.put(new BigDecimal("5000.0"), new BigDecimal("10.0"));
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		Endereco enderecoConsulta = pedido.getEnderecoEntrega();
		
		if(enderecoConsulta != null) {
			BigDecimal pesoPedido = new BigDecimal("0.0");
			
			for(ItemPedido item : pedido.getItensPedido()) {
				Livro livro = item.getLivro();
				pesoPedido = livro.getDimensoes().getPeso();
				pesoPedido = pesoPedido.multiply(new BigDecimal(item.getQuantidade()));
			}
			
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			try {
				List<EntidadeDominio> consulta = enderecoDAO.consultar(enderecoConsulta);
				if(!consulta.isEmpty()) {
					Endereco endereco = (Endereco) consulta.get(0);
					// FALTA DEFINIR O ENDEREÇO DE COBRANÇA AO CADASTRAR O CLIENTE (ENDEREÇO PRINCIPAL) - SOLUÇÃO TEMPORÁRIA
					pedido.setEnderecoCobranca(endereco);
					String uf = endereco.getEstado();
					BigDecimal precoEstado = tabelaPrecosEstado.get(uf);
					
					if(pesoPedido.doubleValue() <= 100) {
						pesoPedido = new BigDecimal("100.0");
					}
					else if (pesoPedido.doubleValue() <= 250) {
						pesoPedido = new BigDecimal("250.0");
					}
					else if (pesoPedido.doubleValue() <= 500) {
						pesoPedido =  new BigDecimal("500.0");
					}
					else if (pesoPedido.doubleValue() <= 1000) {
						pesoPedido = new BigDecimal("1000.0");
					}
					else if (pesoPedido.doubleValue() <= 250) {
						pesoPedido = new BigDecimal("2000.0");
					}
					else {
						pesoPedido = new BigDecimal("5000.0");
					}
					
					BigDecimal precoPeso = tabelaPrecosPeso.get(pesoPedido);
					Integer diasEntrega = tabelaPrazosEstado.get(uf);
					Frete frete = new Frete();
					BigDecimal valorFrete = new BigDecimal("0.0");
					valorFrete = valorFrete.add(precoEstado).add(precoPeso);
					valorFrete = valorFrete.setScale(2, BigDecimal.ROUND_CEILING);
					frete.setValor(valorFrete);
					frete.setDiasEntrega(Long.valueOf(diasEntrega));
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, diasEntrega);
					Date prazoEstimado = cal.getTime();
					frete.setPrazoEstimado(prazoEstimado);
					new ComplementarDtCadastro().processar(frete);
					pedido.setFrete(frete);
					Pedido pedidoSession = carrinho.getPedidoSession();
					pedidoSession.setEnderecoEntrega(endereco);
					pedidoSession.setFrete(frete);
					carrinho.setPedidoSession(pedidoSession);
				}
				else {
					sb.append("Endereço não encontrado:");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				sb.append("Problema na consulta de endereço:");
			}
		}
				
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
