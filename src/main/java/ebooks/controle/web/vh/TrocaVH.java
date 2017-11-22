package ebooks.controle.web.vh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.ItemTroca;
import ebooks.modelo.Pedido;
import ebooks.modelo.Status;
import ebooks.modelo.StatusTroca;
import ebooks.modelo.Troca;

public class TrocaVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		EntidadeDominio entidade = new EntidadeDominio();
		String operacao = request.getParameter("operacao");
		if(operacao != null) {
			if(operacao.equals("SALVAR")) {
				Troca troca = new Troca();
				String idPedido = request.getParameter("idPedido");
				String idCliente = request.getParameter("idCliente");
				String compraToda = request.getParameter("compraToda");
				boolean compraSelecionada = compraToda == null ? false : Boolean.valueOf(compraToda);
				troca.setCompraToda(compraSelecionada);
				Pedido pedido = new Pedido();
				if(idPedido != null && !idPedido.equals("")) {
					pedido.setId(Long.valueOf(idPedido));
					troca.setPedido(pedido);
				}
				if(idCliente != null && !idPedido.equals("")) {
					Cliente cliente = new Cliente();
					cliente.setId(Long.valueOf(idCliente));
					troca.setCliente(cliente);
				}
				//retorna os ids dos itens do pedido
				String idsItens[] = request.getParameterValues("idItem");
				List<ItemTroca> itensTroca = new ArrayList<>();
				if(idsItens != null) {
					// para cada item, verifica se este foi selecionado e faz as atribuições necessárias
					for(int i = 0; i < idsItens.length; i++) {
						String idItem = idsItens[i];
						String selecionarItem = request.getParameter("item" + idItem);
						boolean selecionado = false;
						if(selecionarItem != null) {
							selecionado = Boolean.valueOf(selecionarItem);
						}
						if(selecionado) {
							String quantidade = request.getParameter("quantidade" + idItem);
							ItemTroca itemTroca = new ItemTroca();
							itemTroca.setQuantidadeTrocada(Long.valueOf(quantidade));
							ItemPedido itemPedido = new ItemPedido();
							itemPedido.setId(Long.valueOf(idItem));
							itemTroca.setItemPedido(itemPedido);
							itensTroca.add(itemTroca);
						}
					}
				}
				troca.setItensTroca(itensTroca);
				List<StatusTroca> statusesTroca = new ArrayList<>();
				Status status = new Status();
				// Status: Em troca - ID 6
				status.setId(Long.valueOf(6));
				status.setNome("Em troca");
				StatusTroca statusTroca = new StatusTroca();
				statusTroca.setAtual(true);
				statusTroca.setStatus(status);
				statusTroca.setTroca(troca);
				statusesTroca.add(statusTroca);
				troca.setStatusesTroca(statusesTroca);
				entidade = troca;
			}
			if(operacao.equals("CONSULTAR")) {
				Troca troca = new Troca();
				String idTroca = request.getParameter("idTroca");
				String idCliente = request.getParameter("idCliente");
				if(idCliente != null && !idCliente.equals("")) {
					Cliente cliente = new Cliente();
					cliente.setId(Long.valueOf(idCliente));
					troca.setCliente(cliente);
				}
				if(idTroca != null && !idTroca.equals("")) {
					troca.setId(Long.valueOf(idTroca));
				}
				entidade = troca;
			}
		}		
		return entidade;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/trocaForm")) {
			request.getRequestDispatcher("WEB-INF/jsp/troca/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/trocaList")) {
			request.getRequestDispatcher("WEB-INF/jsp/troca/list.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/trocaSalvar")) {
			if(object != null) {
				String retorno = (String) object;
				String[] mensagens = retorno.split(":");
				request.setAttribute("mensagens", mensagens);
				String idPedido = request.getParameter("idPedido");
				String idCliente = request.getParameter("idCliente");
				request.getRequestDispatcher("pedidoTroca?operacao=CONSULTAR&idPedido=" 
				+ idPedido + "&idCliente=" + idCliente).forward(request, response);
			}
			else {
				request.getRequestDispatcher("WEB-INF/jsp/troca/success.jsp").forward(request, response);
			}
		}
		if(uri.equals(contexto + "/trocaConsultar")) {
			if(object != null) {
				List<Troca> consulta = (List<Troca>) object;
				request.setAttribute("consulta", consulta);
			}
			request.getRequestDispatcher("WEB-INF/jsp/troca/list.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/trocaView")) {
			if(object != null) {
				List<EntidadeDominio> consulta = (List<EntidadeDominio>) object;
				request.setAttribute("troca", consulta.get(0));
			}
			request.getRequestDispatcher("WEB-INF/jsp/troca/tracking.jsp").forward(request, response);
		}
	}

}
