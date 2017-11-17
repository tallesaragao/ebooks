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
import ebooks.modelo.Pedido;
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
				String compraToda = request.getParameter("compraToda");
				boolean compraSelecionada = compraToda == null ? false : Boolean.valueOf(compraToda);
				troca.setCompraToda(compraSelecionada);
				Pedido pedido = new Pedido();
				if(idPedido != null && !idPedido.equals("")) {
					pedido.setId(Long.valueOf(idPedido));
					troca.setPedido(pedido);
				}
				//retorna os ids dos itens do pedido
				String idsItens[] = request.getParameterValues("idItem");
				if(idsItens != null) {
					List<ItemTroca> itensTroca = new ArrayList<>();
					// para cada item, verifica se este foi selecionado e faz as atribuições necessárias
					for(int i = 0; i < idsItens.length; i++) {
						String idItem = idsItens[i];
						String selecionarItem = request.getParameter("selecionarItem" + idItem);
						boolean selecionado = false;
						if(selecionarItem != null) {
							selecionado = Boolean.valueOf(selecionarItem);
						}
						if(selecionado) {
							String quantidade = request.getParameter("quantidade" + idItem);
							ItemTroca itemTroca = new ItemTroca();
							itemTroca.setQuantidadeRetornavel(Long.valueOf(quantidade));
							ItemPedido itemPedido = new ItemPedido();
							itemPedido.setId(Long.valueOf(idItem));
							itemTroca.setItemPedido(itemPedido);
							itensTroca.add(itemTroca);
						}
					}
					troca.setItensTroca(itensTroca);
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
		if(uri.equals(contexto + "/trocaSalvar")) {
			
		}

	}

}
