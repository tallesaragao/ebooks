package ebooks.test;

import org.openqa.selenium.WebDriver;

public abstract class AbstractTest {
	protected WebDriver driver;
	protected String contexto = "http://localhost:8081/ebooks/";
}
