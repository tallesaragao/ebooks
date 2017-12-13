package ebooks.test;

import org.openqa.selenium.WebDriver;

public abstract class AbstractTest implements ITest {
	protected WebDriver driver;
	protected String contexto = "http://localhost:8081/ebooks/";
	
	public void aguardar() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
