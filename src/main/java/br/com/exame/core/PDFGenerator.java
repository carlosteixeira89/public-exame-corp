package br.com.exame.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import com.itextpdf.text.DocumentException;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;



import cucumber.api.Scenario;


public class PDFGenerator extends DriverFactory {

	public Method method;

	private Document document;
	
	Utils util = new Utils();
	YamlHelper yamlhelper = new YamlHelper();


	//public Drivers driv = new Drivers();

	
	/*
	* Método responsável por iniciar a criação do arquivo PDF
	*
	*/
	public void iniciaPDF(Scenario cenario) throws Exception {
	String featureName = "";
	String rawFeatureName = cenario.getId().split(";")[0].replace("-", " ");
	featureName = featureName + rawFeatureName.substring(0, 1).toUpperCase() + rawFeatureName.substring(1);
	// Document document = new Document(PageSize.LETTER.rotate());
	document = new Document(PageSize.LETTER.rotate());
	document.setPageSize(PageSize.LETTER);
	File file = new File(".//src//main//report//"+featureName);
	file.mkdir();
	String pathtoFolder=file.getAbsolutePath().toString();
	//String path= Utils.configProp().getProperty("Path_ScreenShot").toString();
	PdfWriter.getInstance(document, new FileOutputStream((pathtoFolder+"//"+cenario.getName()+" "+"evidencia.pdf")));
	
	
	document.open();
	Date agora = new Date();

	String converterDataParaString = DateFormat.getInstance().format(agora);
	
    String[] headers = new String[]{ "ENV", "Feature", "Cenario", "Status" ,"Data"};
    String[][] rows = new String[][]{{"uat", featureName, "Cenário: " + cenario.getName(),cenario.getStatus() ,converterDataParaString},};
    
    

    try {
        
        PdfWriter.getInstance(document,new FileOutputStream(new File(".//src//main//report//Table.pdf")));
        document.open();
        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 17, Font.NORMAL);
        Font fontRow = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        
        Font formatColor = new Font();
        formatColor.setFamily("HELVETICA");
        formatColor.setStyle(Font.BOLD);
        formatColor.setSize(15);
        formatColor.setColor(BaseColor.BLACK);
    	String content = "RELATÓRIO DE AUTOMAÇÃO DE TESTES";
    	
    	
        Image logo = Image.getInstance("./src/main/report/logo-exame.png");
    	logo.scalePercent(100);
    	document.add(logo);   
     	document.add(Chunk.NEWLINE);
     	document.add(new Paragraph(content, formatColor));
    	document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(headers.length);
        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setGrayFill(0.9f);
            cell.setPhrase(new Phrase(header.toUpperCase(), fontHeader));
            table.addCell(cell);
        }
        
        table.completeRow();

