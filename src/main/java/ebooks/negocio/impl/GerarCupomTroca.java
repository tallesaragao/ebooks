package ebooks.negocio.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ebooks.dao.CupomTrocaDAO;
import ebooks.dao.IDAO;
import ebooks.dao.TrocaDAO;
import ebooks.modelo.CupomTroca;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.ItemPedido;
import ebooks.modelo.ItemTroca;
import ebooks.modelo.Livro;
import ebooks.modelo.Troca;
import ebooks.negocio.IStrategy;

public class GerarCupomTroca implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Troca troca = (Troca) entidade;
		IStrategy strategy;
		try {
			IDAO dao = new TrocaDAO();
			List<EntidadeDominio> consulta = dao.consultar(troca);
			if(!consulta.isEmpty()) {
				CupomTroca cupom = new CupomTroca();
				strategy = new GerarCodigoCupom();
				strategy.processar(cupom);
				BigDecimal valorCupom = new BigDecimal("0");
				List<ItemTroca> itensTroca = troca.getItensTroca();
				for(ItemTroca itemTroca : itensTroca) {
					BigDecimal subtotal = new BigDecimal("0");
					ItemPedido itemPedido = itemTroca.getItemPedido();
					Livro livro = itemPedido.getLivro();
					BigDecimal precoVenda = livro.getPrecificacao().getPrecoVenda();
					subtotal = subtotal.add(precoVenda);
					subtotal = subtotal.multiply(new BigDecimal(itemTroca.getQuantidadeTrocada().toString()));
					valorCupom = valorCupom.add(subtotal);
				}
				cupom.setValor(valorCupom);
				cupom.setAtivo(true);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date validade = sdf.parse("01/01/2099");
				cupom.setValidade(validade);
				strategy = new ComplementarDtCadastro();
				strategy.processar(cupom);
				dao = new CupomTrocaDAO();
				boolean salvo = dao.salvar(cupom);
				if(salvo) {
					troca.setCupomTroca(cupom);
					dao = new TrocaDAO();
					boolean alterado = dao.alterar(troca);
					if(!alterado) {
						sb.append("Problema ao vincular o cupom à troca:");
					}
				}
				else {
					sb.append("Problema ao salvar o cupom de troca:");
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			sb.append("Problema na geração do cupom de troca:");
		}
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
