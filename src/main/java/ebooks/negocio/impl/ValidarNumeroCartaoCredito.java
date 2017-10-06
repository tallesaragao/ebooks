package ebooks.negocio.impl;

import ebooks.modelo.CartaoCredito;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class ValidarNumeroCartaoCredito implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		CartaoCredito cartaoCredito = (CartaoCredito) entidade;
		String numero = cartaoCredito.getNumero();
		// Algoritmo de Luhn, para fazer a verificação do número do cartão
		int soma1 = 0, soma2 = 0;
		// Inverte o número informado
		String numeroReverso = new StringBuilder(numero).reverse().toString();
		for (int i = 0; i < numeroReverso.length(); i++) {
			int digito = Character.digit(numeroReverso.charAt(i), 10);
			// Se o número é par, o valor é somado a soma1
			if (i % 2 == 0) {
				soma1 += digito;
			} else {
				soma2 += 2 * digito;
				if (digito >= 5) {
					soma2 -= 9;
				}
			}
		}
		// Se o módulo da divisão de soma1+soma2 por 10 for diferente de zero, significa que o cartão é inválido
		if (!((soma1 + soma2) % 10 == 0)) {
			sb.append("Número do cartão é inválido:");
		}
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
