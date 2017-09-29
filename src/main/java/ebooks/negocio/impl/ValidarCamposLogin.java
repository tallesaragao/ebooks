package ebooks.negocio.impl;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;
import ebooks.negocio.IStrategy;

public class ValidarCamposLogin implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Login login = (Login) entidade;
		StringBuilder sb = new StringBuilder();
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
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
