package br.com.exame.pages;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import br.com.exame.core.DriverFactory;
import br.com.exame.core.TipoDriver;

public class HomeExamePage{
	
	public HomeExamePage(){
		PageFactory.initElements(DriverFactory.getDriver(TipoDriver.CHROME), this);
	}
	
	@FindBy(xpath = "//i[contains(text(),'search')]")
	WebElement buttonPesquisa;
	

	@FindBy(xpath = "//*[@id=\"cse-search-box\"]/input")
	WebElement inputPesquisa;
	
	@FindBy(xpath = "/html/body/div[5]/div/div[1]")
	WebElement divResultPesquisa;
	
	
	
	public WebElement getDivResultPesquisa() {
		return divResultPesquisa;
	}


	public void setDivResultPesquisa(WebElement divResultPesquisa) {
		this.divResultPesquisa = divResultPesquisa;
	}


	public WebElement getButtonPesquisa() {
		return buttonPesquisa;
	}


	public void setButtonPesquisa(WebElement buttonPesquisa) {
		this.buttonPesquisa = buttonPesquisa;
	}


	public WebElement getInputPesquisa() {
		return inputPesquisa;
	}


	public void setInputPesquisa(WebElement inputPesquisa) {
		this.inputPesquisa = inputPesquisa;
	}

		
}
