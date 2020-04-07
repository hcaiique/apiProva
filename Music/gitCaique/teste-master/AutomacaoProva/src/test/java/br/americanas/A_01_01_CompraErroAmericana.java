package br.americanas;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class A_01_01_CompraErroAmericana {

	static WebDriver driver;
 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");

		System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");

		driver = new FirefoxDriver();
		// driver = new ChromeDriver();
		// site americanas
		driver.get("https://www.americanas.com.br/");

		WebElement caixaDeBusca = driver.findElement(By.id("h_search-input"));
		// String de panela para compara no final
		caixaDeBusca.sendKeys("Panela");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// pesquisa
		WebElement botaoBusca = driver.findElement(By.id("h_search-btn"));
		botaoBusca.click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// receber os dados da tela e comparar com a string inserida
		WebElement resultadoPesquisa = driver.findElement(By.className("card-product-name"));
		String resultado = resultadoPesquisa.getText();
		System.out.println(resultado);
		resultado = resultado.toLowerCase();

		if (resultado.contains("panela") || resultado.contains("press�o")) {
			System.out.println("Sucesso");
		} else {
			System.out.println("Erro na pesquisa");
			System.out.println("O resultado foi: " + resultado);
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// buscar produto
		WebElement botaoBusca2 = driver.findElement(By.className("card-product-name"));
		botaoBusca2.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Digitar CEP do comprador
		WebElement digitarCEP = driver.findElement(By.id("freight-field"));
		digitarCEP.click();
		digitarCEP.sendKeys("0000000");

		// clicar em ok apos inserir CEP
		WebElement clicarOkCPF = driver.findElement(By.id("freight-field-button"));
		clicarOkCPF.click();

		// clicar comprar
		WebElement clicarComprar = driver.findElement(By.id("btn-buy"));
		clicarComprar.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// clicar continuar a compra tive que usar o xpath pois nao estava localizando
		// clique pelo id
		WebElement clicarComprarConti = driver.findElement(By.id("btn-continue"));
		clicarComprarConti.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// clicar em remover itens usando xpath pois nao tinha Id
		WebElement clicarContinuarCompra = driver.findElement(ByXPath.xpath("/html/body/div[4]/section/section/div[1]/div[2]/div[1]/section/ul/li/div[2]/div[2]/div[2]/span"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		clicarContinuarCompra.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test
	public void test() {

		boolean resultadoTeste;

		// receber os dados da tela e comparar com a string pesquisada
		// caso tenha conexao com a base de dados a string da tela � comparada com a
		// base fazendo select
		WebElement resultadoPesquisaErro = driver.findElement(By.className("basket__wrapper"));
		String resultado2 = resultadoPesquisaErro.getText();
		System.out.println(resultado2);
		resultado2 = resultado2.toLowerCase();
		//Pegar a mensagem de cesta e vazia, zerando o item para verificar a msg, se tiver sua e cesta vem sucesso se nao tiver vem erro
		if (resultado2.contains("Sua") || resultado2.contains("cesta")) {
			System.out.println("Sucesso");
		} else {
			System.out.println("Erro na pesquisa");
			System.out.println("O resultado foi: " + resultado2);
		}

		String testes = "sua cesta est� vazia\r\n" + "Voltar para p�gina inicial ou escolha outros produtos";
		// as duas variaveis sendo exibidas caso tenha algum item a mais fica mais facil
		// de visualizar e corrigir
		System.out.println(testes);
		System.out.println(resultado2);

		if (resultado2.contains("produto") || resultado2.contains("Superrice")) {

			// os sysout eu deixei pos fica mais visivel o que est� acontecendo na tela
			System.out.println(resultado2);
			System.out.println("produto");
			System.out.println("Sucesso");
			resultadoTeste = true;

		} else {
			// os sysout eu deixei pos fica mais visivel o que est� acontecendo na tela
			System.out.println("Erro no testes");
			System.out.println("Erro na pesquisa");
			System.out.println("O resultado foi: " + resultado2);
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
