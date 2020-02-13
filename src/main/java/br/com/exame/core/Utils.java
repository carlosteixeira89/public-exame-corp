package br.com.exame.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Utils {

	public  Properties prop = new Properties();
	public  ChromeOptions chromeOptions;
	public 	String path = ".//src//main//report//Evidence.jpg";
	YamlHelper yamlhelper = new YamlHelper();

	 public String takeScreenshot(WebDriver driver) throws Exception{
			try{
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(yamlhelper.getAtributo("paths","imagem").toString()));

				
			} catch (Exception e){
				
				throw new Exception(e);
			}
			return path;
		
	
	 }
}
