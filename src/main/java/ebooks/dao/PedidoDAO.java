package ebooks.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ebooks.modelo.Carrinho;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.Pedido;

public class PedidoDAO extends AbstractDAO {

	@Override
	public boolean salvar(EntidadeDominio entidade) throws SQLException {
		Carrinho carrinho = (Carrinho) entidade;
		Pedido pedido = carrinho.getPedido();
		IDAO dao = new FreteDAO();
		boolean freteSalvo = dao.salvar(pedido.getFrete());
		dao = new FormaPagamentoDAO();
		boolean formaPagamentoSalva = dao.salvar(pedido.getFormaPagamento());
		conexao = factory.getConnection();
		try {
			conexao.setAutoCommit(false);
			String sql = "insert into pedido(valor_total, numero, id_endereco_entrega, id_endereco_cobranca, id_cliente,"
						+ " id_cupom_promo, id_frete, id_forma_pag, dt_cadastro) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setBigDecimal(1, pedido.getValorTotal());
			ps.setString(2, pedido.getNumero());
			ps.setLong(3, pedido.getEnderecoEntrega().getId());
			// FALTA DEFINIR O ENDEREÇO DE COBRANÇA AO CADASTRAR O CLIENTE (ENDEREÇO PRINCIPAL) - SOLUÇÃO TEMPORÁRIA
			ps.setLong(4, pedido.getEnderecoCobranca().getId());
			ps.setLong(5, pedido.getCliente().getId());
			ps.setLong(6, pedido.getCupomPromocional().getId());
			ps.setLong(7, pedido.getFrete().getId());
			ps.setLong(8, pedido.getFormaPagamento().getId());
			ps.setDate(9, new Date(pedido.getDataCadastro().getTime()));
			ps.execute();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			while (generatedKeys.next()) {
				pedido.setId(generatedKeys.getLong(1));
			}
			ps.close();
			
			dao = new ItemPedidoDAO();
			boolean itensSalvos = false;
			for(ItemPedido item : pedido.getItensPedido()) {
				item.setPedido(pedido);
				itensSalvos = dao.salvar(item);
				if(!itensSalvos) {
					break;
				}
			}
			if(freteSalvo && formaPagamentoSalva && itensSalvos) {
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
		//Implementar
		return null;
	}

}
