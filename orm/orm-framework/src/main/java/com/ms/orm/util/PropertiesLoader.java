package com.ms.orm.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
	public static Properties loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(PropertiesLoader.class.getResourceAsStream("/db.properties"));
		return properties;
	}
}
