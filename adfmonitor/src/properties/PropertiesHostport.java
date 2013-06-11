package properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHostport {

	private Properties properties;
	

	public PropertiesHostport() {

		this.properties = new Properties();
		try {
			InputStream is = PropertiesHostport.class
					.getResourceAsStream("/properties/statsd.properties");

			properties.load(is);

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