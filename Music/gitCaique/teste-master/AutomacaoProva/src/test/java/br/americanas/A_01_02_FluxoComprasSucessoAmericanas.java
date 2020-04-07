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

public class A_01_02_FluxoComprasSucessoAmericanas {

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
		caixaDeBusca.sendKeys(constantes.buscaSucesso);
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

		if (resultado.contains(constantes.buscaSucesso) || resultado.contains("press�o")) {
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
		digitarCEP.sendKeys("60841270");

		// clicar em ok apos inserir cpf
		WebElement clicarOkCPF = driver.findElement(By.id("freight-field-button"));
		clicarOkCPF.click();

		// clicar comprar
		WebElement clicarComprar = driver.findElement(By.id("btn-buy"));
		clicarComprar.click();

		// clicar continuar a compra tive que usar o xpath pois nao estava localizando
		// pelo class e n�o tinha id
		WebElement clicarComprarConti = driver
				.findElement(ByXPath.xpath("/html/body/div[6]/div/div/div[2]/div[3]/div/div[2]/div/a/div/span"));
		clicarComprarConti.click();

//		usando classname
//		WebElement clicarComprarConti = driver.findElement(By.className("TextUI-sc-1i9za0i-4 gulQE TextUI-sc-12tokcy-0 gYgvXd"));
//		clicarComprarConti.click();

		// clicar comprar continuar
		WebElement clicarContinuar = driver.findElement(By.id("btn-continue"));
		clicarContinuar.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// clicar continuar a compra ate inserir e-mail e senha
		WebElement clicarContinuarCompra = driver.findElement(By.id("buy-button"));
		clicarContinuarCompra.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// inserir e-mail e senha
		WebElement caixadeEmail = driver.findElement(By.id("email-input"));
		// String de panela para compara no final
		caixadeEmail.sendKeys(constantes.email);
		WebElement caixadeEmailSenha = driver.findElement(By.id("password-input"));
		caixadeEmailSenha.sendKeys(constantes.senhaEmail);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test
	public void test() {

		boolean resultadoTeste;

		// receber os dados da tela e comparar com a string pesquisada
		// caso tenha conexao com a base de dados a string da tela � comparada com a
		// base fazendo select
		WebElement resultadoPesquisa = driver.findElement(By.className("entrar__wrapper"));
		String resultado2 = resultadoPesquisa.getText();
		System.out.println(resultado2);
		resultado2 = resultado2.toLowerCase();

		if (resultado2.contains("Login") || resultado2.contains("cliente")) {
			System.out.println("Sucesso");
		} else {
			System.out.println("Erro na pesquisa");
			System.out.println("O resultado foi: " + resultado2);
		}

		String testes = "login";
		// as duas variaveis sendo exibidas caso tenha algum item a mais fica mais facil
		// de visualizar e corrigir
		System.out.println(testes);
		System.out.println(resultado2);

		if (resultado2.contains("Login") || resultado2.contains("cliente")) {

			// os sysout eu deixei pos fica mais visivel o que est� acontecendo na tela
			System.out.println(resultado2);
			System.out.println("sua cesta");
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
		// comparacao de dados com a tela, porem o correto seria pegar os daos da tela e
		// fazer a comparacao com a base de dados
		Assert.assertTrue(resultadoTeste);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		// fechar aplicacao
		driver.close();

	}

}
