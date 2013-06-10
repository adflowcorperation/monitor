package kr.co.adflow.monitor.web;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesHostport {

	private Properties properties;
	private static final String filePath = "C:\\Users\\Administrator\\git\\monitor\\adfmonitor\\properties\\statsd.properties";
	
	public PropertiesHostport() {

		this.properties = new Properties();
		try {
			this.properties.load(new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath))));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String read(String key) {
		return properties.getProperty(key);
	}


}