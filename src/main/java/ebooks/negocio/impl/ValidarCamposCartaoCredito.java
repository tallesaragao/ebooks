package ebooks.negocio.impl;

import ebooks.modelo.CartaoCredito;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class ValidarCamposCartaoCredito implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		CartaoCredito cartaoCredito = (CartaoCredito) entidade;
		if(cartaoCredito != null) {
			if(cartaoCredito.getBandeira() == null || cartaoCredito.getBandeira().getId() == null) {
				sb.append("Bandeira do cartão deve ser informada:");
			}
			if(cartaoCredito.getNumero() == null || cartaoCredito.getNumero().equals("")) {
				sb.append("Número do cartão é obrigatório:");
			}
			else {
				ValidarNumeroCartaoCredito valNumCarCred = new ValidarNumeroCartaoCredito();
				String resultado = valNumCarCred.processar(cartaoCredito);
				if(resultado != null) {
					sb.append(resultado);
				}
			}
			if(cartaoCredito.getNomeTitular() == null || cartaoCredito.getNomeTitular().equals("")) {
				sb.append("Nome do titular é obrigatório:");
			}	
			if(cartaoCredito.getDataVencimento() == null) {
				sb.append("Data de vencimento inválida:");
			}
			if(cartaoCredito.getCodigoSeguranca() == null || cartaoCredito.getCodigoSeguranca().equals("")) {
				sb.append("Codigo de segurança deve ser informado:");
			}
		}
		else {
			sb.append("Dados do cartão de crédito devem estar preenchidos:");
		}
		ComplementarDtCadastro compDtCad = new ComplementarDtCadastro();
		compDtCad.processar(cartaoCredito);
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}


}
