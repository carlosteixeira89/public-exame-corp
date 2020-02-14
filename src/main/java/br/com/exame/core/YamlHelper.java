package br.com.exame.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlHelper {
	
	public Object getAtributo(String... param) throws Exception {
		//String caminho = new File("src\\main\\resources").getAbsolutePath() + File.separator + "Info-properties.Yaml";

		//File file = new File("/home/runner/work/exame-automacao-testes-front/exame-automacao-testes-front/src/main/resources/Info-properties.Yaml");
		
		File file = new File("./src/main/resources/Info-properties.Yaml");
		
		InputStream input = new FileInputStream(file);
		Map<?, ?> mapAux = (Map<?, ?>) new Yaml().load(input);
		if (mapAux == null) {
			throw new Exception(String.format("A massa de dados não foi localizada no arquivo %s", file.getName()));
		}
		
		int cont;
		for (cont = 0; cont < (param.length - 1); cont++) {
			mapAux = (Map<?, ?>) mapAux.get(param[cont]);
		}
		
		return mapAux.get(param[cont]);
	}

}
