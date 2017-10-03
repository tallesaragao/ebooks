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
import javax.servlet.http.HttpSession;

import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;
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
				
				HttpSession session = request.getSession();
				Login login = (Login) session.getAttribute("login");

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
				cliente.setLogin(login);
				cliente.setEnderecos(enderecos);
				cliente.setTelefone(telefone);
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
		}
		
	}

}
