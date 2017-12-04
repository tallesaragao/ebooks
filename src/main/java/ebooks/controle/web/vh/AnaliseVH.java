package ebooks.controle.web.vh;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.modelo.Analise;
import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;

public class AnaliseVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Analise analise = new Analise();
		analise.setSession(request.getSession());
		String operacao = request.getParameter("operacao");
		if(operacao.equals("CONSULTAR")) {
			List<Categoria> categorias = new ArrayList<>();
			String[] idsCategorias = request.getParameterValues("categorias");
			if(idsCategorias != null) {
				for(String idCategoria : idsCategorias) {
					Categoria categoria = new Categoria();
					categoria.setId(Long.valueOf(idCategoria));
					categorias.add(categoria);
				}
				analise.setCategorias(categorias);
			}
		}
		return analise;
	}

	@Override
	public void setView(Object object, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/vendasAnalise")) {
			List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
			if(categorias == null || categorias.isEmpty()) {
				request.getRequestDispatcher("vendasAnaliseCategorias?operacao=CONSULTAR").forward(request, response);
			}
			else {
				request.getRequestDispatcher("WEB-INF/jsp/analise/view.jsp").forward(request, response);
			}
		}
		if(uri.equals(contexto + "/graficoImagem")) {
			HttpSession session = request.getSession();
			BufferedImage grafico = (BufferedImage) session.getAttribute("grafico");
			response.setContentType("image/png");
			if(grafico != null) {
				ImageIO.write(grafico, "png", response.getOutputStream());
				session.removeAttribute("grafico");
			}
		}
	}

}
