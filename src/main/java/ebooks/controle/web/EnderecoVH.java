package ebooks.controle.web;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.controle.web.IViewHelper;
import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.TipoEndereco;

public class EnderecoVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		String operacao = request.getParameter("operacao");
		Endereco endereco = new Endereco();
		if(operacao != null) {
			if(operacao.equals("SALVAR") || operacao.equals("ALTERAR")) {
				if(operacao.equals("ALTERAR")) {
					String id = request.getParameter("id");
					endereco.setId(Long.valueOf(id));
				}

				String idCliente = request.getParameter("idCliente");
				if(idCliente != null) {
					Cliente cliente = new Cliente();
					cliente.setId(Long.valueOf(idCliente));
					endereco.setPessoa(cliente);
				}
				String logradouro = request.getParameter("logradouro");
				String numeroEnd = request.getParameter("numeroEnd");
				String complemento = request.getParameter("complemento");
				String cep = request.getParameter("cep");
				String bairro = request.getParameter("bairro");
				String cidade = request.getParameter("cidade");
				String estado = request.getParameter("estado");
				String pais = request.getParameter("pais");
				String identificacao = request.getParameter("identificacao");
				String tipoEnderecoId = request.getParameter("tipoEndereco");
				
				endereco.setLogradouro(logradouro);
				endereco.setNumero(numeroEnd);
				endereco.setComplemento(complemento);
				endereco.setCep(cep);
				endereco.setBairro(bairro);
				endereco.setCidade(cidade);
				endereco.setEstado(estado);
				endereco.setPais(pais);
				endereco.setIdentificacao(identificacao);
				TipoEndereco tipoEndereco = new TipoEndereco();
				tipoEndereco.setId(Long.valueOf(tipoEnderecoId));
				endereco.setTipoEndereco(tipoEndereco);
			}
			if(operacao.equals("CONSULTAR")) {
				String id = request.getParameter("id");
				if(id != null) {
					endereco.setId(Long.valueOf(id));
				}
			}
			if(operacao.equals("EXCLUIR")) {
				String id = request.getParameter("id");
				String idCliente = request.getParameter("idCliente");
				Cliente cliente = new Cliente();
				if(id != null) {
					endereco.setId(Long.valueOf(id));
				}
				if(idCliente != null) {
					cliente.setId(Long.valueOf(idCliente));
					endereco.setPessoa(cliente);
				}
			}
		}
		return endereco;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		
		if(uri.equals(contexto + "/enderecoForm")) {
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idCliente", idCliente);
			request.getRequestDispatcher("WEB-INF/jsp/endereco/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/enderecoEdit")) {
			List<Endereco> listaEndereco = (List<Endereco>) object;
			Endereco endereco = listaEndereco.get(0);
			request.setAttribute("endereco", endereco);
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idCliente", idCliente);
			request.setAttribute("operacao", "ALTERAR");
			request.getRequestDispatcher("WEB-INF/jsp/endereco/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/enderecoSalvar")) {
			if(object == null) {
				String idCliente = request.getParameter("idCliente");
				request.setAttribute("idCliente", idCliente);
				String sucesso = "Endereço cadastrado com sucesso";
				request.setAttribute("sucesso", sucesso);
				request.getRequestDispatcher("clienteView?operacao=CONSULTAR&id=" + idCliente).forward(request, response);
				return;
			}
			String mensagem = (String) object;
			String[] mensagens = mensagem.split(":");
			Endereco endereco = (Endereco) this.getEntidade(request);
			request.setAttribute("endereco", endereco);
			request.setAttribute("mensagens", mensagens);
			request.getRequestDispatcher("WEB-INF/jsp/endereco/form.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/enderecoAlterar")) {
			if (object != null) {
				String idCliente = request.getParameter("idCliente");
				request.setAttribute("idCliente", idCliente);
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				Endereco endereco = (Endereco) this.getEntidade(request);
				request.setAttribute("endereco", endereco);
				request.setAttribute("mensagens", mensagens);
				request.getRequestDispatcher("WEB-INF/jsp/endereco/form.jsp").forward(request, response);
				return;
			}
			String idCliente = request.getParameter("idCliente");
			String sucesso = "Alteração efetuada com sucesso";
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("clienteView?operacao=CONSULTAR&id=" + idCliente).forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/enderecoExcluir")) {
			String idCliente = request.getParameter("idCliente");
			request.setAttribute("idCliente", idCliente);
			String sucesso = (String) object;
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("clienteView?operacao=CONSULTAR&id=" + idCliente).forward(request, response);
		}

	}

}
