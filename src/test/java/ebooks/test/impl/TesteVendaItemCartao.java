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

public class TesteVendaItemCartao extends AbstractTest {
	@Before
	public void iniciar() {
		System.setProperty("webdriver.chrome.driver", "D:\\Users\\tallesaragao\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	//@Test
	public void testar() {
		driver.get(contexto + "loginSite");
		WebElement usuario = driver.findElement(By.name("usuario"));
		WebElement senha = driver.findElement(By.name("senha"));
		WebElement btnLogar = driver.findElement(By.id("btnLogar"));
		usuario.sendKeys("tobias");
		senha.sendKeys("Saibot00&");
		btnLogar.click();
		WebElement titulo = driver.findElement(By.name("titulo"));
		titulo.sendKeys("Memórias Póstumas de Brás Cubas");
		WebElement btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		btnPesquisar.click();
		boolean livroAdicionado = false;
		WebElement btnCarrinho2 = driver.findElement(By.id("btnCarrinho2"));
		btnCarrinho2.click();
		boolean freteCalculado = false;
		Select enderecos = new Select(driver.findElement(By.name("endereco")));
		enderecos.selectByIndex(2);
		WebElement btnCalcularFrete = driver.findElement(By.id("btnCalcularFrete"));
		btnCalcularFrete.click();
		String pageSource = driver.getPageSource();
		freteCalculado = pageSource.contains("Ir para pagamento");
		driver.get(contexto + "carrinhoPagamento");
		boolean cartaoSelecionado = false;
		Select cuponsTroca = new Select(driver.findElement(By.id("cartoesCredito")));
		cuponsTroca.selectByIndex(1);
		WebElement btnSelecionarCartoes = driver.findElement(By.id("btnSelecionarCartoes"));
		btnSelecionarCartoes.click();
		pageSource = driver.getPageSource();
		cartaoSelecionado = pageSource.contains("Digite o valor a ser pago nesse cartão");
		boolean informado = false;
		WebElement valorCartao = driver.findElement(By.name("valorCartao5"));
		valorCartao.clear();
		valorCartao.sendKeys("47");
		informado = valorCartao.getAttribute("value").equals("47");
		boolean confirmada = false;
		WebElement btnValidarFormaPagamento = driver.findElement(By.id("btnValidarFormaPagamento"));
		btnValidarFormaPagamento.click();
		WebElement btnPedidoConfirmarCompra = driver.findElement(By.id("btnPedidoConfirmarCompra"));
		btnPedidoConfirmarCompra.click();
		pageSource = driver.getPageSource();
		confirmada = pageSource.contains("Compra confirmada com sucesso");
		assertTrue(confirmada);
	}
	
	@After
	public void finalizar() {
		driver.close();
	}
}
