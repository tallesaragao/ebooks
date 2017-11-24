package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.ItemTroca;
import ebooks.modelo.Troca;
import ebooks.modelo.Status;
import ebooks.modelo.StatusTroca;

public class StatusTrocaVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		EntidadeDominio entidade = new EntidadeDominio();
		String operacao = request.getParameter("operacao");
		if(operacao != null) {
			if(operacao.equals("SALVAR")) {
				StatusTroca statusTroca = new StatusTroca();
				Troca troca = new Troca();
				String idTroca = request.getParameter("idTroca");
				if(idTroca != null && !idTroca.equals("")) {
					troca.setId(Long.valueOf(idTroca));
					statusTroca.setTroca(troca);
				}
				String nomeStatus = request.getParameter("status");
				if(nomeStatus != null) {
					Status status = new Status();
					status.setNome(nomeStatus);
					statusTroca.setStatus(status);
				}
				String idsItens[] = request.getParameterValues("idItem");
				List<ItemTroca> itensTroca = new ArrayList<>();
				if(idsItens != null) {
					// para cada item, verifica se este foi selecionado e faz as atribuições necessárias
					for(int i = 0; i < idsItens.length; i++) {
						String idItem = idsItens[i];						
						String quantidade = request.getParameter("quantidade" + idItem);
						ItemTroca itemTroca = new ItemTroca();
						itemTroca.setQuantidadeRetornavel(Long.valueOf(quantidade));
						itemTroca.setId(Long.valueOf(idItem));
						itensTroca.add(itemTroca);
					}
					troca.setItensTroca(itensTroca);
				}
				
				entidade = statusTroca;
			}
		}
		return entidade;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/statusTrocaSalvar")) {
			String idTroca = request.getParameter("idTroca");
			if(idTroca == null) {
				idTroca = "";
			}
			if(object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				request.setAttribute("mensagens", mensagens);
				request.getRequestDispatcher("trocaView?operacao=CONSULTAR&idTroca=" + idTroca).forward(request, response);
			}
			else {
				response.sendRedirect("trocaView?operacao=CONSULTAR&idTroca=" + idTroca);
			}
		}
	}

}
