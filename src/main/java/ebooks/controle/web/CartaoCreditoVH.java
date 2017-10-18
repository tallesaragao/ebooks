package ebooks.controle.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.modelo.Bandeira;
import ebooks.modelo.CartaoCredito;
import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;

public class CartaoCreditoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		CartaoCredito cartaoCredito = new CartaoCredito();
		if(operacao != null) {
			if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				if(operacao.equals("ALTERAR")) {
					String id = request.getParameter("id");
					cartaoCredito.setId(Long.valueOf(id));
				}
				String numero = request.getParameter("numero");
				String nomeTitular = request.getParameter("nomeTitular");
				String dataVencimentoString = request.getParameter("dataVencimento");
				String codigoSeguranca = request.getParameter("codigoSeguranca");
				String idCliente = request.getParameter("idCliente");
				String idBandeira = request.getParameter("bandeira");
				
				cartaoCredito.setNumero(numero);
				cartaoCredito.setNomeTitular(nomeTitular);
				cartaoCredito.setCodigoSeguranca(codigoSeguranca);
				Cliente cliente = new Cliente();
				cliente.setId(Long.valueOf(idCliente));
				cartaoCredito.setCliente(cliente);
				Bandeira bandeira = new Bandeira();
				bandeira.setId(Long.valueOf(idBandeira));
				cartaoCredito.setBandeira(bandeira);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dataFormatada;
				if(dataVencimentoString != null) {
					try {
						dataFormatada = sdf.parse(dataVencimentoString);
						cartaoCredito.setDataVencimento(dataFormatada);
					} catch (ParseException e) {
						cartaoCredito.setDataVencimento(null);
					}
				}
			}
			if(operacao.equals("CONSULTAR")) {
				String id = request.getParameter("id");
				if(id != null) {
					cartaoCredito.setId(Long.valueOf(id));
				}
			}
			if(operacao.equals("EXCLUIR")) {
				String id = request.getParameter("id");
				if(id != null) {
					cartaoCredito.setId(Long.valueOf(id));
				}
			}
		}
		return cartaoCredito;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/cartaoCreditoForm")) {
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idCliente", idCliente);
			request.getRequestDispatcher("cartaoCreditoFormBandeiras?operacao=CONSULTAR").forward(request, response);
		}
		if(uri.equals(contexto + "/cartaoCreditoEdit")) {
			List<CartaoCredito> listaCartaoCredito = (List<CartaoCredito>) object;
			CartaoCredito cartaoCredito = listaCartaoCredito.get(0);
			request.setAttribute("cartaoCredito", cartaoCredito);
			request.setAttribute("operacao", "ALTERAR");
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idCliente", idCliente);
			request.getRequestDispatcher("cartaoCreditoEditBandeiras").forward(request, response);
		}
		if(uri.equals(contexto + "/cartaoCreditoSalvar")) {
			if(object == null) {
				String idCliente = request.getParameter("idCliente");
				String sucesso = "Cartão de crédito cadastrado com sucesso";
				request.setAttribute("sucesso", sucesso);
				request.getRequestDispatcher("clienteView?operacao=CONSULTAR&id=" + idCliente).forward(request, response);
				return;
			}
			String mensagem = (String) object;
			String[] mensagens = mensagem.split(":");
			CartaoCredito cartaoCredito = (CartaoCredito) this.getEntidade(request);
			request.setAttribute("cartaoCredito", cartaoCredito);
			request.setAttribute("mensagens", mensagens);
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idCliente", idCliente);
			request.getRequestDispatcher("WEB-INF/jsp/cartaoCredito/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/cartaoCreditoAlterar")) {
			if (object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				CartaoCredito cartaoCredito = (CartaoCredito) this.getEntidade(request);
				request.setAttribute("cartaoCredito", cartaoCredito);
				request.setAttribute("mensagens", mensagens);
				String operacao = "ALTERAR";
				request.setAttribute("operacao", operacao);
				String idCliente = request.getParameter("idCliente");
				request.setAttribute("idCliente", idCliente);
				request.getRequestDispatcher("WEB-INF/jsp/cartaoCredito/form.jsp").forward(request, response);
				return;
			}
			String idCliente = request.getParameter("idCliente");
			String sucesso = "Alteração efetuada com sucesso";
			request.setAttribute("sucesso", sucesso);
			response.sendRedirect("clienteView?operacao=CONSULTAR&id=" + idCliente);
			return;
		}
		if(uri.equals(contexto + "/cartaoCreditoExcluir")) {
			String idCliente = request.getParameter("idCliente");
			String sucesso = (String) object;
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("clienteView?operacao=CONSULTAR&id=" + idCliente).forward(request, response);
		}

	}

}
