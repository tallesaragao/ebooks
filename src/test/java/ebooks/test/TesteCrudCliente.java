package ebooks.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import ebooks.modelo.Cliente;
import ebooks.pages.ClienteFormPage;

public class TesteCrudCliente {
	
	private WebDriver driver;
	private ClienteFormPage formularioCliente;
	
	@Before
	public void iniciar() {
		System.setProperty("webdriver.chrome.driver", "D:\\Users\\tallesaragao\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	//@Test
	public void deveAdicionarUmCliente() {
		formularioCliente = new ClienteFormPage(driver);
		Cliente cliente = new Cliente();
		cliente.setNome("Teste JUnit");
		boolean resultado = formularioCliente.salvar(cliente);
		assertTrue(resultado);
		
	}
	
	public void devePesquisarUmCliente(Cliente cliente) {
		driver.get("http://localhost:8080/ebooks/clienteList");
		
		WebElement nome = driver.findElement(By.name("nome"));
		WebElement email = driver.findElement(By.name("email"));
		WebElement cpf = driver.findElement(By.name("cpf"));
		WebElement genero = driver.findElement(By.name("genero"));
	}
	
	@After
	public void encerrar() {
		driver.close();
	}
}
