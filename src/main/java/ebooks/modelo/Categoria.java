package ebooks.modelo;

import java.util.List;

public class Categoria extends EntidadeDominio {
	private String nome;
	private List<Subcategoria> subcategorias;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Subcategoria> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<Subcategoria> subcategorias) {
		this.subcategorias = subcategorias;
	}
}
