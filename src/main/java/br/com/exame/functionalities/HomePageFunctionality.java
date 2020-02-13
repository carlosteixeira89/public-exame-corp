package br.com.exame.functionalities;

import java.io.IOException;
import java.net.MalformedURLException;

import br.com.exame.core.PDFGenerator;
import br.com.exame.pages.HomeExamePage;

public class HomePageFunctionality {

	private HomeExamePage homeexampepage;
	public PDFGenerator pdfgenerator = new PDFGenerator();
	
	
	public HomePageFunctionality() throws IOException {
		// TODO Auto-generated constructor stub
		
		homeexampepage = new HomeExamePage();
	}
	
	public void clicarBotaoPesquisa() {
		//cadastro.getCaixaDeTextoNome().sendKeys(faker.funnyName().name());
		homeexampepage.getButtonPesquisa().click();
	}
	
	public void escreverTextoPesquisa(String text) throws Exception {
		//cadastro.getCaixaDeTextoNome().sendKeys(faker.funnyName().name());
		homeexampepage.getInputPesquisa().sendKeys(text);
		homeexampepage.getInputPesquisa().submit();
	}
	
	public boolean validarPesquisa(String text) {
		//cadastro.getCaixaDeTextoNome().sendKeys(faker.funnyName().name());
		boolean result = false;
		
		if (homeexampepage.getDivResultPesquisa().getText().contains(text))
			result= true;
		else 
			result = false;
		
		
		return result;

	}

	
}
