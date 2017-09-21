package ebooks.controle.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.modelo.EntidadeDominio;

public interface IViewHelper {
	public EntidadeDominio getEntidade(HttpServletRequest request);

	public void setView(Object obj, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
