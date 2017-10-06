package ebooks.controle.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.modelo.CartaoCredito;
import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Telefone;
import ebooks.modelo.TipoEndereco;
import ebooks.modelo.TipoTelefone;

public class ClienteVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Cliente cliente = new Cliente();
		String operacao = request.getParameter("operacao");
		if(operacao != null) {
			if(operacao.equals("SALVAR")) {
				String nome = request.getParameter("nome");
				String cpf = request.getParameter("cpf");
				String genero = request.getParameter("genero");
				String email = request.getParameter("email");
				String dataNascimento = request.getParameter("dataNascimento");
				String ddd = request.getParameter("ddd");
				String numeroTel = request.getParameter("numeroTel");
				String tipoTelefoneId = request.getParameter("tipoTelefone");
				String logradouro = request.getParameter("logradouro");
				String numeroEnd = request.getParameter("numeroEnd");
				String complemento = request.getParameter("complemento");
				String cep = request.getParameter("cep");
				String bairro = request.getParameter("bairro");
				String cidade = request.getParameter("cidade");
				String estado = request.getParameter("estado");
				String pais = request.getParameter("pais");
				String tipoEnderecoId = request.getParameter("tipoEndereco");
				String identificacao = request.getParameter("identificacao");

				Endereco endereco = new Endereco();
				endereco.setLogradouro(logradouro);
				endereco.setBairro(bairro);
				endereco.setCep(cep);
				endereco.setCidade(cidade);
				endereco.setNumero(numeroEnd);
				endereco.setComplemento(complemento);
				endereco.setEstado(estado);
				endereco.setPais(pais);
				endereco.setIdentificacao(identificacao);
				TipoEndereco tipoEndereco = new TipoEndereco();
				tipoEndereco.setId(Long.valueOf(tipoEnderecoId));
				endereco.setTipoEndereco(tipoEndereco);
				List<Endereco> enderecos = new ArrayList<>();
				enderecos.add(endereco);

				Telefone telefone = new Telefone();
				telefone.setDdd(ddd);
				telefone.setNumero(numeroTel);
				TipoTelefone tipoTelefone = new TipoTelefone();
				tipoTelefone.setId(Long.valueOf(tipoTelefoneId));
				telefone.setTipoTelefone(tipoTelefone);
				
				cliente.setNome(nome);
				cliente.setCpf(cpf);
				cliente.setGenero(genero.charAt(0));
				cliente.setEmail(email);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dataFormatada;
				if(dataNascimento != null) {
					try {
						dataFormatada = sdf.parse(dataNascimento);
						cliente.setDataNascimento(dataFormatada);
					} catch (ParseException e) {
						cliente.setDataNascimento(null);
					}
				}
				cliente.setEnderecos(enderecos);
				cliente.setTelefone(telefone);
			}
			if(operacao.equals("CONSULTAR")) {
				String id = request.getParameter("id");
				if(id != null) {
					cliente.setId(Long.valueOf(id));
				}
				else {
					String nome = request.getParameter("nome");
					String cpf = request.getParameter("cpf");
					String email = request.getParameter("email");
					String genero = request.getParameter("genero");
					if(genero == null) {
						genero = "";
					}
					cliente.setNome(nome);
					cliente.setCpf(cpf);
					cliente.setEmail(email);
					if(!genero.isEmpty()) {
						cliente.setGenero(genero.charAt(0));					
					}
				}
			}
			if(operacao.equals("ALTERAR")) {
				String id = request.getParameter("id");
				if(id != null) {
					cliente.setId(Long.valueOf(id));
				}
				String ativo = request.getParameter("ativo");
				String nome = request.getParameter("nome");
				String cpf = request.getParameter("cpf");
				String genero = request.getParameter("genero");
				String email = request.getParameter("email");
				String dataNascimento = request.getParameter("dataNascimento");
				String ddd = request.getParameter("ddd");
				String numeroTel = request.getParameter("numeroTel");
				String tipoTelefoneId = request.getParameter("tipoTelefone");
				String idTelefone = request.getParameter("idTelefone");
				
				Telefone telefone = new Telefone();
				telefone.setId(Long.valueOf(idTelefone));
				telefone.setDdd(ddd);
				telefone.setNumero(numeroTel);
				TipoTelefone tipoTelefone = new TipoTelefone();
				tipoTelefone.setId(Long.valueOf(tipoTelefoneId));
				telefone.setTipoTelefone(tipoTelefone);
				
				if(ativo != null && ativo.equals("true")) {
					cliente.setAtivo(true);
				}
				cliente.setNome(nome);
				cliente.setCpf(cpf);
				cliente.setGenero(genero.charAt(0));
				cliente.setEmail(email);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dataFormatada;
				if(dataNascimento != null) {
					try {
						dataFormatada = sdf.parse(dataNascimento);
						cliente.setDataNascimento(dataFormatada);
					} catch (ParseException e) {
						cliente.setDataNascimento(null);
					}
				}
				cliente.setTelefone(telefone);
				cliente.setEnderecos(new ArrayList<Endereco>());
			}
			if(operacao.equals("EXCLUIR")) {
				String id = request.getParameter("id");
				cliente.setId(Long.valueOf(id));
				cliente.setCartoesCredito(new ArrayList<CartaoCredito>());
				cliente.setEnderecos(new ArrayList<Endereco>());
				
			}
		}
		return cliente;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/clienteForm")) {
			request.getRequestDispatcher("WEB-INF/jsp/cliente/form.jsp").forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/clienteList")) {
			request.getRequestDispatcher("WEB-INF/jsp/cliente/list.jsp").forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/clienteEdit")) {
			List<Cliente> listaCliente = (List<Cliente>) object;
			Cliente cliente = listaCliente.get(0);
			request.setAttribute("cliente", cliente);
			request.setAttribute("operacao", "ALTERAR");
			request.getRequestDispatcher("WEB-INF/jsp/cliente/form.jsp").forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/clienteView")) {
			List<Cliente> listaCliente = (List<Cliente>) object;
			Cliente cliente = listaCliente.get(0);
			request.setAttribute("cliente", cliente);
			request.getRequestDispatcher("WEB-INF/jsp/cliente/view.jsp").forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/clienteSalvar")) {
			if(object == null) {
				String sucesso = "Cliente cadastrado com sucesso";
				request.setAttribute("sucesso", sucesso);
				request.getRequestDispatcher("WEB-INF/jsp/cliente/list.jsp").forward(request, response);
				return;
			}
			String mensagem = (String) object;
			String[] mensagens = mensagem.split(":");
			Cliente cliente = (Cliente) this.getEntidade(request);
			request.setAttribute("cliente", cliente);
			request.setAttribute("endereco", cliente.getEnderecos().get(0));
			request.setAttribute("mensagens", mensagens);
			request.getRequestDispatcher("WEB-INF/jsp/cliente/form.jsp").forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/clienteConsultar")) {
			if(object == null) {
				String erro = "Nenhum cliente encontrado";
				request.setAttribute("erro", erro);
				request.getRequestDispatcher("WEB-INF/jsp/cliente/list.jsp").forward(request, response);
				return;
			}
			List<Cliente> consulta = (List<Cliente>) object;
			request.setAttribute("consulta", consulta);
			request.getRequestDispatcher("WEB-INF/jsp/cliente/list.jsp").forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/clienteAlterar")) {
			if (object != null) {
				String mensagem = (String) object;
				String[] mensagens = mensagem.split(":");
				Cliente cliente = (Cliente) this.getEntidade(request);
				request.setAttribute("cliente", cliente);
				request.setAttribute("mensagens", mensagens);
				request.getRequestDispatcher("WEB-INF/jsp/cliente/form.jsp").forward(request, response);
				return;
			}
			String sucesso = "Alteração efetuada com sucesso";
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("WEB-INF/jsp/cliente/list.jsp").forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/clienteExcluir")) {
			String sucesso = (String) object;
			request.setAttribute("sucesso", sucesso);
			request.getRequestDispatcher("WEB-INF/jsp/cliente/list.jsp").forward(request, response);
		}
		if(uri.equals(contexto + "/clienteAtivar")) {
			if(object == null) {
				String erro = "Nenhum cliente encontrado para inativar";
				request.setAttribute("erro", erro);
				request.getRequestDispatcher("WEB-INF/jsp/cliente/list.jsp").forward(request, response);
				return;
			}
			List<Cliente> listaCliente = (List<Cliente>) object;
			Cliente cliente = listaCliente.get(0);
			cliente.setAtivo(true);
			request.setAttribute("entidade", cliente);
			request.setAttribute("identificacao", cliente.getNome());
			request.setAttribute("nomeEntidade", "Cliente");
			request.getRequestDispatcher("WEB-INF/jsp/ativacao/form.jsp").forward(request, response);
			return;
		}
		if(uri.equals(contexto + "/clienteInativar")) {
			if(object == null) {
				String erro = "Nenhum cliente encontrado para inativar";
				request.setAttribute("erro", erro);
				request.getRequestDispatcher("WEB-INF/jsp/cliente/list.jsp").forward(request, response);
				return;
			}
			List<Cliente> listaCliente = (List<Cliente>) object;
			Cliente cliente = listaCliente.get(0);
			cliente.setAtivo(false);
			request.setAttribute("entidade", cliente);
			request.setAttribute("identificacao", cliente.getNome());
			request.setAttribute("nomeEntidade", "Cliente");
			request.getRequestDispatcher("WEB-INF/jsp/inativacao/form.jsp").forward(request, response);
			return;
		}
	}

}
