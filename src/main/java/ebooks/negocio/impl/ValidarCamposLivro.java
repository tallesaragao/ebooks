package ebooks.negocio.impl;

import java.util.List;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import ebooks.modelo.Autor;
import ebooks.modelo.Categoria;
import ebooks.modelo.Dimensoes;
import ebooks.modelo.Editora;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Livro;
import ebooks.modelo.Precificacao;
import ebooks.negocio.IStrategy;

public class ValidarCamposLivro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Livro livro = (Livro) entidade;
		if(livro.getTitulo() == null || livro.getTitulo().equals("")) {
			sb.append("Título do livro é obrigatório:");
		}
		if(livro.getAno() == null || livro.getAno().equals("")) {
			sb.append("Ano do livro é obrigatório:");
		}
		if(livro.getEdicao() == null || livro.getEdicao().equals("")) {
			sb.append("Edição do livro é obrigatória:");
		}
		if(livro.getIsbn() == null || livro.getIsbn().equals("")) {
			sb.append("ISBN do livro é obrigatório:");
		}
		else {
			String isbn = livro.getIsbn().trim().replace("-", "");
			for(int i = 0; i < isbn.length(); i++) {
				//se o caracter iterado dentro da String isbn não for numérico
				if(!Character.isDigit(isbn.charAt(i))) {
					sb.append("ISBN deve ser numérico");
					break;
				}
			}
			if(isbn.length() != 13) {
				sb.append("ISBN deve ter 13 dígitos");
			}
		}
		if(livro.getNumeroPaginas() == null || livro.getNumeroPaginas().equals("")) {
			sb.append("Número de páginas do livro é obrigatório:");
		}
		if(livro.getQuantidade() == null || livro.getQuantidade() <= 0) {
			sb.append("Quantidade do livro deve ser maior que zero:");
		}
		if(livro.getSinopse() == null || livro.getSinopse().equals("")) {
			sb.append("Sinopse do livro é obrigatória:");
		}

		Dimensoes dimensoes = livro.getDimensoes();
		if(dimensoes.getAltura() == null || dimensoes.getAltura() <= 0) {
			sb.append("Altura do livro deve ser maior que zero:");
		}
		if(dimensoes.getLargura() == null || dimensoes.getLargura() <= 0) {
			sb.append("Largura do livro deve ser maior que zero:");
		}
		if(dimensoes.getProfundidade() == null || dimensoes.getProfundidade() <= 0) {
			sb.append("Profundidade do livro deve ser maior que zero:");
		}
		if(dimensoes.getPeso() == null || dimensoes.getPeso() <= 0) {
			sb.append("Peso do livro deve ser maior que zero:");
		}

		Editora editora = livro.getEditora();
		if(editora.getNome() == null || editora.getNome().equals("")) {
			sb.append("Nome da editora é obrigatório:");
		}
		if(editora.getCnpj() == null || editora.getCnpj().equals("")) {
			sb.append("CNPJ da editora é obrigatório:");
		}
		else {
			CNPJValidator valCnpj = new CNPJValidator();
			try {
				valCnpj.assertValid(editora.getCnpj());
			}
			catch(InvalidStateException e) {
				sb.append("CNPJ da editora inválido:");
			}
		}
		if(editora.getRazaoSocial() == null || editora.getRazaoSocial().equals("")) {
			sb.append("Razão Social da editora é obrigatória:");
		}
		
		List<Categoria> categorias = livro.getCategorias();
		if(categorias.size() == 0) {
			sb.append("Informar as categorias do livro:");
		}
		
		List<Autor> autores = livro.getAutores();
		for(Autor autor : autores) {
			if(autor.getNome() == null || autor.getNome().equals("")) {
				sb.append("Nome do autor é obrigatório:");
			}
			if(autor.getCpf() == null || autor.getCpf().equals("")) {
				sb.append("CPF do autor é obrigatório:");
			}
			else {
				CPFValidator valCpf = new CPFValidator();
				try {
					valCpf.assertValid(autor.getCpf());
				}
				catch(InvalidStateException e) {
					sb.append("CPF do autor inválido:");
				}
			}
		}
		Precificacao precificacao = livro.getPrecificacao();
		if(precificacao.getPrecoCusto() == null || precificacao.getPrecoCusto() <= 0) {
			sb.append("Preço de custo do livro deve ser maior que zero:");
		}
		livro.getGrupoPrecificacao();
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
