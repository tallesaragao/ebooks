package ebooks.negocio.impl;

import java.util.Random;

import ebooks.modelo.CupomTroca;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class GerarCodigoCupom implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		CupomTroca cupom = (CupomTroca) entidade;
		String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random r = new Random();
		String codigoCupom = "";
		for(int i = 0; i < 10; i++) {
			codigoCupom += alfabeto.charAt(r.nextInt(25));
		}
		cupom.setCodigo(codigoCupom);
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
