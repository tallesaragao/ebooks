package ebooks.controle.web.vh;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ebooks.aplicacao.Resultado;
import ebooks.modelo.Analise;
import ebooks.modelo.Categoria;
import ebooks.modelo.EntidadeDominio;

public class AnaliseVH implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		Analise analise = new Analise();
		String operacao = request.getParameter("operacao");
		if(operacao.equals("CONSULTAR")) {
			String mesInicial = request.getParameter("mesInicial");
			String anoInicial = request.getParameter("anoInicial");
			String mesFinal = request.getParameter("mesFinal");
			String anoFinal = request.getParameter("anoFinal");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dataFormatada;
			if(mesInicial != null && anoInicial != null && mesFinal != null && anoFinal != null) {
				try {
					boolean anoFinalBissexto = false;
					long anoFinalLong = Long.valueOf(anoFinal);
					if(anoFinalLong % 4 == 0) {
						if(anoFinalLong % 100 == 0) {
							if(anoFinalLong % 400 != 0) {
								anoFinalBissexto = true;
							}
						}
						else {
							anoFinalBissexto = true;
						}
					}
					String diaFinalMes = "0";
					switch(mesFinal) {
						case "02":
							diaFinalMes = "28";
							if(anoFinalBissexto) {
								diaFinalMes = "29";
							}
							break;
						case "04": case "06": case "09": case "11":
							diaFinalMes = "30";
							break;
						default:
							diaFinalMes = "31";
							break;						
					}
					String dataInicialString = anoInicial + "-" + mesInicial  + "-01";
					String dataFinalString = anoFinal + "-" + mesFinal  + "-" + diaFinalMes;
					dataFormatada = sdf.parse(dataInicialString);
					analise.setDataInicial(dataFormatada);
					dataFormatada = sdf.parse(dataFinalString);
					analise.setDataFinal(dataFormatada);
				} catch (ParseException e) {
					analise.setDataInicial(null);
					analise.setDataFinal(null);
				}
			}
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
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String contexto = request.getContextPath();
		String uri = request.getRequestURI();
		if(uri.equals(contexto + "/vendasAnalise")) {
			List<EntidadeDominio> listaEntidade = resultado.getEntidades();
			Analise analise = new Analise();
			if(listaEntidade != null) {
				analise = (Analise) listaEntidade.get(0);
				HttpSession session = request.getSession();
				session.setAttribute("grafico", analise.getGrafico());
				String resposta = resultado.getResposta();
				if(resposta != null) {
					String[] mensagens = resposta.split(":");
					request.setAttribute("mensagens", mensagens);
				}
			}
			List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
			if(categorias == null || categorias.isEmpty()) {
				request.getRequestDispatcher("vendasAnaliseCategorias?operacao=CONSULTAR").forward(request, response);
			}
			else {
				request.getRequestDispatcher("WEB-INF/jsp/analise/view.jsp").forward(request, response);
			}
			analise = (Analise) getEntidade(request);
			request.setAttribute("analise", analise);
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
