package ebooks.controle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ebooks.aplicacao.Resultado;
import ebooks.dao.BandeiraDAO;
import ebooks.dao.CartaoCreditoDAO;
import ebooks.dao.CategoriaDAO;
import ebooks.dao.ClienteDAO;
import ebooks.dao.EnderecoDAO;
import ebooks.dao.GrupoPrecificacaoDAO;
import ebooks.dao.IDAO;
import ebooks.dao.LivroDAO;
import ebooks.dao.LoginDAO;
import ebooks.dao.PedidoDAO;
import ebooks.dao.StatusPedidoDAO;
import ebooks.dao.StatusTrocaDAO;
import ebooks.dao.TipoEnderecoDAO;
import ebooks.dao.TipoTelefoneDAO;
import ebooks.dao.TrocaDAO;
import ebooks.modelo.Acesso;
import ebooks.modelo.Analise;
import ebooks.modelo.Bandeira;
import ebooks.modelo.Carrinho;
import ebooks.modelo.CartaoCredito;
import ebooks.modelo.Categoria;
import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.EntidadeDominio;
import ebooks.modelo.GrupoPrecificacao;
import ebooks.modelo.Livro;
import ebooks.modelo.Login;
import ebooks.modelo.Pedido;
import ebooks.modelo.StatusPedido;
import ebooks.modelo.StatusTroca;
import ebooks.modelo.TipoEndereco;
import ebooks.modelo.TipoTelefone;
import ebooks.modelo.Troca;
import ebooks.negocio.IStrategy;
import ebooks.negocio.impl.AdicionarCartoesPagamento;
import ebooks.negocio.impl.AdicionarCupomPromocionalPagamento;
import ebooks.negocio.impl.AdicionarCuponsTrocaPagamento;
import ebooks.negocio.impl.AdicionarLivroCarrinho;
import ebooks.negocio.impl.AlterarQuantidadeItemCarrinho;
import ebooks.negocio.impl.AlterarStatusAtualPedido;
import ebooks.negocio.impl.AlterarStatusAtualTroca;
import ebooks.negocio.impl.AlterarStatusCompraEmTroca;
import ebooks.negocio.impl.AtivadorClientePrimeiroCadastro;
import ebooks.negocio.impl.AtivadorLivroPrimeiroCadastro;
import ebooks.negocio.impl.CalcularFrete;
import ebooks.negocio.impl.CalcularValorTotalPedido;
import ebooks.negocio.impl.ComplementarDtCadastro;
import ebooks.negocio.impl.ConsultarClienteCarrinho;
import ebooks.negocio.impl.ExcluirCupomPagamento;
import ebooks.negocio.impl.ExcluirLivroCarrinho;
import ebooks.negocio.impl.GeradorCodigoLivro;
import ebooks.negocio.impl.GeradorPrecoLivro;
import ebooks.negocio.impl.GerarGraficoAnalise;
import ebooks.negocio.impl.RemoverCartaoCreditoCarrinho;
import ebooks.negocio.impl.RemoverValeComprasCarrinho;
import ebooks.negocio.impl.ValidarCamposCartaoCredito;
import ebooks.negocio.impl.ValidarCamposCategoria;
import ebooks.negocio.impl.ValidarCamposCliente;
import ebooks.negocio.impl.ValidarCamposEndereco;
import ebooks.negocio.impl.ValidarCamposLivro;
import ebooks.negocio.impl.ValidarCamposLogin;
import ebooks.negocio.impl.ValidarConsistenciaFrete;
import ebooks.negocio.impl.ValidarFormaPagamento;
import ebooks.negocio.impl.ValidarTroca;
import ebooks.negocio.impl.VerificarAcesso;
import ebooks.negocio.impl.VerificarDisponibilidadeLivros;
import ebooks.negocio.impl.VerificarExistenciaCliente;
import ebooks.negocio.impl.VerificarPedidoFinalizado;

public class Fachada implements IFachada {

