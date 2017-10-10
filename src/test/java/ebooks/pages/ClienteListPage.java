package ebooks.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ebooks.modelo.Cliente;

public class ClienteListPage {
	private WebDriver driver;
	
	public ClienteListPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void visitar() {
		driver.get("http://localhost:8080/ebooks/clienteList");
	}
	
	public ClienteFormPage novo() {
		driver.findElement(By.id("btnNovoCliente")).click();
		return new ClienteFormPage(driver);
	}
	
	public boolean pesquisar(Cliente cliente) {
		WebElement nome = driver.findElement(By.name("nome"));
		WebElement email = driver.findElement(By.name("email"));
		WebElement cpf = driver.findElement(By.name("cpf"));
		WebElement genero = driver.findElement(By.name("genero"));
		WebElement btnPesquisar = driver.findElement(By.id("btnPesquisar"));
		
		if(cliente.getNome() != null) {
			nome.sendKeys(cliente.getNome());
		}
		if(cliente.getEmail() != null) {
			email.sendKeys(cliente.getEmail());
		}
		if(cliente.getCpf() != null) {
			cpf.sendKeys(cliente.getCpf());
		}
		if(cliente.getGenero() != null) {
			genero.sendKeys(cliente.getGenero().toString());
		}
		
		btnPesquisar.click();
		
		String codigoFontePagina = driver.getPageSource();
		
		boolean contemNome = true;
		if(cliente.getNome() != null) {
			contemNome = codigoFontePagina.contains(cliente.getNome());
		}
		
		boolean contemEmail = true;
		if(cliente.getEmail() != null) {
			contemEmail = codigoFontePagina.contains(cliente.getEmail());
		}
		
		boolean contemCpf = true;
		if(cliente.getCpf() != null) {
			contemCpf = codigoFontePagina.contains(cliente.getCpf());
		}
		
		boolean contemGenero = true;
		if(cliente.getGenero() != null) {
			contemGenero = codigoFontePagina.contains(cliente.getGenero().toString());
		}
		
		return contemNome && contemEmail && contemCpf && contemGenero;
		
		
		
	}
}
