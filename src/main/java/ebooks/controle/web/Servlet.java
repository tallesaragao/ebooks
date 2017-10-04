package ebooks.controle.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ebooks.controle.AlterarCommand;
import ebooks.controle.ConsultarCommand;
import ebooks.controle.ExcluirCommand;
import ebooks.controle.ICommand;
import ebooks.controle.SalvarCommand;
import ebooks.modelo.EntidadeDominio;

public class Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Map<String, IViewHelper> vhs;
	private Map<String, ICommand> commands;
	
	public Servlet() {
		commands = new HashMap<String, ICommand>();
		commands.put("SALVAR", new SalvarCommand());
		commands.put("ALTERAR", new AlterarCommand());
		commands.put("CONSULTAR", new ConsultarCommand());
		commands.put("EXCLUIR", new ExcluirCommand());
		
		String contextoApp = "/ebooks";
		
		vhs = new HashMap<String, IViewHelper>();
		vhs.put(contextoApp + "/home", new CategoriaVH());
		vhs.put(contextoApp + "/categoriaForm", new CategoriaVH());
		vhs.put(contextoApp + "/categoriaList", new CategoriaVH());
		vhs.put(contextoApp + "/categoriaEdit", new CategoriaVH());
		vhs.put(contextoApp + "/categoriaSalvar", new CategoriaVH());
		vhs.put(contextoApp + "/categoriaConsultar", new CategoriaVH());
		vhs.put(contextoApp + "/categoriaAlterar", new CategoriaVH());
		vhs.put(contextoApp + "/categoriaExcluir", new CategoriaVH());
		vhs.put(contextoApp + "/livroForm", new LivroVH());
		vhs.put(contextoApp + "/livroFormCategorias", new CategoriaVH());
		vhs.put(contextoApp + "/livroFormGruposPrecificacao", new GrupoPrecificacaoVH());
		vhs.put(contextoApp + "/livroList", new LivroVH());
		vhs.put(contextoApp + "/livroEdit", new LivroVH());
		vhs.put(contextoApp + "/livroSalvar", new LivroVH());
		vhs.put(contextoApp + "/livroConsultar", new LivroVH());
		vhs.put(contextoApp + "/livroAlterar", new LivroVH());
		vhs.put(contextoApp + "/livroExcluir", new LivroVH());
		vhs.put(contextoApp + "/loginCliente", new LoginVH());
		vhs.put(contextoApp + "/loginForm", new LoginVH());
		vhs.put(contextoApp + "/loginSalvar", new LoginVH());
		vhs.put(contextoApp + "/loginConsultar", new LoginVH());
		vhs.put(contextoApp + "/loginAlterar", new LoginVH());
		vhs.put(contextoApp + "/clienteForm", new ClienteVH());
		vhs.put(contextoApp + "/clienteList", new ClienteVH());
		vhs.put(contextoApp + "/clienteEdit", new ClienteVH());
		vhs.put(contextoApp + "/clienteView", new ClienteVH());
		vhs.put(contextoApp + "/clienteSalvar", new ClienteVH());
		vhs.put(contextoApp + "/clienteAlterar", new ClienteVH());
		vhs.put(contextoApp + "/clienteExcluir", new ClienteVH());
		vhs.put(contextoApp + "/clienteConsultar", new ClienteVH());
		vhs.put(contextoApp + "/cartaoCreditoForm", new CartaoCreditoVH());
		vhs.put(contextoApp + "/cartaoCreditoList", new CartaoCreditoVH());
		vhs.put(contextoApp + "/cartaoCreditoEdit", new CartaoCreditoVH());
		vhs.put(contextoApp + "/cartaoCreditoView", new CartaoCreditoVH());
		vhs.put(contextoApp + "/cartaoCreditoSalvar", new CartaoCreditoVH());
		vhs.put(contextoApp + "/cartaoCreditoAlterar", new CartaoCreditoVH());
		vhs.put(contextoApp + "/cartaoCreditoExcluir", new CartaoCreditoVH());
		vhs.put(contextoApp + "/cartaoCreditoConsultar", new CartaoCreditoVH());
		vhs.put(contextoApp + "/enderecoForm", new EnderecoVH());
		vhs.put(contextoApp + "/enderecoList", new EnderecoVH());
		vhs.put(contextoApp + "/enderecoEdit", new EnderecoVH());
		vhs.put(contextoApp + "/enderecoView", new EnderecoVH());
		vhs.put(contextoApp + "/enderecoSalvar", new EnderecoVH());
		vhs.put(contextoApp + "/enderecoAlterar", new EnderecoVH());
		vhs.put(contextoApp + "/enderecoExcluir", new EnderecoVH());
		vhs.put(contextoApp + "/enderecoConsultar", new EnderecoVH());
	}
		
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operacao = request.getParameter("operacao");
		Object obj = null;
		String uri = request.getRequestURI();
		IViewHelper vh = vhs.get(uri);
		if(vh != null) {
			if(operacao != null && !operacao.equals("")) {
				EntidadeDominio entidade = vh.getEntidade(request);
				ICommand cmd = commands.get(operacao);
				obj = cmd.executar(entidade);
			}
			vh.setView(obj, request, response);
		}
	}
}