	private static final String SALVAR = "SALVAR";
	private static final String ALTERAR = "ALTERAR";
	private static final String CONSULTAR = "CONSULTAR";
	private static final String EXCLUIR = "EXCLUIR";

	private Map<String, Map<String, List<IStrategy>>> requisitos;
	private Map<String, Map<String, List<IStrategy>>> requisitosAfter;
	private Map<String, IDAO> daos;
	
	private Resultado resultado;

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
		AdicionarLivroCarrinho adcLivCar = new AdicionarLivroCarrinho();
		VerificarPedidoFinalizado verPedFin = new VerificarPedidoFinalizado();
		AlterarQuantidadeItemCarrinho altQuantItemCar = new AlterarQuantidadeItemCarrinho();
		ExcluirLivroCarrinho excLivCar = new ExcluirLivroCarrinho();
		ConsultarClienteCarrinho conCliCar = new ConsultarClienteCarrinho();
		CalcularFrete calcFrete = new CalcularFrete();
		ValidarConsistenciaFrete valConsistFrete = new ValidarConsistenciaFrete();
		CalcularValorTotalPedido calcValTotalPedido = new CalcularValorTotalPedido();
		AdicionarCartoesPagamento adicCartPag = new AdicionarCartoesPagamento();
		AdicionarCupomPromocionalPagamento adicCupomPromoPag = new AdicionarCupomPromocionalPagamento();
		ExcluirCupomPagamento excluirCupomPag = new ExcluirCupomPagamento();
		AdicionarCuponsTrocaPagamento adicCupomTrocaPag = new AdicionarCuponsTrocaPagamento();
		RemoverValeComprasCarrinho remValeCarrinho = new RemoverValeComprasCarrinho();
		RemoverCartaoCreditoCarrinho remCarCredCarrinho = new RemoverCartaoCreditoCarrinho();
		ValidarFormaPagamento valFormaPag = new ValidarFormaPagamento();
		VerificarDisponibilidadeLivros verDispLivros = new VerificarDisponibilidadeLivros();
		AlterarStatusAtualPedido altStatusAtualPed = new AlterarStatusAtualPedido();
		VerificarAcesso verAcesso = new VerificarAcesso();
		ValidarTroca valTroca = new ValidarTroca();
		AlterarStatusCompraEmTroca altStatusCompTroca = new AlterarStatusCompraEmTroca();
		AlterarStatusAtualTroca altStatusAtualTroc = new AlterarStatusAtualTroca();
		GerarGraficoAnalise gerarGraficoAnalise = new GerarGraficoAnalise();

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
		
		Map<String, List<IStrategy>> contextoCarrinho = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lCarrinhoSalvar = new ArrayList<>();
		lCarrinhoSalvar.add(adcLivCar);
		lCarrinhoSalvar.add(adicCartPag);
		lCarrinhoSalvar.add(adicCupomPromoPag);
		lCarrinhoSalvar.add(adicCupomTrocaPag);
		//lCarrinhoSalvar.add(adicValeCompPag);
		lCarrinhoSalvar.add(valFormaPag);
		lCarrinhoSalvar.add(verDispLivros);
		lCarrinhoSalvar.add(verPedFin);
		List<IStrategy> lCarrinhoAlterar = new ArrayList<>();
		lCarrinhoAlterar.add(altQuantItemCar);
		lCarrinhoAlterar.add(verPedFin);
		List<IStrategy> lCarrinhoExcluir = new ArrayList<>();
		lCarrinhoExcluir.add(excLivCar);
		lCarrinhoExcluir.add(excluirCupomPag);
		lCarrinhoExcluir.add(remValeCarrinho);
		lCarrinhoExcluir.add(remCarCredCarrinho);
		lCarrinhoExcluir.add(verPedFin);
		List<IStrategy> lCarrinhoConsultar = new ArrayList<>();
		lCarrinhoConsultar.add(conCliCar);
		lCarrinhoConsultar.add(calcFrete);
		lCarrinhoConsultar.add(valConsistFrete);
		lCarrinhoConsultar.add(calcValTotalPedido);
		lCarrinhoConsultar.add(verPedFin);
		contextoCarrinho.put(SALVAR, lCarrinhoSalvar);
		contextoCarrinho.put(ALTERAR, lCarrinhoAlterar);
		contextoCarrinho.put(EXCLUIR, lCarrinhoExcluir);
		contextoCarrinho.put(CONSULTAR, lCarrinhoConsultar);
		
