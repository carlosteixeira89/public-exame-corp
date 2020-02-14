package br.com.exame.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "br.com.exame.steps",
		                  "br.com.exame.hooks"}, 
                 features = {"src/main/java/br/com/exame/feature" }, 
                		 plugin = {  "pretty", "html:target/cucumber-reports" }, 
                 tags = { "@chrome"})
public class Runner {
// plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
}
