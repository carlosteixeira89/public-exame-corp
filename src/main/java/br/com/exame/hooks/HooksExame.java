package br.com.exame.hooks;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.cucumber.listener.Reporter;
import com.itextpdf.text.DocumentException;

import br.com.exame.core.DriverFactory;
import br.com.exame.core.PDFGenerator;
import br.com.exame.core.YamlHelper;
import br.com.exame.report.ScreenshotEmbeded;
import cucumber.api.Scenario;
import cucumber.api.java.After;

public class HooksExame {

	PDFGenerator pdfgenerator;
	YamlHelper yamlhelper = new YamlHelper();

	
	@Before
	public void antesScenario(Scenario cenario) throws Exception {
		 
	
	}

	@After
	public void depoisScenario(Scenario cenario) throws Exception {
		ScreenshotEmbeded.screenshot(cenario);
		DriverFactory.encerraDriver();
		

	}

}