		Map<String, List<IStrategy>> contextoStatusPedido = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lStatusPedidoSalvar = new ArrayList<>();
		lStatusPedidoSalvar.add(altStatusAtualPed);
		List<IStrategy> lStatusPedidoAlterar = new ArrayList<>();
		List<IStrategy> lStatusPedidoExcluir = new ArrayList<>();
		List<IStrategy> lStatusPedidoConsultar = new ArrayList<>();
		contextoStatusPedido.put(SALVAR, lStatusPedidoSalvar);
		contextoStatusPedido.put(ALTERAR, lStatusPedidoAlterar);
		contextoStatusPedido.put(EXCLUIR, lStatusPedidoExcluir);
		contextoStatusPedido.put(CONSULTAR, lStatusPedidoConsultar);
		
		Map<String, List<IStrategy>> contextoStatusTroca = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lStatusTrocaSalvar = new ArrayList<>();
		lStatusTrocaSalvar.add(altStatusAtualTroc);
		lStatusTrocaSalvar.add(compDtCad);
		List<IStrategy> lStatusTrocaAlterar = new ArrayList<>();
		List<IStrategy> lStatusTrocaExcluir = new ArrayList<>();
		List<IStrategy> lStatusTrocaConsultar = new ArrayList<>();
		contextoStatusTroca.put(SALVAR, lStatusTrocaSalvar);
		contextoStatusTroca.put(ALTERAR, lStatusTrocaAlterar);
		contextoStatusTroca.put(EXCLUIR, lStatusTrocaExcluir);
		contextoStatusTroca.put(CONSULTAR, lStatusTrocaConsultar);
		
		Map<String, List<IStrategy>> contextoTroca = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lTrocaSalvar = new ArrayList<>();
		lTrocaSalvar.add(valTroca);
		lTrocaSalvar.add(compDtCad);
		List<IStrategy> lTrocaAlterar = new ArrayList<>();
		List<IStrategy> lTrocaExcluir = new ArrayList<>();
		List<IStrategy> lTrocaConsultar = new ArrayList<>();
		contextoTroca.put(SALVAR, lTrocaSalvar);
		contextoTroca.put(ALTERAR, lTrocaAlterar);
		contextoTroca.put(EXCLUIR, lTrocaExcluir);
		contextoTroca.put(CONSULTAR, lTrocaConsultar);
		
		Map<String, List<IStrategy>> contextoAcesso = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lAcessoConsultar = new ArrayList<>();
		lAcessoConsultar.add(verAcesso);
		contextoAcesso.put(CONSULTAR, lAcessoConsultar);

		Map<String, List<IStrategy>> contextoAnalise = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lAnaliseConsultar = new ArrayList<>();
		lAnaliseConsultar.add(gerarGraficoAnalise);
		contextoAnalise.put(CONSULTAR, lAnaliseConsultar);
		
		Map<String, List<IStrategy>> contextoGrupoPrecificacao = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lGrupoPrecificacaoConsultar = new ArrayList<>();
		contextoGrupoPrecificacao.put(CONSULTAR, lGrupoPrecificacaoConsultar);
		
		Map<String, List<IStrategy>> contextoBandeira = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lBandeiraConsultar = new ArrayList<>();
		contextoBandeira.put(CONSULTAR, lBandeiraConsultar);
		
		Map<String, List<IStrategy>> contextoTipoEnd = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lTipoEndConsultar = new ArrayList<>();
		contextoTipoEnd.put(CONSULTAR, lTipoEndConsultar);
		
		Map<String, List<IStrategy>> contextoTipoTel = new HashMap<String, List<IStrategy>>();
		List<IStrategy> lTipoTelConsultar = new ArrayList<>();
		contextoTipoTel.put(CONSULTAR, lTipoTelConsultar);
		
