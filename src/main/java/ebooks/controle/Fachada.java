package ebooks.controle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ebooks.dao.BandeiraDAO;
import ebooks.dao.CartaoCreditoDAO;
import ebooks.dao.CategoriaDAO;
import ebooks.dao.ClienteDAO;
import ebooks.dao.EnderecoDAO;
import ebooks.dao.GrupoPrecificacaoDAO;
import ebooks.dao.IDAO;
import ebooks.dao.LivroDAO;
import ebooks.dao.LoginDAO;
import ebooks.dao.TipoEnderecoDAO;
import ebooks.dao.TipoTelefoneDAO;
import ebooks.modelo.Bandeira;
import ebooks.modelo.CartaoCredito;
import ebooks.modelo.Categoria;
import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.GrupoPrecificacao;
import ebooks.modelo.Livro;
import ebooks.modelo.Login;
import ebooks.modelo.TipoEndereco;
import ebooks.modelo.TipoTelefone;
import ebooks.negocio.IStrategy;
import ebooks.negocio.impl.AtivadorClientePrimeiroCadastro;
import ebooks.negocio.impl.AtivadorLivroPrimeiroCadastro;
import ebooks.negocio.impl.ComplementarDtCadastro;
import ebooks.negocio.impl.GeradorCodigoLivro;
import ebooks.negocio.impl.GeradorPrecoLivro;
import ebooks.negocio.impl.ValidarCamposCartaoCredito;
import ebooks.negocio.impl.ValidarCamposCategoria;
import ebooks.negocio.impl.ValidarCamposCliente;
import ebooks.negocio.impl.ValidarCamposEndereco;
import ebooks.negocio.impl.ValidarCamposLivro;
import ebooks.negocio.impl.ValidarCamposLogin;
import ebooks.negocio.impl.VerificarExistenciaCliente;

public class Fachada implements IFachada {

	private static final String SALVAR = "SALVAR";
	private static final String ALTERAR = "ALTERAR";
	private static final String CONSULTAR = "CONSULTAR";
	private static final String EXCLUIR = "EXCLUIR";

	private Map<String, Map<String, List<IStrategy>>> requisitos;
	private Map<String, IDAO> daos;

