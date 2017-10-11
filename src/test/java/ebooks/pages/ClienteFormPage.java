package ebooks.pages;

import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import ebooks.modelo.Cliente;
import ebooks.modelo.Endereco;
import ebooks.modelo.Telefone;
import ebooks.modelo.TipoEndereco;
import ebooks.modelo.TipoTelefone;

public class ClienteFormPage {

	private WebDriver driver;

	public ClienteFormPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get("http://localhost:8080/ebooks/clienteForm");
	}

	public void limparFormulario() {
		WebElement nome = driver.findElement(By.name("nome"));
		nome.clear();

		WebElement dataNascimento = driver.findElement(By.name("dataNascimento"));
		dataNascimento.clear();

		WebElement cpf = driver.findElement(By.name("cpf"));
		cpf.clear();

		WebElement genero = driver.findElement(By.name("genero"));
		genero.clear();

		WebElement email = driver.findElement(By.name("email"));
		email.clear();

		WebElement cep = driver.findElement(By.name("cep"));
		cep.clear();

		WebElement cidade = driver.findElement(By.name("cidade"));
		cidade.clear();

		WebElement estado = driver.findElement(By.name("estado"));
		estado.clear();

		WebElement pais = driver.findElement(By.name("pais"));
		pais.clear();

		WebElement bairro = driver.findElement(By.name("bairro"));
		bairro.clear();

		WebElement logradouro = driver.findElement(By.name("logradouro"));
		logradouro.clear();

		WebElement numeroEnd = driver.findElement(By.name("numeroEnd"));
		numeroEnd.clear();

		WebElement complemento = driver.findElement(By.name("complemento"));
		complemento.clear();

		Select tipoEndereco = new Select(driver.findElement(By.name("tipoEndereco")));
		tipoEndereco.deselectAll();

		WebElement identificacao = driver.findElement(By.name("identificacao"));
		identificacao.clear();

		WebElement ddd = driver.findElement(By.name("ddd"));
		ddd.clear();

		WebElement numeroTel = driver.findElement(By.name("numeroTel"));
		numeroTel.clear();

		Select tipoTelefone = new Select(driver.findElement(By.name("tipoTelefone")));
		tipoTelefone.deselectAll();
	}

	public void preencherFormulario(Cliente cliente) {
		WebElement nome = driver.findElement(By.name("nome"));
		if(nome.getText() != null && !nome.getText().equals("") && cliente.getNome() != null) {
			nome.clear();
		}
		nome.sendKeys(cliente.getNome() == null ? "" : cliente.getNome());
		
		WebElement dataNascimento = driver.findElement(By.name("dataNascimento"));
		String dataNascString = "";
		if(cliente.getDataNascimento() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataNascString = sdf.format(cliente.getDataNascimento());
		}
		if(dataNascimento.getText() != null && !dataNascimento.getText().equals("") && cliente.getDataNascimento() != null) {
			dataNascimento.clear();
		}
		dataNascimento.sendKeys(dataNascString);
		
		WebElement cpf = driver.findElement(By.name("cpf"));
		if(cpf.getText() != null && !cpf.getText().equals("") && cliente.getCpf() != null) {
			cpf.clear();
		}
		cpf.sendKeys(cliente.getCpf() == null ? "" : cliente.getCpf());
		
		WebElement genero = driver.findElement(By.name("genero"));
		if(genero.getText() != null && !genero.getText().equals("") && cliente.getGenero() != null) {
			genero.clear();
		}
		genero.sendKeys(cliente.getGenero() == null ? "" : String.valueOf(cliente.getGenero()));
		
		WebElement email = driver.findElement(By.name("email"));
		if(email.getText() != null && !email.getText().equals("") && cliente.getEmail() != null) {
			email.clear();
		}
		email.sendKeys(cliente.getEmail() == null ? "" : cliente.getEmail());
		
		Endereco end = new Endereco();
		end.setTipoEndereco(new TipoEndereco());
		if(cliente.getEnderecos() != null) {
			end = cliente.getEnderecos().get(0);
		}
		WebElement cep = driver.findElement(By.name("cep"));
		if(cep.getText() != null && !cep.getText().equals("") && end.getCep() != null) {
			cep.clear();
		}
		cep.sendKeys(end.getCep() == null ? "" : end.getCep());
		
		WebElement cidade = driver.findElement(By.name("cidade"));
		cidade.sendKeys("");
	
		new WebDriverWait(driver, 20);
		
		WebElement numeroEnd = driver.findElement(By.name("numeroEnd"));
		if(numeroEnd.getText() != null && !numeroEnd.getText().equals("") && end.getNumero() != null) {
			numeroEnd.clear();
		}
		numeroEnd.sendKeys(end.getNumero() == null ? "" : end.getNumero());
		
		WebElement complemento = driver.findElement(By.name("complemento"));
		if(complemento.getText() != null && !complemento.getText().equals("") && end.getComplemento() != null) {
			complemento.clear();
		}
		complemento.sendKeys(end.getComplemento() == null ? "" : end.getComplemento());
		
		Select tipoEndereco = new Select(driver.findElement(By.name("tipoEndereco")));
		if(end.getTipoEndereco().getId() != null) {
			int index = Integer.valueOf(end.getTipoEndereco().getId().toString());
			tipoEndereco.selectByIndex(index);
		}
		
		WebElement identificacao = driver.findElement(By.name("identificacao"));
		if(identificacao.getText() != null && !identificacao.getText().equals("") && end.getIdentificacao() != null) {
			identificacao.clear();
		}
		identificacao.sendKeys(end.getIdentificacao() == null ? "" : end.getIdentificacao());
		
		Telefone telefone = new Telefone();
		telefone.setTipoTelefone(new TipoTelefone());
		if(cliente.getTelefone() != null) {
			telefone = cliente.getTelefone();
		}
		
		WebElement ddd = driver.findElement(By.name("ddd"));
		if(ddd.getText() != null && !ddd.getText().equals("") && telefone.getDdd() != null) {
			ddd.clear();
		}
		ddd.sendKeys(telefone.getDdd() == null ? "" : telefone.getDdd());
		
		WebElement numeroTel = driver.findElement(By.name("numeroTel"));
		if(numeroTel.getText() != null && !numeroTel.getText().equals("") && telefone.getNumero() != null) {
			numeroTel.clear();
		}
		numeroTel.sendKeys(telefone.getNumero() == null ? "" : telefone.getNumero());
		
		Select tipoTelefone = new Select(driver.findElement(By.name("tipoTelefone")));
		if(telefone.getTipoTelefone().getId() != null) {
			int index = Integer.valueOf(telefone.getTipoTelefone().getId().toString());
			tipoTelefone.selectByIndex(index);
		}
		
		new WebDriverWait(driver, 5);
	}

	public ClienteListPage salvar(Cliente cliente) {

		preencherFormulario(cliente);

		WebElement btnSalvar = driver.findElement(By.id("btnSalvar"));
		btnSalvar.click();
		
		return new ClienteListPage(driver);
	}
	
	public ClienteListPage alterar(Cliente cliente) {
		
		preencherFormulario(cliente);
		
		WebElement btnAlterar = driver.findElement(By.id("btnAlterar"));
		btnAlterar.click();
		
		return new ClienteListPage(driver);
	}

	public boolean cancelar() {
		WebElement btnSalvar = driver.findElement(By.id("btnSalvar"));
		btnSalvar.click();
		String pageSource = driver.getPageSource();
		boolean resultado = pageSource.contains("Lista de clientes");
		return resultado;
	}
	
	public boolean verificaSeExistemMensagensDeErro() {
		String pageSource = driver.getPageSource();
		boolean resultado = false;
		resultado = resultado || pageSource.contains("Nome do cliente deve estar preenchido");
		resultado = resultado || pageSource.contains("E-mail deve estar preenchido");
		resultado = resultado || pageSource.contains("E-mail deve estar preenchido");
		resultado = resultado || pageSource.contains("Data de nascimento inválida");
		resultado = resultado || pageSource.contains("CPF deve estar preenchido");
		resultado = resultado || pageSource.contains("CPF do cliente inválido");
		resultado = resultado || pageSource.contains("Tipo de endereço deve ser informado");
		resultado = resultado || pageSource.contains("A identificação do endereço deve ser composta por uma frase curta");
		resultado = resultado || pageSource.contains("Logradouro deve estar preenchido");
		resultado = resultado || pageSource.contains("Número do endereço deve estar preenchido");
		resultado = resultado || pageSource.contains("Bairro deve estar preenchido");
		resultado = resultado || pageSource.contains("CEP deve estar preenchido");
		resultado = resultado || pageSource.contains("Cidade deve estar preenchida");
		resultado = resultado || pageSource.contains("Estado deve estar preenchido");
		resultado = resultado || pageSource.contains("DDD deve estar preenchido");
		resultado = resultado || pageSource.contains("Número do telefone deve estar preechido");
		resultado = resultado || pageSource.contains("Tipo do telefone deve ser selecionado");
		return resultado;
	}
}
