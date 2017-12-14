package ebooks.test.impl;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import ebooks.test.AbstractTest;

public class TesteVendaItensDescontoCartoesCupons extends AbstractTest {
	
	@Before
	public void iniciar() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Talles\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	//@Test
	public void testar() {
		driver.get(contexto + "loginSite");
		WebElement usuario = driver.findElement(By.name("usuario"));
		WebElement senha = driver.findElement(By.name("senha"));
		WebElement btnLogar = driver.findElement(By.id("btnLogar"));
		usuario.sendKeys("snvalmeida");
		senha.sendKeys("Guu2z3C0aY!");
		aguardar();
		btnLogar.click();
		
		WebElement btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		aguardar();
		btnPesquisar.click();
		
		WebElement btnCarrinho2 = driver.findElement(By.id("btnCarrinho2"));
		aguardar();
		btnCarrinho2.click();
		
		WebElement quantidade2 = driver.findElement(By.name("quantidade2"));
		quantidade2.clear();
		quantidade2.sendKeys("3");
		WebElement btnCarrinhoAlterar2 = driver.findElement(By.id("btnCarrinhoAlterar2"));
		aguardar();
		btnCarrinhoAlterar2.click();
		
		WebElement txtQuantidadeAlterada2 = driver.findElement(By.name("quantidade2"));
		String valor = txtQuantidadeAlterada2.getAttribute("value");
		WebElement btnContinuarComprando = driver.findElement(By.id("btnContinuarComprando"));
		aguardar();
		btnContinuarComprando.click();
		btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		aguardar();
		btnPesquisar.click();
		
		WebElement btnCarrinho4 = driver.findElement(By.id("btnCarrinho4"));
		aguardar();
		btnCarrinho4.click();
		
		WebElement quantidade4 = driver.findElement(By.name("quantidade4"));
		quantidade4.clear();
		quantidade4.sendKeys("5");
		WebElement btnCarrinhoAlterar4 = driver.findElement(By.id("btnCarrinhoAlterar4"));
		aguardar();
		btnCarrinhoAlterar4.click();
		WebElement txtQuantidadeAlterada4 = driver.findElement(By.name("quantidade4"));
		btnContinuarComprando = driver.findElement(By.id("btnContinuarComprando"));
		btnContinuarComprando.click();
		btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		aguardar();
		btnPesquisar.click();
		
		WebElement btnCarrinho6 = driver.findElement(By.id("btnCarrinho6"));
		btnCarrinho6.click();		
		WebElement quantidade6 = driver.findElement(By.name("quantidade6"));
		quantidade6.clear();
		quantidade6.sendKeys("4");
		WebElement btnCarrinhoAlterar6 = driver.findElement(By.id("btnCarrinhoAlterar6"));
		aguardar();
		btnCarrinhoAlterar6.click();
		WebElement txtQuantidadeAlterada6 = driver.findElement(By.name("quantidade6"));	
		
		boolean freteCalculado = false;
		Select enderecos = new Select(driver.findElement(By.name("endereco")));
		enderecos.selectByIndex(1);
		WebElement btnCalcularFrete = driver.findElement(By.id("btnCalcularFrete"));
		aguardar();
		btnCalcularFrete.click();
		String pageSource = driver.getPageSource();
		driver.get(contexto + "carrinhoPagamento");
		
		WebElement codigoPromocional = driver.findElement(By.name("codigoPromocional"));
		codigoPromocional.clear();
		codigoPromocional.sendKeys("5OFF");
		WebElement btnAdicionarCupom = driver.findElement(By.id("btnAdicionarCupom"));
		aguardar();
		btnAdicionarCupom.click();		

		driver.get(contexto + "carrinhoPagamento");
		boolean cupomSelecionado = false;
		Select cuponsTroca = new Select(driver.findElement(By.id("cuponsTroca")));
		cuponsTroca.selectByIndex(1);
		cuponsTroca.selectByIndex(2);
		WebElement btnSelecionarCupons = driver.findElement(By.id("btnSelecionarCupons"));
		aguardar();
		btnSelecionarCupons.click();
		pageSource = driver.getPageSource();
		cupomSelecionado = pageSource.contains("Valor disponível");
		
		boolean cartaoSelecionado = false;
		Select cartoes = new Select(driver.findElement(By.id("cartoesCredito")));
		cartoes.selectByIndex(1);
		cartoes.selectByIndex(2);
		WebElement btnSelecionarCartoes = driver.findElement(By.id("btnSelecionarCartoes"));
		aguardar();
		btnSelecionarCartoes.click();

		boolean informado = false;
		WebElement valorCartao6 = driver.findElement(By.name("valorCartao6"));
		valorCartao6.clear();
		valorCartao6.sendKeys("19,40");
		WebElement valorCartao8 = driver.findElement(By.name("valorCartao8"));
		valorCartao8.clear();
		valorCartao8.sendKeys("19");
		informado = valorCartao8.getAttribute("value").equals("165.88");
		aguardar();
		

		boolean confirmada = false;
		WebElement btnValidarFormaPagamento = driver.findElement(By.id("btnValidarFormaPagamento"));
		aguardar();
		btnValidarFormaPagamento.click();
		WebElement btnPedidoConfirmarCompra = driver.findElement(By.id("btnPedidoConfirmarCompra"));
		aguardar();
		btnPedidoConfirmarCompra.click();
		pageSource = driver.getPageSource();
		confirmada = pageSource.contains("Compra confirmada com sucesso");
		aguardar();
		assertTrue(confirmada);
	}
	
	@After
	public void finalizar() {
		driver.close();
	}
	
	public void aguardar() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
