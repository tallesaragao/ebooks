package ebooks.test.run;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.Telefone;
import ebooks.modelo.TipoEndereco;
import ebooks.modelo.TipoTelefone;
import ebooks.pages.ClienteFormPage;
import ebooks.pages.ClienteListPage;
import ebooks.pages.ClienteViewPage;

public class TesteCrudCliente {
	
	private WebDriver driver;
	private ClienteListPage listaClientes;
	private ClienteFormPage formularioCliente;
	private ClienteViewPage detalhesCliente;
	private Cliente cliente;
	
	
	@Before
	public void iniciar() {
		System.setProperty("webdriver.chrome.driver", "D:\\Users\\tallesaragao\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
		cliente = new Cliente();
		cliente.setNome("Teste JUnit");
		cliente.setDataNascimento(new Date("11/05/1997"));
		cliente.setCpf("365.541.953-86");
		cliente.setGenero('M');
		cliente.setEmail("junit@email.com");
		
		Telefone telefone = new Telefone();
		TipoTelefone tipoTelefone = new TipoTelefone();
		tipoTelefone.setId(Long.valueOf(2));
		telefone.setTipoTelefone(tipoTelefone);
		telefone.setDdd("11");
		telefone.setNumero("4455-2211");
		cliente.setTelefone(telefone);
		
		Endereco endereco = new Endereco();
		endereco.setCep("08773-150");
		endereco.setNumero("223");
		endereco.setComplemento("junit complemento");
		endereco.setIdentificacao("junit casa");
		TipoEndereco tipoEndereco = new TipoEndereco();
		tipoEndereco.setId(Long.valueOf(1));
		endereco.setTipoEndereco(tipoEndereco);
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>(1);
		enderecos.add(endereco);
		cliente.setEnderecos(enderecos);
	}
	
	//@Test
	public void deveAdicionarUmCliente() {
		listaClientes = new ClienteListPage(driver);
		formularioCliente = listaClientes.novo();
		listaClientes = formularioCliente.salvar(cliente);
		boolean resultado = listaClientes.verificaSeClienteFoiSalvo();
		assertTrue(resultado);
	}
	
	//@Test
	public void deveMostrarDetalhesDeUmCliente() {
		listaClientes = new ClienteListPage(driver);
		detalhesCliente = listaClientes.detalhes(cliente);
		boolean resultado = detalhesCliente.verificaSeDetalhesDoClienteForamExibidos(cliente);
		assertTrue(resultado);
	}
	
	//@Test
	public void deveAlterarUmCliente() {
		listaClientes = new ClienteListPage(driver);
		detalhesCliente = listaClientes.detalhes(cliente);
		Cliente clienteAlteracao = new Cliente();
		clienteAlteracao.setNome("JUnit alteração de nome");
		formularioCliente = detalhesCliente.editar();
		listaClientes = formularioCliente.alterar(clienteAlteracao);
		boolean resultado = listaClientes.verificaSeClienteFoiSalvo();
		assertTrue(resultado);
	}
	
	//@Test
	public void deveExcluirUmCliente() {
		listaClientes = new ClienteListPage(driver);
		ClienteViewPage detalhes = listaClientes.detalhes(cliente);
		listaClientes = detalhes.excluir();
		boolean resultado = listaClientes.verificaSeExcluiu();
		assertTrue(resultado);
	}
	
	@After
	public void encerrar() {
		driver.close();
	}
}
