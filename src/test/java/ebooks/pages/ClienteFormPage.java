package ebooks.pages;

import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;

public class ClienteFormPage {

	private WebDriver driver;

	public ClienteFormPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void visita() {
		driver.get("http://localhost:8080/ebooks/clienteForm");
	}
	
	public boolean salvar(Cliente cliente) {
		WebElement nome = driver.findElement(By.name("nome"));
		nome.sendKeys(cliente.getNome());
		
		WebElement dataNascimento = driver.findElement(By.name("dataNascimento"));
		SimpleDateFormat sdf = new SimpleDateFormat();
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
		
		Select tipoEndereco = new Select(driver.findElement(By.name("tipoEndereco")));
		int index = Integer.valueOf(end.getTipoEndereco().getId().toString());
		tipoEndereco.selectByIndex(index);
		
		WebElement identificacao = driver.findElement(By.name("identificacao"));
		identificacao.sendKeys(end.getIdentificacao());
		
		WebElement ddd = driver.findElement(By.name("ddd"));
		ddd.sendKeys(cliente.getTelefone().getDdd());
		
		WebElement numeroTel = driver.findElement(By.name("numeroTel"));
		numeroTel.sendKeys(cliente.getTelefone().getNumero());
		
		Select tipoTelefone = new Select(driver.findElement(By.name("tipoTelefone")));
		index = Integer.valueOf(cliente.getTelefone().getTipoTelefone().getId().toString());
		tipoTelefone.selectByIndex(index);
		
		WebElement btnSalvar = driver.findElement(By.id("btnSalvar"));
		btnSalvar.click();
		
		String codigoFontePagina = driver.getPageSource();
		
		boolean sucesso = codigoFontePagina.contains("Cliente cadastrado com sucesso");
		
		return sucesso;
	}
	
	public void cancelar() {
		WebElement btnSalvar = driver.findElement(By.id("btnSalvar"));
		btnSalvar.click();
	}
}