        for (String[] row : rows) {
            for (String data : row) {
            	int cont = headers.length;
            	if(headers.length == 3) {
            		if (cenario.getStatus() == "passed") {
            	    	Font color = new Font();
            	    	color.setFamily("Courier");
            	    	color.setStyle(Font.BOLD);
            	    	color.setSize(20);
            	    	color.setColor(BaseColor.GREEN);
            	    	 Phrase phrase = new Phrase(data, color);
            	    	  table.addCell(new PdfPCell(phrase));
            	    	//document.add(new Paragraph("Status: " + cenario.getStatus(), color));
            	    	} else {
            	    	Font color = new Font();
            	    	color.setFamily("Courier");
            	    	color.setColor(BaseColor.RED);
            	    	color.setStyle(Font.BOLD);
            	    	color.setSize(20);
            	    	 Phrase phrase = new Phrase(data, color);
            	    	  table.addCell(new PdfPCell(phrase));
            	    	//document.add(new Paragraph("Status: " + cenario.getStatus(), color));
            	    	}
            	   
            	}
            	
            		
                Phrase phrase = new Phrase(data, fontRow);
               
                
                table.addCell(new PdfPCell(phrase));
            }
            table.completeRow();
        }

      
        document.setMargins(40, 40, 80, 40);
        document.add(table);
    } catch (DocumentException FileNotFoundException ) {
        
    } finally {
      
    }
 

    
   	document.add(Chunk.NEWLINE);

	}

	/*
	* Método responsável pelo conteúdo de cada step do BDD no arquivo PDF.
	*
	*/
	public void conteudoPDF(String conteudo) throws Exception {

	document.newPage();

	Font color = new Font();
	color.setFamily("Courier");
	color.setStyle(Font.BOLD);
	color.setSize(15);
	color.setColor(BaseColor.BLUE);

	document.add(new Paragraph(conteudo, color));

	//File screenshot = ((TakesScreenshot) getDriver(TipoDriver.CHROME)).getScreenshotAs(OutputType.FILE);
	//FileUtils.copyFile(screenshot, new File("screenshot.png"));
	
	String path = util.takeScreenshot(DriverFactory.getDriver(TipoDriver.CHROME));
	Image image = Image.getInstance(path);
	
	
	float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
	float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
	
	image.setBorder(Rectangle.OUT_BOTTOM);
	image.setBorderColor(BaseColor.BLACK);
	image.setBorderWidth(1f);
	image.scaleToFit(documentWidth, documentHeight);
	document.add(image);
	Files.deleteIfExists(Paths.get(path));
	
	}


	/*
	 * Método responsável por finalizar o PDF e, no caso de falha, tira uma evidência.
	 */
	public void fechaPDF(Scenario scenario) throws DocumentException, IOException {
	if (scenario.isFailed()) {

	document.newPage();

	Font colorFailed = new Font();
	colorFailed.setFamily("Courier");
	colorFailed.setStyle(Font.BOLD);
	colorFailed.setSize(15);
	colorFailed.setColor(BaseColor.RED);

	document.add(new Paragraph("Status: " + scenario.getStatus(), colorFailed));
	document.add(new Paragraph("Evidência da falha: ", colorFailed));


	File screenshot = ((TakesScreenshot)getDriver(TipoDriver.CHROME)).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(screenshot, new File("screenshot.png"));

	Image image = Image.getInstance("screenshot.png");
	float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
	float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
	image.scaleToFit(documentWidth, documentHeight);
	image.setBorder(Rectangle.OUT_BOTTOM);
	image.setBorderColor(BaseColor.BLACK);
	image.setBorderWidth(1f);
	document.add(image);
	document.close();
	} else {
	Font colorPassed = new Font();
	colorPassed.setFamily("Courier");
	colorPassed.setColor(BaseColor.GREEN);
	colorPassed.setStyle(Font.BOLD);
	colorPassed.setSize(20);
	document.add(new Paragraph("Status: " + scenario.getStatus(), colorPassed));

	document.close();
	
	}
	}

	public String scenarioName() {
	return method.getName();
	}

	

	public String getDefaultBrowser() {
	try {
	// Get registry where we find the default browser
	Process process = Runtime.getRuntime().exec("REG QUERY HKEY_CLASSES_ROOT\\http\\shell\\open\\command");
	Scanner kb = new Scanner(process.getInputStream());
	while (kb.hasNextLine()) {
	// Get output from the terminal, and replace all '\' with '/' (makes regex a bit
	// more manageable)
	String registry = (kb.nextLine()).replaceAll("\\\\", "/").trim();

	// Extract the default browser
	Matcher matcher = Pattern.compile("/(?=[^/]*$)(.+?)[.]").matcher(registry);
	if (matcher.find()) {
	// Scanner is no longer needed if match is found, so close it
	kb.close();
	String defaultBrowser = matcher.group(1);

	// Capitalize first letter and return String
	defaultBrowser = defaultBrowser.substring(0, 1).toUpperCase()
	+ defaultBrowser.substring(1, defaultBrowser.length());
	return defaultBrowser;
	}
	}
	// Match wasn't found, still need to close Scanner
	kb.close();
	} catch (Exception e) {
	e.printStackTrace();
	}
	// Have to return something if everything fails
	return "Error: Unable to get default browser";
	}


}