package ebooks.test;

import org.openqa.selenium.WebDriver;

public abstract class AbstractTest implements ITest {
	protected WebDriver driver;
	protected String contexto = "http://localhost:8080/ebooks/";
}
