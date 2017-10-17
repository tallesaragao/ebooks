package ebooks.negocio.impl;

import java.util.Date;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;
import ebooks.modelo.Telefone;
import ebooks.negocio.IStrategy;

public class ValidarCamposCliente implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Cliente cliente = (Cliente) entidade;
		if (cliente.getNome() == null || cliente.getNome().equals("")) {
			sb.append("Nome do cliente deve estar preechido:");
		}
		if (cliente.getEmail() == null || cliente.getEmail().equals("")) {
			sb.append("E-mail deve estar preechido:");
		}
		if (cliente.getDataNascimento() == null || cliente.getDataNascimento().after(new Date())) {
			sb.append("Data de nascimento inválida:");
		}
		if (cliente.getGenero() == null || cliente.getGenero().charValue() == ' ') {
			sb.append("Gênero deve ser informado:");
		}
		if (cliente.getCpf() == null || cliente.getCpf().equals("")) {
			sb.append("CPF deve estar preechido:");
		}
		else {
			CPFValidator valCpf = new CPFValidator();
			try {
				valCpf.assertValid(cliente.getCpf());
			}
			catch(InvalidStateException e) {
				sb.append("CPF do cliente inválido:");
			}
		}
		for(Endereco end : cliente.getEnderecos()) {
			ValidarCamposEndereco valEnd = new ValidarCamposEndereco();
			String resultado = valEnd.processar(end);
			if(resultado != null) {
				sb.append(resultado);
			}
		}
		Telefone telefone = cliente.getTelefone();
		if(telefone != null) {
			if (telefone.getDdd() == null || telefone.getDdd().equals("")) {
				sb.append("DDD deve estar preenchido:");
			}
			if (telefone.getNumero() == null || telefone.getNumero().equals("")) {
				sb.append("Número do telefone deve estar preechido:");
			}
			if (telefone.getTipoTelefone() == null || telefone.getTipoTelefone().getId() == null) {
				sb.append("Tipo do telefone deve ser selecionado:");
			}
		}
		else {
			sb.append("Dados do telefone devem estar preenchidos:");
		}
		Login login = cliente.getLogin();
		if(login != null) {
			if(login.getUsuario() == null || login.getUsuario().equals("")) {
				sb.append("Nome de usuário é obrigatório:");
			}
			if(login.getSenha() == null || login.getSenha().equals("")) {
				sb.append("Senha é obrigatória:");
			}
			if(login.getSenhaConfirmacao() == null || login.getSenhaConfirmacao().equals("")) {
				sb.append("É obrigatório confirmar a senha:");
			}
			else {
				ValidarSenha validarSenha = new ValidarSenha();
				String resultado = validarSenha.processar(login);
				if(resultado != null) {
					sb.append(resultado);
				}
			}
		}
		if (sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}
}
