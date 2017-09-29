package ebooks.negocio.impl;

import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;
import ebooks.negocio.IStrategy;

public class ValidarSenha implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Login login = (Login) entidade;
		StringBuilder sb = new StringBuilder();
		// Verifica se a senha e a confimação são diferentes
		if(!login.getSenha().equals(login.getSenhaConfirmacao())) {
			sb.append("A senhas digitadas não são iguais:");
		}
		String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,20}$";
		if(!login.getSenha().matches(regex)) {
			sb.append("A senha deve conter maiúsculas, minúsculas, números e caracteres especiais");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
