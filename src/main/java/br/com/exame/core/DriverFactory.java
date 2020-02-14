package br.com.exame.core;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class DriverFactory {

	public DriverFactory() {
	}
	
	//https://chromedriver.storage.googleapis.com/index.html?path=80.0.3987.16/

	private static WebDriver driver;

	public static WebDriver getDriver(TipoDriver tipo){
		switch (tipo) {
		case FIREFOX:
			System.setProperty("webdriver.gecko.driver",
			System.getProperty("user.dir") + "/src/main/resources/geckodriver.exe");
			if (driver == null) {
				FirefoxOptions opcoes = new FirefoxOptions();
				opcoes.addArguments("--headless");
				driver = new FirefoxDriver(opcoes);
			}
			break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver",
			System.getProperty("user.dir") + "./src/main/resources/chromedriver");
			//System.getProperty("//home//runner//work//exame-automacao-testes-front//exame-automacao-testes-front//src//main//resources//chromedriver");	
			//System.getProperty(("") +"/home/runner/work/_actions/nanasess/setup-chromedriver/v1.0.1/lib/setup-chromedriver.sh"));
		
			
			if (driver == null) {
				ChromeOptions opcoes = new ChromeOptions();
				opcoes.addArguments("--headless");
				driver = new ChromeDriver(opcoes);
			}
			break;
		default:
			break;
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;

	}
	
	
	public static Boolean webDriverNaoNulo() {
		return driver != null;
	}

	public static WebDriver encerraDriver() {
		if (driver != null) {
			driver.quit();
		}
		return driver;
	}

}
