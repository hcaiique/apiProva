package br.americanas;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class A_01_04_ErroSemEstoqueTestesAmericanas {
	static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");

		System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");

		driver = new FirefoxDriver();
		// driver = new ChromeDriver();
		// site
		driver.get("https://www.americanas.com.br/");

		WebElement caixaDeBusca = driver.findElement(By.id("h_search-input"));
		// String de panela para compara no final
		caixaDeBusca.sendKeys(constantes.buscaNaoExiste);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// pesquisa
		WebElement botaoBusca = driver.findElement(By.id("h_search-btn"));
		botaoBusca.click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// receber os dados da tela e comparar com a string inserida
		WebElement resultadoPesquisa = driver.findElement(By.id("section-middle"));
		String resultado = resultadoPesquisa.getText();
		System.out.println(resultado);
		resultado = resultado.toLowerCase();

		if (resultado.contains("000000000000000000000000") || resultado.contains("nenhum")) {
			System.out.println("Sucesso");
		} else {
			System.out.println("Erro na pesquisa");
			System.out.println("O resultado foi: " + resultado);
		}

		

	}

	@Test
	public void test() {

		boolean resultadoTeste;

		// receber os dados da tela e comparar com a string pesquisada
		// caso tenha conexao com a base de dados a string da tela � comparada com a
		// base fazendo select
		WebElement resultadoPesquisa = driver.findElement(By.id("section-middle"));
		String resultado = resultadoPesquisa.getText();
		System.out.println(resultado);
		resultado = resultado.toLowerCase();

		if (resultado.contains("000000000000000000000000") || resultado.contains("nenhum")) {
			System.out.println("Sucesso");
		} else {
			System.out.println("Erro na pesquisa");
			System.out.println("O resultado foi: " + resultado);
		}


		String testes = "sua";
		// as duas variaveis sendo exibidas caso tenha algum item a mais fica mais facil
		// de visualizar e corrigir
		System.out.println(testes);
	

		if (resultado.contains("000000000000000000000000") || resultado.contains("nenhum")) {

			// os sysout eu deixei pos fica mais visivel o que est� acontecendo na tela
			System.out.println(resultado);
		
			System.out.println("Sucesso");
			resultadoTeste = true;

		} else {
			// os sysout eu deixei pos fica mais visivel o que est� acontecendo na tela
			System.out.println("Erro no testes");
			System.out.println("Erro na pesquisa");
			System.out.println("O resultado foi: " + resultado);
			resultadoTeste = false;

		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Assert.assertTrue(resultadoTeste);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		// para fechar o brownser
		driver.close();

	}

}