	public Fachada() {
		ComplementarDtCadastro compDtCad = new ComplementarDtCadastro();
		ValidarCamposCategoria valCampCat = new ValidarCamposCategoria();
		ValidarCamposLivro valCampLivro = new ValidarCamposLivro();
		GeradorCodigoLivro gerCodLivro = new GeradorCodigoLivro();
		GeradorPrecoLivro gerPrecLivro = new GeradorPrecoLivro();
		AtivadorLivroPrimeiroCadastro ativLivroPrimCad = new AtivadorLivroPrimeiroCadastro();
		ValidarCamposLogin valCampLogin = new ValidarCamposLogin();
		ValidarCamposCliente valCampCli = new ValidarCamposCliente();
		AtivadorClientePrimeiroCadastro ativCliPrimCad = new AtivadorClientePrimeiroCadastro();
		ValidarCamposEndereco valCampEnd = new ValidarCamposEndereco();
		ValidarCamposCartaoCredito valCampCarCred= new ValidarCamposCartaoCredito();
		VerificarExistenciaCliente verExistCli = new VerificarExistenciaCliente();

		Map<String, List<IStrategy>> contextoCat = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lSalvarCat = new ArrayList<IStrategy>();
		lSalvarCat.add(compDtCad);
		lSalvarCat.add(valCampCat);
		List<IStrategy> lAlterarCat = new ArrayList<IStrategy>();
		lAlterarCat.add(valCampCat);
		List<IStrategy> lExcluirCat = new ArrayList<IStrategy>();
		List<IStrategy> lConsultarCat = new ArrayList<IStrategy>();
		contextoCat.put(SALVAR, lSalvarCat);
		contextoCat.put(ALTERAR, lAlterarCat);
		contextoCat.put(EXCLUIR, lExcluirCat);
		contextoCat.put(CONSULTAR, lConsultarCat);

		Map<String, List<IStrategy>> contextoLivro = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lSalvarLivro = new ArrayList<IStrategy>();
		lSalvarLivro.add(compDtCad);
		lSalvarLivro.add(valCampLivro);
		lSalvarLivro.add(gerCodLivro);
		lSalvarLivro.add(gerPrecLivro);
		lSalvarLivro.add(ativLivroPrimCad);	
		List<IStrategy> lAlterarLivro = new ArrayList<IStrategy>();
		lAlterarLivro.add(valCampLivro);
		lAlterarLivro.add(gerPrecLivro);
		List<IStrategy> lExcluirLivro = new ArrayList<IStrategy>();
		List<IStrategy> lConsultarLivro = new ArrayList<IStrategy>();
		contextoLivro.put(SALVAR, lSalvarLivro);
		contextoLivro.put(ALTERAR, lAlterarLivro);
		contextoLivro.put(EXCLUIR, lExcluirLivro);
		contextoLivro.put(CONSULTAR, lConsultarLivro);
		

		Map<String, List<IStrategy>> contextoLogin = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lLoginSalvar = new ArrayList<>();
		lLoginSalvar.add(compDtCad);
		lLoginSalvar.add(valCampLogin);
		List<IStrategy> lLoginAlterar = new ArrayList<>();
		List<IStrategy> lLoginExcluir = new ArrayList<>();
		List<IStrategy> lLoginConsultar = new ArrayList<>();
		contextoLogin.put(SALVAR, lLoginSalvar);
		contextoLogin.put(ALTERAR, lLoginAlterar);
		contextoLogin.put(EXCLUIR, lLoginExcluir);
		contextoLogin.put(CONSULTAR, lLoginConsultar);
		
		Map<String, List<IStrategy>> contextoCliente = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lClienteSalvar = new ArrayList<>();
		lClienteSalvar.add(compDtCad);
		lClienteSalvar.add(valCampCli);
		lClienteSalvar.add(verExistCli);
		lClienteSalvar.add(ativCliPrimCad);
		List<IStrategy> lClienteAlterar = new ArrayList<>();
		lClienteAlterar.add(valCampCli);
		List<IStrategy> lClienteExcluir = new ArrayList<>();
		List<IStrategy> lClienteConsultar = new ArrayList<>();
		contextoCliente.put(SALVAR, lClienteSalvar);
		contextoCliente.put(ALTERAR, lClienteAlterar);
		contextoCliente.put(EXCLUIR, lClienteExcluir);
		contextoCliente.put(CONSULTAR, lClienteConsultar);
		
		Map<String, List<IStrategy>> contextoEndereco = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lEnderecoSalvar = new ArrayList<>();
		lEnderecoSalvar.add(valCampEnd);
		List<IStrategy> lEnderecoAlterar = new ArrayList<>();
		lEnderecoAlterar.add(valCampEnd);
		List<IStrategy> lEnderecoExcluir = new ArrayList<>();
		List<IStrategy> lEnderecoConsultar = new ArrayList<>();
		contextoEndereco.put(SALVAR, lEnderecoSalvar);
		contextoEndereco.put(ALTERAR, lEnderecoAlterar);
		contextoEndereco.put(EXCLUIR, lEnderecoExcluir);
		contextoEndereco.put(CONSULTAR, lEnderecoConsultar);
		
		Map<String, List<IStrategy>> contextoCarCred = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lCarCredSalvar = new ArrayList<>();
		lCarCredSalvar.add(valCampCarCred);
		List<IStrategy> lCarCredAlterar = new ArrayList<>();
		lCarCredAlterar.add(valCampCarCred);
		List<IStrategy> lCarCredExcluir = new ArrayList<>();
		List<IStrategy> lCarCredConsultar = new ArrayList<>();
		contextoCarCred.put(SALVAR, lCarCredSalvar);
		contextoCarCred.put(ALTERAR, lCarCredAlterar);
		contextoCarCred.put(EXCLUIR, lCarCredExcluir);
		contextoCarCred.put(CONSULTAR, lCarCredConsultar);
		
		requisitos = new HashMap<String, Map<String, List<IStrategy>>>();
		requisitos.put(Categoria.class.getName(), contextoCat);
		requisitos.put(Livro.class.getName(), contextoLivro);
		requisitos.put(Login.class.getName(), contextoLogin);
		requisitos.put(Cliente.class.getName(), contextoCliente);
		requisitos.put(Endereco.class.getName(), contextoEndereco);
		requisitos.put(CartaoCredito.class.getName(), contextoCarCred);
		
		daos = new HashMap<String, IDAO>();
		daos.put(Categoria.class.getName(), new CategoriaDAO());
		daos.put(Livro.class.getName(), new LivroDAO());
		daos.put(GrupoPrecificacao.class.getName(), new GrupoPrecificacaoDAO());
		daos.put(Login.class.getName(), new LoginDAO());
		daos.put(Cliente.class.getName(), new ClienteDAO());
		daos.put(Endereco.class.getName(), new EnderecoDAO());
		daos.put(CartaoCredito.class.getName(), new CartaoCreditoDAO());
		daos.put(Bandeira.class.getName(), new BandeiraDAO());
		daos.put(TipoEndereco.class.getName(), new TipoEnderecoDAO());
		daos.put(TipoTelefone.class.getName(), new TipoTelefoneDAO());
	}

	@Override
	public String salvar(EntidadeDominio entidade) {
		StringBuilder sb = executarRegras(entidade, SALVAR);
		if (sb.length() > 0) {
			return sb.toString();
		}
		IDAO dao = daos.get(entidade.getClass().getName());
		try {
			if(!dao.salvar(entidade)) {
				return "Erro ao persistir a entidade";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Problema na transação SQL";
		}
		return null;
		
	}

	@Override
	public String alterar(EntidadeDominio entidade) {
		IDAO dao = daos.get(entidade.getClass().getName());
		StringBuilder sb = executarRegras(entidade, ALTERAR);
		if (sb.length() > 0) {
			return sb.toString();
		}
		try {
			if(!dao.alterar(entidade)) {
				return "Problema na alteração";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Problema na transação SQL";
		}
		return null;
	}

	@Override
	public String excluir(EntidadeDominio entidade) {
		IDAO dao = daos.get(entidade.getClass().getName());
		try {
			if (!dao.excluir(entidade)) {
				return "Problema na exclusão";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Problema na transação SQL";
		}
		return "Exclusão efetuada com sucesso";
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		IDAO dao = daos.get(entidade.getClass().getName());
		List<EntidadeDominio> consulta;
		try {
			consulta = dao.consultar(entidade);
			if(!consulta.isEmpty()) {
				return consulta;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private StringBuilder executarRegras(EntidadeDominio entidade, String operacao) {
		StringBuilder sb = new StringBuilder();
		Map<String, List<IStrategy>> contextoEntidade = requisitos.get(entidade.getClass().getName());
		List<IStrategy> reqs = contextoEntidade.get(operacao);
		for (IStrategy req : reqs) {
			String msg = req.processar(entidade);
			if (msg != null) {
				sb.append(msg);
			}
		}
		return sb;
	}

}
