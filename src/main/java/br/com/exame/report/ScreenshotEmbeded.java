package br.com.exame.report;

import java.net.MalformedURLException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import br.com.exame.core.DriverFactory;
import br.com.exame.core.TipoDriver;
import cucumber.api.Scenario;

public class ScreenshotEmbeded {
	
	public static void screenshot(Scenario cenario) throws WebDriverException, MalformedURLException {
		if(cenario.isFailed()) {
			final byte[] screenshot = ((TakesScreenshot)DriverFactory.getDriver(TipoDriver.CHROME)).getScreenshotAs(OutputType.BYTES);
			cenario.embed(screenshot, "image/png");
		}
	}

}
