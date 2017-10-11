package ebooks.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		visitar();
		driver.findElement(By.id("btnNovoCliente")).click();
		return new ClienteFormPage(driver);
	}
	
	public boolean pesquisar(Cliente cliente) {
		visitar();
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
	
	public ClienteViewPage detalhes(Cliente cliente) {
		pesquisar(cliente);
		WebElement btnDetalhes;
		if(cliente.getId() != null) {
			btnDetalhes = driver.findElement(By.id("btnDetalhes" + cliente.getId()));
		}
		else {
			btnDetalhes = driver.findElements(By.name("detalhes")).get(0);
		}
		
		btnDetalhes.click();
		return new ClienteViewPage(driver);
	}
	
	public boolean verificaSeClienteFoiSalvo() {
		String codigoFontePagina = driver.getPageSource();
		boolean resultado = codigoFontePagina.contains("Cliente cadastrado com sucesso");
		return resultado;
	}
	
	public boolean verificaSeClienteFoiAlterado() {
		String codigoFontePagina = driver.getPageSource();
		boolean resultado = codigoFontePagina.contains("Alteração efetuada com sucesso");
		return resultado;
	}
	
	public boolean verificaSeExcluiu() {
		String codigoFontePagina = driver.getPageSource();
		boolean resultado = codigoFontePagina.contains("Exclusão efetuada com sucesso");
		return resultado;
	}
}
