package ebooks.negocio.impl;

import java.sql.SQLException;
import java.util.List;

import ebooks.dao.TrocaDAO;
import ebooks.dao.StatusDAO;
import ebooks.dao.StatusTrocaDAO;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Troca;
import ebooks.modelo.Status;
import ebooks.modelo.StatusTroca;
import ebooks.negocio.IStrategy;

public class AlterarStatusAtualTroca implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		StatusTroca statusTroca = (StatusTroca) entidade;
		Troca troca = statusTroca.getTroca();
		TrocaDAO trocaDAO = new TrocaDAO();
		StatusDAO statusDAO = new StatusDAO();
		StatusTrocaDAO statusTrocaDAO = new StatusTrocaDAO();
		try {
			List<EntidadeDominio> consulta = trocaDAO.consultar(troca);
			if(!consulta.isEmpty()) {
				troca = (Troca) consulta.get(0);
				statusTroca.setTroca(troca);
				Status status = statusTroca.getStatus();
				consulta = statusDAO.consultar(status);
				if(!consulta.isEmpty()) {
					status = (Status) consulta.get(0);
					Status statusAtual = new Status();
					if(status.getNome().equals("Trocado")) {
						for(StatusTroca st : troca.getStatusesTroca()) {
							if(st.getAtual()) {
								statusAtual = st.getStatus();
							}
						}
						if(statusAtual.getNome().equals("Em troca")) {
							IStrategy strategy = new ComplementarDtCadastro();
							strategy.processar(statusTroca);
							strategy = new GerarCupomTroca();
							String mensagem = strategy.processar(troca);
							if(mensagem == null) {
								//Pedido aprovado
								statusTroca.setStatus(status);
								statusTroca.setAtual(true);
								strategy = new DarBaixaEstoque();
								strategy.processar(statusTroca);
								Troca t = new Troca();
								t.setId(statusTroca.getTroca().getId());
								statusTroca.setTroca(t);
								for(StatusTroca st : troca.getStatusesTroca()) {
									st.setAtual(false);
									statusTrocaDAO.alterar(st);
								}
							}
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
