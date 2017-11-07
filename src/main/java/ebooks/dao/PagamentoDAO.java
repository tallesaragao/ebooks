package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.CartaoCredito;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Pagamento;
import ebooks.modelo.PagamentoCartao;
import ebooks.modelo.PagamentoValeCompras;
import ebooks.modelo.ValeCompras;

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
		List<EntidadeDominio> consulta = new ArrayList<>();
		Pagamento pagamentoConsulta = (Pagamento) entidade;
		conexao = factory.getConnection();
		String sql = "select * from pagamento p ";
		if(pagamentoConsulta.getFormaPagamento() != null && pagamentoConsulta.getFormaPagamento().getId() != null) {
			sql += " where p.id_forma_pag = ?";
		}
		else {
			if(pagamentoConsulta.getId() != null) {
				sql += " where p.id_pagamento = ?";
			}
		}
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(pagamentoConsulta.getFormaPagamento() != null && pagamentoConsulta.getFormaPagamento().getId() != null) {
				ps.setLong(1, pagamentoConsulta.getFormaPagamento().getId());
			}
			else {
				if(pagamentoConsulta.getId() != null) {
					ps.setLong(1, pagamentoConsulta.getId());
				}
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pagamento pagamento = new Pagamento();
				pagamento.setId(rs.getLong("p.id_pagamento"));
				pagamento.setValorPago(rs.getBigDecimal("p.valor_pago"));
				pagamento.setFormaPagamento(pagamentoConsulta.getFormaPagamento());
				consulta.add(pagamento);
			}
			ps.close();
			
			List<PagamentoCartao> pagamentosCartao = new ArrayList<>();
			List<PagamentoValeCompras> pagamentosVale = new ArrayList<>();
			for(EntidadeDominio ent : consulta) {
				Pagamento pagamento = (Pagamento) ent;
				sql = "select * from pag_cartao pc where id_pagamento = ?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, pagamento.getId());
				rs = ps.executeQuery();
				while(rs.next()) {
					PagamentoCartao pagCartao = new PagamentoCartao();
					pagCartao.setId(pagamento.getId());
					pagCartao.setFormaPagamento(pagamento.getFormaPagamento());
					pagCartao.setValorPago(pagamento.getValorPago());
					CartaoCredito cartao = new CartaoCredito();
					cartao.setId(rs.getLong("pc.id_cartao_credito"));
					pagCartao.setCartaoCredito(cartao);
					pagamentosCartao.add(pagCartao);
				}
				sql = "select * from pag_vale_compras pvc where id_pagamento = ?";
				ps = conexao.prepareStatement(sql);
				ps.setLong(1, pagamento.getId());
				rs = ps.executeQuery();
				while(rs.next()) {
					PagamentoValeCompras pagVale = new PagamentoValeCompras();
					pagVale.setId(pagamento.getId());
					pagVale.setFormaPagamento(pagamento.getFormaPagamento());
					pagVale.setValorPago(pagamento.getValorPago());
					ValeCompras vale = new ValeCompras();
					vale.setId(rs.getLong("pvc.id_vale_compras"));
					pagVale.setValeCompras(vale);
					pagamentosVale.add(pagVale);
				}
			}
			
			for(PagamentoCartao pagCartao : pagamentosCartao) {
				CartaoCredito cartao = pagCartao.getCartaoCredito();
				IDAO cartaoDAO = new CartaoCreditoDAO();
				List<EntidadeDominio> consultaCartao = cartaoDAO.consultar(cartao);
				cartao = (CartaoCredito) consultaCartao.get(0);
				pagCartao.setCartaoCredito(cartao);
			}
			for(PagamentoValeCompras pagVale : pagamentosVale) {
				ValeCompras vale = pagVale.getValeCompras();
				IDAO valeDAO = new ValeComprasDAO();
				List<EntidadeDominio> consultaVale = valeDAO.consultar(vale);
				vale = (ValeCompras) consultaVale.get(0);
				pagVale.setValeCompras(vale);
			}
			for(PagamentoCartao pagCartao : pagamentosCartao) {
				consulta.add(pagCartao);
			}
			for(PagamentoValeCompras pagVale : pagamentosVale) {
				consulta.add(pagVale);
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
