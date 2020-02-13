package br.com.exame.steps;

import br.com.exame.core.DriverFactory;
import br.com.exame.core.PDFGenerator;
import br.com.exame.core.TipoDriver;
import br.com.exame.core.Utils;
import br.com.exame.core.YamlHelper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;

public class AcessarHomeSteps {
	
	PDFGenerator pdfgenerator = new PDFGenerator();
	private String baseUrl = "https://exame.abril.com.br/";
	Utils util = new Utils();
	YamlHelper YamlHelper = new YamlHelper();
	
	@Before(value = "@chrome", order = 1)
	public void before(Scenario cenario) throws Exception {
		DriverFactory.getDriver(TipoDriver.CHROME).navigate().to("https://exame.abril.com.br/");
		
		pdfgenerator.iniciaPDF(cenario);
	}

	@After(value = "@chrome", order = 1)
	public void finalizaPDF(Scenario scenario) throws Exception {
		
		pdfgenerator.fechaPDF(scenario);
	}

	@Dado("^que o usuario acessa a home no chrome$")
	public void que_o_usuario_acessa_a_home_no_chrome() throws Throwable {
	
	
	
	pdfgenerator.conteudoPDF("que_o_usuario_acessa_a_home_no_chrome: "+ baseUrl);
	
	}

	@Entao("^a home e acessada com sucesso no respectivo navegador$")
	public void a_home_e_acessada_com_sucesso_no_respectivo_navegador() throws Throwable {
	pdfgenerator.conteudoPDF("a_home_e_acessada_com_sucesso_no_respectivo_navegador");
	
	}
}
