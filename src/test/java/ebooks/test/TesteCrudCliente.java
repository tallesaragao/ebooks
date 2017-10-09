package ebooks.test;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.Telefone;
import ebooks.modelo.TipoEndereco;
import ebooks.modelo.TipoTelefone;

public class TesteCrudCliente {
	
	public static void main(String[] args) {
		TesteCrudCliente teste = new TesteCrudCliente();
		teste.deveAdicionarUmCliente();
	}
	
	//@Test
	public void deveAdicionarUmCliente() {     
		WebDriver driver = new FirefoxDriver();
		driver.get("http://localhost:8080/ebooks/clienteForm");
		
		Cliente cliente = new Cliente();
		cliente.setNome("Zeca Pagodinho");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			cliente.setDataNascimento(sdf.parse("11/05/1950"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cliente.setCpf("11111111111");
		cliente.setGenero('M');
		cliente.setEmail("zeca.pagodinho@hotmail.com");
		
		Endereco endereco = new Endereco();
		endereco.setCep("21070-140");
		endereco.setCidade("Rio de Janeiro");
		endereco.setEstado("RJ");
		endereco.setPais("Brasil");
		endereco.setBairro("Penha");
		endereco.setLogradouro("Rua José Maria");
		endereco.setNumero("504");
		endereco.setComplemento("Casa 15");
		TipoEndereco tipoEnd = new TipoEndereco();
		tipoEnd.setId(Long.valueOf(1));
		endereco.setTipoEndereco(tipoEnd);
		List<Endereco> enderecos = new ArrayList<>(1);
		enderecos.add(endereco);
		cliente.setEnderecos(enderecos);
		
		Telefone telefone = new Telefone();
		telefone.setDdd("21");
		telefone.setNumero("981995586");
		TipoTelefone tipoTel = new TipoTelefone();
		tipoTel.setId(Long.valueOf(1));
		telefone.setTipoTelefone(tipoTel);
		cliente.setTelefone(telefone);
		
		WebElement nome = driver.findElement(By.name("nome"));
		nome.sendKeys(cliente.getNome());
		
		WebElement dataNascimento = driver.findElement(By.name("dataNascimento"));
		String dataNascString = sdf.format(cliente.getDataNascimento());
		dataNascimento.sendKeys(dataNascString);
		
		WebElement cpf = driver.findElement(By.name("cpf"));
		cpf.sendKeys(cliente.getCpf());
		
		WebElement genero = driver.findElement(By.name("genero"));
		genero.sendKeys(String.valueOf(cliente.getGenero()));
		
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys(cliente.getEmail());
		
		Endereco end = cliente.getEnderecos().get(0);
		WebElement cep = driver.findElement(By.name("cep"));
		cep.sendKeys(end.getCep());
		
		WebElement cidade = driver.findElement(By.name("cidade"));
		cidade.sendKeys(end.getCidade());
		
		WebElement estado = driver.findElement(By.name("estado"));
		estado.sendKeys(end.getEstado());
		
		WebElement pais = driver.findElement(By.name("pais"));
		pais.sendKeys(end.getPais());
		
		WebElement bairro = driver.findElement(By.name("bairro"));
		bairro.sendKeys(end.getBairro());
		
		WebElement logradouro = driver.findElement(By.name("logradouro"));
		logradouro.sendKeys(end.getLogradouro());
		
		WebElement numeroEnd = driver.findElement(By.name("numeroEnd"));
		numeroEnd.sendKeys(end.getNumero());
		
		WebElement complemento = driver.findElement(By.name("complemento"));
		complemento.sendKeys(end.getComplemento());
		
		Select tipoEndereco = (Select) driver.findElement(By.name("tipoEndereco"));
		int index = Integer.valueOf(end.getTipoEndereco().getId().toString());
		tipoEndereco.selectByIndex(index);
		
		WebElement identificacao = driver.findElement(By.name("identificacao"));
		identificacao.sendKeys(end.getIdentificacao());
		
		WebElement ddd = driver.findElement(By.name("ddd"));
		ddd.sendKeys(cliente.getTelefone().getDdd());
		
		WebElement numeroTel = driver.findElement(By.name("numeroTel"));
		numeroTel.sendKeys(cliente.getTelefone().getNumero());
		
		Select tipoTelefone = (Select) driver.findElement(By.name("tipoTelefone"));
		index = Integer.valueOf(cliente.getTelefone().getTipoTelefone().getId().toString());
		tipoTelefone.selectByIndex(index);
		
		WebElement btnSalvar = driver.findElement(By.id("btnSalvar"));
		btnSalvar.click();
		
		String codigoFontePagina = driver.getPageSource();
		boolean sucesso = codigoFontePagina.contains("Cliente adicionado com sucesso");
		assertTrue(sucesso);
		
	}
}
