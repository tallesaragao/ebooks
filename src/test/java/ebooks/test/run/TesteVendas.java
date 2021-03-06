package ebooks.test.run;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TesteVendas {
	private WebDriver driver;
	private String contexto = "http://localhost:8080/ebooks/";
	
	@Before
	public void iniciar() {
		System.setProperty("webdriver.chrome.driver", "D:\\Users\\tallesaragao\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
		//driver.manage().window().maximize();
	}
	
	@After
	public void finalizar() {
		driver.close();
	}
	
	//Execução dos testes
	//@Test
	public void deveLogarNoSite() {
		boolean sucesso = fazerLoginSite();
		assertTrue(sucesso);
	}
	
	//@Test
	public void devePesquisarLivro() {
		boolean sucesso = pesquisarLivro();
		assertTrue(sucesso);		
	}
	
	//@Test
	public void deveAdicionarLivroCarrinho() {
		fazerLoginSite();
		pesquisarLivro();
		boolean sucesso = adicionarLivroCarrinho();
		fazerLogoutSite();
		assertTrue(sucesso);		
	}
	
	//@Test
	public void deveAlterarQuantidadeLivroCarrinho() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		boolean sucesso = alterarQuantidadeCarrinho();
		fazerLogoutSite();
		assertTrue(sucesso);		
	}
	
	//@Test
	public void deveRemoverLivroCarrinho() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		boolean sucesso = removerLivroCarrinho();
		fazerLogoutSite();
		assertTrue(sucesso);	
	}
	
	//@Test
	public void deveCalcularFrete() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		boolean sucesso = calcularFrete();
		fazerLogoutSite();
		assertTrue(sucesso);	
	}
	
	//@Test
	public void deveAdicionarCupomPromocional() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		calcularFrete();
		boolean sucesso = aplicarCupomDesconto();
		fazerLogoutSite();
		assertTrue(sucesso);	
	}
	
	//@Test
	public void deveRemoverCupomPromocional() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		calcularFrete();
		aplicarCupomDesconto();
		boolean sucesso = removerCupomDesconto();
		fazerLogoutSite();
		assertTrue(sucesso);	
	}

	//@Test
	public void deveSelecionarCupomTroca() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		calcularFrete();
		boolean sucesso = selecionarCupomTroca();
		fazerLogoutSite();
		assertTrue(sucesso);	
	}

	//@Test
	public void deveRemoverCupomTroca() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		calcularFrete();
		selecionarCupomTroca();
		boolean sucesso = removerCupomTroca();
		fazerLogoutSite();
		assertTrue(sucesso);	
	}
	
	//@Test
	public void deveSelecionarCartaoCredito() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		calcularFrete();
		boolean sucesso = selecionarCartaoCredito();
		fazerLogoutSite();
		assertTrue(sucesso);	
	}

	//@Test
	public void deveRemoverCartaoCredito() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		calcularFrete();
		selecionarCartaoCredito();
		boolean sucesso = removerCartaoCredito();
		fazerLogoutSite();
		assertTrue(sucesso);	
	}
	
	//@Test
	public void deveFazerUmPedido() {
		fazerLoginSite();
		pesquisarLivro();
		adicionarLivroCarrinho();
		alterarQuantidadeCarrinho();
		calcularFrete();
		selecionarCupomTroca();
		selecionarCartaoCredito();
		informarValorPagamentoCredito();
		boolean sucesso = fazerPedido();
		assertTrue(sucesso);
	}
	
	//@Test
	public void deveMostrarPedidos() {
		fazerLoginAdministrador();
		boolean sucesso = mostrarPedidos();
		assertTrue(sucesso);
	}
	
	//@Test
	public void deveMostrarDetalhesPedido() {
		fazerLoginAdministrador();
		mostrarPedidos();
		boolean sucesso = mostrarDetalhesPedido();
		assertTrue(sucesso);
	}
	
	//@Test
	public void deveAprovarUmPedido() {
		fazerLoginAdministrador();
		mostrarPedidos();
		mostrarDetalhesPedido();
		boolean sucesso = aprovarPedido();
		assertTrue(sucesso);
	}
	
	//Divisão das funções
	public boolean fazerLoginSite() {
		driver.get(contexto + "loginSite");
		WebElement usuario = driver.findElement(By.name("usuario"));
		WebElement senha = driver.findElement(By.name("senha"));
		WebElement btnLogar = driver.findElement(By.id("btnLogar"));
		usuario.sendKeys("tobias");
		senha.sendKeys("Saibot00&");
		aguardar();
		btnLogar.click();
		String pageSource = driver.getPageSource();
		boolean logado = pageSource.contains("Olá, tobias");
		return logado;
	}
	
	public boolean fazerLoginAdministrador() {
		driver.get(contexto + "loginSite");
		WebElement usuario = driver.findElement(By.name("usuario"));
		WebElement senha = driver.findElement(By.name("senha"));
		WebElement btnLogar = driver.findElement(By.id("btnLogar"));
		usuario.sendKeys("admin");
		senha.sendKeys("Admin12#");
		aguardar();
		btnLogar.click();
		String pageSource = driver.getPageSource();
		boolean logado = pageSource.contains("Olá, admin");
		return logado;
	}
	
	public boolean fazerLogoutSite() {
		WebElement btnLoginDrop = driver.findElement(By.id("idLoginDrop"));
		btnLoginDrop.click();
		WebElement btnLogout = driver.findElement(By.id("logoutSite"));
		aguardar();
		btnLogout.click();
		String pageSource = driver.getPageSource();
		boolean deslogado = pageSource.contains("Login de cliente");
		return deslogado;
	}
	
	public boolean pesquisarLivro() {
		driver.get(contexto + "livroList");
		WebElement titulo = driver.findElement(By.name("titulo"));
		titulo.sendKeys("Memórias Póstumas de Brás Cubas");
		WebElement btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		aguardar();
		btnPesquisar.click();
		String pageSource = driver.getPageSource();
		aguardar();
		boolean livroEncontrado = pageSource.contains("Memórias Póstumas de Brás Cubas");
		return livroEncontrado;
	}
	
	public boolean adicionarLivroCarrinho() {
		boolean livroAdicionado = false;
		WebElement btnCarrinho2 = driver.findElement(By.id("btnCarrinho2"));
		aguardar();
		btnCarrinho2.click();
		String pageSource = driver.getPageSource();
		boolean carrinhoMostrado = pageSource.contains("Carrinho de compras");
		livroAdicionado = pageSource.contains("Memórias Póstumas de Brás Cubas");
		return carrinhoMostrado && livroAdicionado;
	}
	
	public boolean alterarQuantidadeCarrinho() {
		boolean quantidadeAlterada = false;
		WebElement quantidade2 = driver.findElement(By.name("quantidade2"));
		quantidade2.clear();
		quantidade2.sendKeys("10");
		WebElement btnCarrinhoAlterar = driver.findElement(By.id("btnCarrinhoAlterar"));
		btnCarrinhoAlterar.click();
		aguardar();
		WebElement txtQuantidadeAlterada = driver.findElement(By.name("quantidade2"));
		String valor = txtQuantidadeAlterada.getAttribute("value");
		if(valor != null && valor.equals("10")) {
			quantidadeAlterada = true;
		}
		return quantidadeAlterada;
	}
	
	public boolean removerLivroCarrinho() {
		boolean livroRemovido = false;
		WebElement btnRemoverCarrinho = driver.findElement(By.id("btnRemoverCarrinho"));
		btnRemoverCarrinho.click();
		aguardar();
		driver.switchTo().alert().accept();
		aguardar();
		String pageSource = driver.getPageSource();
		livroRemovido = pageSource.contains("O carrinho está vazio");
		return livroRemovido;
	}
	
	public boolean calcularFrete() {
		boolean freteCalculado = false;
		Select enderecos = new Select(driver.findElement(By.name("endereco")));
		enderecos.selectByIndex(2);
		WebElement btnCalcularFrete = driver.findElement(By.id("btnCalcularFrete"));
		btnCalcularFrete.click();
		aguardar();
		String pageSource = driver.getPageSource();
		freteCalculado = pageSource.contains("Ir para pagamento");
		return freteCalculado;
	}
	
	public boolean aplicarCupomDesconto() {
		driver.get(contexto + "carrinhoPagamento");
		boolean cupomAplicado = false;
		WebElement codigoPromocional = driver.findElement(By.name("codigoPromocional"));
		WebElement btnAdicionarCupom = driver.findElement(By.id("btnAdicionarCupom"));
		codigoPromocional.clear();
		codigoPromocional.sendKeys("5OFF");
		btnAdicionarCupom.click();
		aguardar();
		String pageSource = driver.getPageSource();
		cupomAplicado = pageSource.contains("5OFF - 5% de desconto");
		return cupomAplicado;
	}
	
	public boolean removerCupomDesconto() {
		driver.get(contexto + "carrinhoPagamento");
		boolean cupomRemovido = false;
		WebElement btnRemoverCupom = driver.findElement(By.id("btnRemoverCupom"));
		btnRemoverCupom.click();
		aguardar();
		String pageSource = driver.getPageSource();
		cupomRemovido = !pageSource.contains("Remover");
		return cupomRemovido;
	}
	
	public boolean selecionarCupomTroca() {
		driver.get(contexto + "carrinhoPagamento");
		boolean cupomSelecionado = false;
		Select cuponsTroca = new Select(driver.findElement(By.id("cuponsTroca")));
		cuponsTroca.selectByIndex(1);
		WebElement btnSelecionarCupons = driver.findElement(By.id("btnSelecionarCupons"));
		btnSelecionarCupons.click();
		aguardar();
		String pageSource = driver.getPageSource();
		cupomSelecionado = pageSource.contains("Valor disponível");
		return cupomSelecionado;		
	}
	
	public boolean removerCupomTroca() {
		driver.get(contexto + "carrinhoPagamento");
		boolean cupomRemovido = false;
		WebElement btnRemoverCupom = driver.findElement(By.id("btnValeComprasRemover"));
		btnRemoverCupom.click();
		aguardar();
		driver.switchTo().alert().accept();
		aguardar();
		String pageSource = driver.getPageSource();
		cupomRemovido = !pageSource.contains("Remover");
		return cupomRemovido;
	}
	
	public boolean selecionarCartaoCredito() {
		driver.get(contexto + "carrinhoPagamento");
		boolean cartaoSelecionado = false;
		Select cuponsTroca = new Select(driver.findElement(By.id("cartoesCredito")));
		cuponsTroca.selectByIndex(1);
		WebElement btnSelecionarCartoes = driver.findElement(By.id("btnSelecionarCartoes"));
		btnSelecionarCartoes.click();
		aguardar();
		String pageSource = driver.getPageSource();
		cartaoSelecionado = pageSource.contains("Digite o valor a ser pago nesse cartão");
		return cartaoSelecionado;		
	}
	
	public boolean informarValorPagamentoCredito() {
		boolean informado = false;
		WebElement valorCartao = driver.findElement(By.name("valorCartao5"));
		valorCartao.clear();
		valorCartao.sendKeys("165,88");
		informado = valorCartao.getAttribute("value").equals("165.88");
		aguardar();
		return informado;
	}
	
	public boolean removerCartaoCredito() {
		driver.get(contexto + "carrinhoPagamento");
		boolean cartaoRemovido = false;
		WebElement btnRemoverCartao = driver.findElement(By.id("btnCartaoCreditoRemover"));
		btnRemoverCartao.click();
		aguardar();
		driver.switchTo().alert().accept();
		aguardar();
		String pageSource = driver.getPageSource();
		cartaoRemovido = !pageSource.contains("Remover");
		return cartaoRemovido;
	}
	
	public boolean fazerPedido() {
		boolean confirmada = false;
		WebElement btnValidarFormaPagamento = driver.findElement(By.id("btnValidarFormaPagamento"));
		btnValidarFormaPagamento.click();
		aguardar();
		WebElement btnPedidoConfirmarCompra = driver.findElement(By.id("btnPedidoConfirmarCompra"));
		btnPedidoConfirmarCompra.click();
		aguardar();
		String pageSource = driver.getPageSource();
		confirmada = pageSource.contains("Compra confirmada com sucesso");
		return confirmada;
	}
	
	public boolean mostrarPedidos() {
		boolean listados = false;
		WebElement pedidoList = driver.findElement(By.id("pedidoList"));
		pedidoList.click();
		aguardar();
		WebElement btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		btnPesquisar.click();
		aguardar();
		String pageSource = driver.getPageSource();
		listados = pageSource.contains("Detalhes");
		aguardar();
		return listados;
	}
	
	public boolean mostrarDetalhesPedido() {
		boolean mostrado = false;
		WebElement btnDetalhesPedido = driver.findElement(By.id("btnDetalhesPedido"));
		btnDetalhesPedido.click();
		aguardar();
		String pageSource = driver.getPageSource();
		mostrado = pageSource.contains("Acompanhamento do pedido");
		return mostrado;
	}
	
	public boolean aprovarPedido() {
		boolean aprovado = false;
		WebElement aprovarPedido = driver.findElement(By.id("aprovarPedido"));
		aprovarPedido.click();
		aguardar();
		String pageSource = driver.getPageSource();
		aprovado = pageSource.contains("Aprovado");
		return aprovado;
	}
	
	public void mostrarTrocas() {
		WebElement trocaList = driver.findElement(By.id("trocaList"));
		trocaList.click();
		WebElement btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		btnPesquisar.click();
	}
	
	public void aguardar() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
