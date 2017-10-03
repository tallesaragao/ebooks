package ebooks.negocio.impl;

import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.negocio.IStrategy;

public class ValidarCamposEndereco implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Endereco endereco = (Endereco) entidade;
		if(endereco != null) {
			if(endereco.getTipoEndereco() == null || endereco.getTipoEndereco().getId() == null) {
				sb.append("Tipo de endereço deve ser informado:");
			}
			if(endereco.getIdentificacao() == null || endereco.getIdentificacao().equals("")) {
				sb.append("A idenficação do endereço deve ser composta de uma frase curta:");
			}
			if(endereco.getLogradouro() == null || endereco.getLogradouro().equals("")) {
				sb.append("Logradouro deve estar preenchido:");
			}	
			if(endereco.getNumero() == null || endereco.getNumero().equals("")) {
				sb.append("Número do endereço deve estar preenchido:");
			}
			if(endereco.getBairro() == null || endereco.getBairro().equals("")) {
				sb.append("Bairro deve estar preenchido:");
			}
			if(endereco.getCep() == null || endereco.getCep().equals("")) {
				sb.append("CEP deve estar preenchido:");
			}
			if(endereco.getCidade() == null || endereco.getCidade().equals("")) {
				sb.append("Cidade deve estar preenchida:");
			}
			if(endereco.getEstado() == null || endereco.getEstado().equals("")) {
				sb.append("Estado deve estar preenchido:");
			}
			DefinirEnderecoPrincipal defEndPri = new DefinirEnderecoPrincipal();
			String resultado = defEndPri.processar(endereco);
			if(resultado != null) {
				sb.append(resultado);
			}
		}
		else {
			sb.append("Dados do endereço devem estar preenchidos:");
		}
		ComplementarDtCadastro compDtCad = new ComplementarDtCadastro();
		compDtCad.processar(endereco);
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}
	
}
