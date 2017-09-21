package ebooks.modelo;

import java.util.List;

public class Autor extends PessoaFisica {
	private List<Livro> livros;

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
}
