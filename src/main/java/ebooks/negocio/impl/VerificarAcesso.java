package ebooks.negocio.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ebooks.modelo.Acesso;
import ebooks.modelo.Cliente;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.Login;
import ebooks.modelo.PerfilAcesso;
import ebooks.modelo.Uri;
import ebooks.negocio.IStrategy;

public class VerificarAcesso implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder sb = new StringBuilder();
		Acesso acesso = (Acesso) entidade;
		String contexto = "/ebooks";
		boolean acessivel = false;
		boolean paginaEncontrada = false;
		HttpServletRequest request = (HttpServletRequest) acesso.getRequest();
		String caminhoAcesso = request.getRequestURI();
		if(caminhoAcesso.contains("resources") 
				|| caminhoAcesso.equals(contexto + "/livroList")
				|| caminhoAcesso.equals(contexto + "/livroConsultar")
				|| caminhoAcesso.equals(contexto + "/loginSite")
				|| caminhoAcesso.equals(contexto + "/loginConsultar")) {
			acessivel = true;
			paginaEncontrada = true;
		}
		else {
			Login login = acesso.getLogin();
			if(login != null) {
				PerfilAcesso perfilAcesso = login.getPerfilAcesso();
				List<Uri> urisAcessiveis = perfilAcesso.getUrisAcessiveis();
				for(Uri uri : urisAcessiveis) {
					if(caminhoAcesso.equals(contexto + uri.getCaminho())) {
						paginaEncontrada = true;
						if(perfilAcesso.getNome().equals("Cliente")) {
							if(caminhoAcesso.equals(contexto + "/clienteView") 
									|| caminhoAcesso.equals(contexto + "/clienteAlterar")) {
								String id = request.getParameter("id");
								Cliente cliente = login.getCliente();
								if(cliente != null) {
									long idCliente = cliente.getId();
									if(idCliente == Long.valueOf(id)) {
										acessivel = true;
										break;
									}
								}
							}
							else if(caminhoAcesso.equals(contexto + "/cartaoCreditoEdit")
									|| caminhoAcesso.equals(contexto + "/cartaoCreditoExcluir")
									|| caminhoAcesso.equals(contexto + "/enderecoEdit")
									|| caminhoAcesso.equals(contexto + "/enderecoExcluir")
									|| caminhoAcesso.equals(contexto + "/pedidoView")
									|| caminhoAcesso.equals(contexto + "/trocaView")) {
								String id = request.getParameter("idCliente");
								Cliente cliente = login.getCliente();
								if(cliente != null) {
									long idCliente = cliente.getId();
									if(idCliente == Long.valueOf(id)) {
										acessivel = true;
										break;
									}
								}
							}
							else {
								acessivel = true;
							}
						}
						else {
							acessivel = true;
						}
					}
				}
			}
		}
		if(!paginaEncontrada) {
			sb.append("Página não encontrada:");
		}
		acesso.setLiberado(acessivel);
		
		if(sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

}
