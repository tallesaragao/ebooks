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
		System.setProperty("webdriver.chrome.driver", "D:\\Users\\tallesaragao\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test
	public void testar() {
		driver.get(contexto + "loginSite");
		WebElement usuario = driver.findElement(By.name("usuario"));
		WebElement senha = driver.findElement(By.name("senha"));
		WebElement btnLogar = driver.findElement(By.id("btnLogar"));
		usuario.sendKeys("snvalmeida");
		senha.sendKeys("Guu2z3C0aY!");
		btnLogar.click();
		
		WebElement btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		btnPesquisar.click();
		
		WebElement btnCarrinho2 = driver.findElement(By.id("btnCarrinho2"));
		btnCarrinho2.click();
		
		WebElement quantidade2 = driver.findElement(By.name("quantidade2"));
		quantidade2.clear();
		quantidade2.sendKeys("3");
		WebElement btnCarrinhoAlterar2 = driver.findElement(By.id("btnCarrinhoAlterar2"));
		btnCarrinhoAlterar2.click();
		
		WebElement txtQuantidadeAlterada2 = driver.findElement(By.name("quantidade2"));
		String valor = txtQuantidadeAlterada2.getAttribute("value");
		WebElement btnContinuarComprando = driver.findElement(By.id("btnContinuarComprando"));
		btnContinuarComprando.click();
		btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		btnPesquisar.click();
		
		WebElement btnCarrinho4 = driver.findElement(By.id("btnCarrinho4"));
		btnCarrinho4.click();
		
		WebElement quantidade4 = driver.findElement(By.name("quantidade4"));
		quantidade4.clear();
		quantidade4.sendKeys("5");
		WebElement btnCarrinhoAlterar4 = driver.findElement(By.id("btnCarrinhoAlterar4"));
		btnCarrinhoAlterar4.click();
		WebElement txtQuantidadeAlterada4 = driver.findElement(By.name("quantidade4"));
		btnContinuarComprando = driver.findElement(By.id("btnContinuarComprando"));
		btnContinuarComprando.click();
		btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		btnPesquisar.click();
		
		WebElement btnCarrinho6 = driver.findElement(By.id("btnCarrinho6"));
		btnCarrinho6.click();		
		WebElement quantidade6 = driver.findElement(By.name("quantidade6"));
		quantidade6.clear();
		quantidade6.sendKeys("4");
		WebElement btnCarrinhoAlterar6 = driver.findElement(By.id("btnCarrinhoAlterar6"));
		btnCarrinhoAlterar6.click();
		WebElement txtQuantidadeAlterada6 = driver.findElement(By.name("quantidade6"));	
		
		boolean freteCalculado = false;
		Select enderecos = new Select(driver.findElement(By.name("endereco")));
		enderecos.selectByIndex(1);
		WebElement btnCalcularFrete = driver.findElement(By.id("btnCalcularFrete"));
		btnCalcularFrete.click();
		String pageSource = driver.getPageSource();
		
		driver.get(contexto + "carrinhoPagamento");
		boolean cartaoSelecionado = false;
		Select cuponsTroca = new Select(driver.findElement(By.id("cartoesCredito")));
		cuponsTroca.selectByIndex(1);
		cuponsTroca.selectByIndex(2);
		WebElement btnSelecionarCartoes = driver.findElement(By.id("btnSelecionarCartoes"));
		btnSelecionarCartoes.click();
		
		WebElement btnLoginDrop = driver.findElement(By.id("idLoginDrop"));
		btnLoginDrop.click();
		WebElement btnLogout = driver.findElement(By.id("logoutSite"));
		btnLogout.click();
		pageSource = driver.getPageSource();
		boolean deslogado = pageSource.contains("Login de cliente");
		
		assertTrue(deslogado);
	}
	
	@After
	public void finalizar() {
		driver.close();
	}
}
