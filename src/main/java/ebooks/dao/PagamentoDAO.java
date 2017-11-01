package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoCartao;
import ebooks.modelo.PagamentoValeCompras;

public class PagamentoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		
		Pagamento pagamento = (Pagamento) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into pagamento(valor_pago, id_forma_pag) values(?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setBigDecimal(1, pagamento.getValorPago());
			ps.setLong(2, pagamento.getFormaPagamento().getId());
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			while(generatedKeys.next()) {
				pagamento.setId(generatedKeys.getLong(1));
			}
			ps.close();
			if(pagamento.getClass().getName().equals(PagamentoCartao.class.getName())) {
				sql = "insert into pag_cartao(id_pagamento, id_cartao_credito) values(?,?)";
				PagamentoCartao pagamentoCartao = (PagamentoCartao) pagamento;
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, pagamentoCartao.getId());
				ps.setLong(2, pagamentoCartao.getCartaoCredito().getId());
				ps.execute();
				ps.close();
			}
			if(pagamento.getClass().getName().equals(PagamentoValeCompras.class.getName())) {
				sql = "insert into pag_vale_compras(id_pagamento, id_vale_compras) values(?,?)";
				PagamentoValeCompras pagamentoValeCompras = (PagamentoValeCompras) pagamento;
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, pagamentoValeCompras.getId());
				ps.setLong(2, pagamentoValeCompras.getValeCompras().getId());
				ps.execute();
				ps.close();
			}
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
		// Implementar
		return false;
	}

	@Override
	public boolean excluir(EntidadeDominio entidade) throws SQLException {
		// Implementar
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		// Implementar
		return null;
	}

}