		requisitos = new HashMap<String, Map<String, List<IStrategy>>>();
		requisitos.put(Categoria.class.getName(), contextoCat);
		requisitos.put(Livro.class.getName(), contextoLivro);
		requisitos.put(GrupoPrecificacao.class.getName(), contextoGrupoPrecificacao);
		requisitos.put(Login.class.getName(), contextoLogin);
		requisitos.put(Cliente.class.getName(), contextoCliente);
		requisitos.put(Endereco.class.getName(), contextoEndereco);
		requisitos.put(CartaoCredito.class.getName(), contextoCarCred);
		requisitos.put(Bandeira.class.getName(), contextoBandeira);
		requisitos.put(TipoEndereco.class.getName(), contextoTipoEnd);
		requisitos.put(TipoTelefone.class.getName(), contextoTipoTel);
		requisitos.put(Carrinho.class.getName(), contextoCarrinho);
		requisitos.put(StatusPedido.class.getName(), contextoStatusPedido);
		requisitos.put(Acesso.class.getName(), contextoAcesso);
		requisitos.put(Troca.class.getName(), contextoTroca);
		requisitos.put(StatusTroca.class.getName(), contextoStatusTroca);
		requisitos.put(Analise.class.getName(), contextoAnalise);

		Map<String, List<IStrategy>> contextoCarrinhoAfter = new HashMap<>();
		List<IStrategy> lCarrinhoAfterSalvar = new ArrayList<>();
		List<IStrategy> lCarrinhoAfterAlterar = new ArrayList<>();
		List<IStrategy> lCarrinhoAfterExcluir = new ArrayList<>();
		List<IStrategy> lCarrinhoAfterConsultar = new ArrayList<>();
		contextoCarrinhoAfter.put(SALVAR, lCarrinhoAfterSalvar);
		contextoCarrinhoAfter.put(ALTERAR, lCarrinhoAfterAlterar);
		contextoCarrinhoAfter.put(EXCLUIR, lCarrinhoAfterExcluir);
		contextoCarrinhoAfter.put(CONSULTAR, lCarrinhoAfterConsultar);

		Map<String, List<IStrategy>> contextoTrocaAfter = new HashMap<>();
		List<IStrategy> lTrocaAfterSalvar = new ArrayList<>();
		lTrocaAfterSalvar.add(altStatusCompTroca);
		List<IStrategy> lTrocaAfterAlterar = new ArrayList<>();
		List<IStrategy> lTrocaAfterExcluir = new ArrayList<>();
		List<IStrategy> lTrocaAfterConsultar = new ArrayList<>();
		contextoTrocaAfter.put(SALVAR, lTrocaAfterSalvar);
		contextoTrocaAfter.put(ALTERAR, lTrocaAfterAlterar);
		contextoTrocaAfter.put(EXCLUIR, lTrocaAfterExcluir);
		contextoTrocaAfter.put(CONSULTAR, lTrocaAfterConsultar);
		
