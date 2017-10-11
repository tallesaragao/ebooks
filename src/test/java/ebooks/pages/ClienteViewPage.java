package ebooks.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ebooks.modelo.Cliente;

public class ClienteViewPage {
	
	private WebDriver driver;

	public ClienteViewPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public ClienteListPage excluir() {
		WebElement btnClienteExcluir = driver.findElement(By.id("btnClienteExcluir"));
		
		btnClienteExcluir.click();
		driver.switchTo().alert().accept();
		return new ClienteListPage(driver);
	}
	
	public ClienteFormPage editar() {
		WebElement btnClienteEdit = driver.findElement(By.id("btnClienteEdit"));
		btnClienteEdit.click();
		return new ClienteFormPage(driver);
		
	}
	
	public boolean verificaSeDetalhesDoClienteForamExibidos(Cliente cliente) {
		String pageSource = driver.getPageSource();
		boolean resultado = pageSource.contains("Informações do cliente");
		resultado = resultado && pageSource.contains(cliente.getNome());
		return resultado;
	}
	
}
