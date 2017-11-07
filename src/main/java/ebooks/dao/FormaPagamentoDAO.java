package ebooks.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ebooks.modelo.CartaoCredito;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.FormaPagamento;
import ebooks.modelo.Login;
import ebooks.modelo.Pagamento;
import ebooks.modelo.Telefone;
import ebooks.modelo.TipoTelefone;

public class FormaPagamentoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		FormaPagamento formaPagamento = (FormaPagamento) entidade;
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into forma_pag(parcelas) values(?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setLong(1, Long.valueOf(1));
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			while(generatedKeys.next()) {
				entidade.setId(generatedKeys.getLong(1));
			}
			ps.close();

			IDAO dao = new PagamentoDAO();
			boolean salvo = false;
			for(Pagamento pagamento : formaPagamento.getPagamentos()) {
				pagamento.setFormaPagamento(formaPagamento);
				salvo = dao.salvar(pagamento);
				if(!salvo) {
					break;
				}
			}
			return salvo;
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
		FormaPagamento formaPagamentoConsulta = (FormaPagamento) entidade;
		conexao = factory.getConnection();
		String sql = "select * from forma_pag fp ";
		if(formaPagamentoConsulta.getId() != null) {
			sql += " where fp.id_forma_pag = ?";
		}
		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			if(formaPagamentoConsulta.getId() != null) {
				ps.setLong(1, formaPagamentoConsulta.getId());
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FormaPagamento formaPagamento = new FormaPagamento();
				formaPagamento.setId(rs.getLong("fp.id_forma_pag"));
				formaPagamento.setParcelas(rs.getLong("fp.parcelas"));				
				consulta.add(formaPagamento);
			}
			ps.close();

			List<FormaPagamento> formasPagamento = new ArrayList<>();
			for(EntidadeDominio ent : consulta) {
				formasPagamento.add((FormaPagamento) ent);
			}
			
			PagamentoDAO pagDAO = new PagamentoDAO();
			for(FormaPagamento formaPagamento : formasPagamento) {
				Pagamento pagamento = new Pagamento();
				pagamento.setFormaPagamento(formaPagamento);
				List<EntidadeDominio> pagamentosConsulta = pagDAO.consultar(pagamento);
				List<Pagamento> pagamentos = new ArrayList<>();
				for(EntidadeDominio ent : pagamentosConsulta) {
					pagamentos.add((Pagamento) ent);
				}
				formaPagamento.setPagamentos(pagamentos);
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
