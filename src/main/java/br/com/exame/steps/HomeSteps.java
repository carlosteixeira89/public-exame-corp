package br.com.exame.steps;

import java.io.IOException;

import org.junit.Assert;

import br.com.exame.core.DriverFactory;
import br.com.exame.core.PDFGenerator;
import br.com.exame.core.TipoDriver;
import br.com.exame.core.Utils;
import br.com.exame.core.YamlHelper;
import br.com.exame.functionalities.HomePageFunctionality;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;

public class HomeSteps {

	private HomePageFunctionality homepagefunctionality;
	public HomeSteps() throws IOException {
		// TODO Auto-generated constructor stub
		 	homepagefunctionality = new HomePageFunctionality();
	}
	
	
	
	
	@Before(value = "@pesquisa", order = 1)
	public void before(Scenario cenario) throws Exception {
		DriverFactory.getDriver(TipoDriver.CHROME).navigate().to("https://www.exame.com.br");
		
		//pdfgenerator.iniciaPDF(cenario);
	}

	@After(value = "@pesquisa", order = 1)
	public void finalizaPDF(Scenario scenario) throws Exception {
		
		//pdfgenerator.fechaPDF(scenario);
	}
	
	
	@Dado("^que o usuario acessa a home page$")
	public void que_o_usuario_acessa_a_home_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		//pdfgenerator.conteudoPDF("que_o_usuario_acessa_a_home_page: "+ YamlHelper.getAtributo("env","uat").toString());
	  
	}

	@Dado("^Realizada uma busca por \"([^\"]*)\"$")
	public void realizada_uma_busca_por(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		homepagefunctionality.clicarBotaoPesquisa();
		//pdfgenerator.conteudoPDF("realizada_uma_busca_por:");
		
		homepagefunctionality.escreverTextoPesquisa(arg1);
		
		
		
	}

	@Entao("^a pesquisa realizada com sucess$")
	public void a_pesquisa_realizada_com_sucess() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		//pdfgenerator.conteudoPDF("a_pesquisa_realizada_com_sucess: ");
	
		Assert.assertTrue(homepagefunctionality.validarPesquisa("Bolsa"));
	   
	}
	
	


}