		requisitosAfter = new HashMap<String, Map<String, List<IStrategy>>>();
		requisitosAfter.put(Carrinho.class.getName(), contextoCarrinhoAfter);
		requisitosAfter.put(Troca.class.getName(), contextoTrocaAfter);
		
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
		daos.put(Carrinho.class.getName(), new PedidoDAO());
		daos.put(Pedido.class.getName(), new PedidoDAO());
		daos.put(StatusPedido.class.getName(), new StatusPedidoDAO());
		daos.put(Troca.class.getName(), new TrocaDAO());
		daos.put(StatusTroca.class.getName(), new StatusTrocaDAO());
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		StringBuilder sb = executarRegras(entidade, SALVAR, requisitos);
		Resultado resultado = new Resultado();
		ArrayList<EntidadeDominio> consultaEntidade = new ArrayList<EntidadeDominio>();
		consultaEntidade.add(entidade);
		resultado.setEntidades(consultaEntidade);
		if (sb.length() > 0) {
			resultado.setResposta(sb.toString());
			return resultado;
		}
		IDAO dao = daos.get(entidade.getClass().getName());
		if(dao != null) {
			try {
				if(!dao.salvar(entidade)) {
					resultado.setResposta("Erro ao persistir a entidade");
					return resultado;
				}
				else {
					sb = executarRegras(entidade, SALVAR, requisitosAfter);
					if(sb.length() > 0) {
						resultado.setResposta(sb.toString());
						return resultado;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setResposta("Problema na transação SQL");
				return resultado;
			}
		}
		return resultado;
		
	}

	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		StringBuilder sb = executarRegras(entidade, ALTERAR, requisitos);
		Resultado resultado = new Resultado();
		ArrayList<EntidadeDominio> consultaEntidade = new ArrayList<EntidadeDominio>();
		consultaEntidade.add(entidade);
		resultado.setEntidades(consultaEntidade);
		if (sb.length() > 0) {
			resultado.setResposta(sb.toString());
			return resultado;
		}
		IDAO dao = daos.get(entidade.getClass().getName());
		if(dao != null) {
			try {
				if(!dao.alterar(entidade)) {
					resultado.setResposta("Problema na alteração");
					return resultado;
				}
				else {
					sb = executarRegras(entidade, ALTERAR, requisitosAfter);
					if(sb.length() > 0) {
						resultado.setResposta(sb.toString());
						return resultado;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setResposta("Problema na transação SQL");
				return resultado;
			}
		}
		return resultado;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		StringBuilder sb = executarRegras(entidade, EXCLUIR, requisitos);
		Resultado resultado = new Resultado();
		ArrayList<EntidadeDominio> consultaEntidade = new ArrayList<EntidadeDominio>();
		consultaEntidade.add(entidade);
		resultado.setEntidades(consultaEntidade);
		if (sb.length() > 0) {
			resultado.setResposta(sb.toString());
			return resultado;
		}
		IDAO dao = daos.get(entidade.getClass().getName());
		if(dao != null) {
			try {
				if (!dao.excluir(entidade)) {
					resultado.setResposta("Problema na exclusão:");
					return resultado;
				}
				else {
					sb = executarRegras(entidade, EXCLUIR, requisitosAfter);
					if(sb.length() > 0) {
						resultado.setResposta(sb.toString());
						return resultado;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				resultado.setResposta("Problema na transação SQL");
				return resultado;
			}
			resultado.setResposta("Exclusão efetuada com sucesso");
			return resultado;
		}
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		StringBuilder sb = executarRegras(entidade, CONSULTAR, requisitos);
		Resultado resultado = new Resultado();
		ArrayList<EntidadeDominio> consultaEntidade = new ArrayList<EntidadeDominio>();
		consultaEntidade.add(entidade);
		resultado.setEntidades(consultaEntidade);
		if (sb.length() > 0) {
			resultado.setResposta(sb.toString());
			return resultado;
		}
		IDAO dao = daos.get(entidade.getClass().getName());
		if(dao != null) {
			List<EntidadeDominio> consulta;
			try {
				consulta = dao.consultar(entidade);
				if(!consulta.isEmpty()) {
					resultado.setEntidades(consulta);
					return resultado;
				}
				else {
					sb = executarRegras(entidade, EXCLUIR, requisitosAfter);
					if(sb.length() > 0) {
						resultado.setEntidades(null);
						resultado.setResposta(sb.toString());
						return resultado;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	private StringBuilder executarRegras(EntidadeDominio entidade, String operacao, Map<String, Map<String, List<IStrategy>>> requisitos) {
		StringBuilder sb = new StringBuilder();
		Map<String, List<IStrategy>> contextoEntidade = requisitos.get(entidade.getClass().getName());
		if(contextoEntidade != null) {
			List<IStrategy> reqs = contextoEntidade.get(operacao);
			if(reqs != null) {
				for (IStrategy req : reqs) {
					String msg = req.processar(entidade);
					if (msg != null) {
						sb.append(msg);
					}
				}
			}
		}
		return sb;
	}
}
