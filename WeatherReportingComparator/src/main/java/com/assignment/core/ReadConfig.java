package com.assignment.core;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	public static String config(String key) throws Exception {
		String value = null;
		 Properties p = new Properties();
		    p.load(new FileInputStream("resources\\config.properties"));
		    for(int i=0;i<=p.size();i++) {
		     value = p.getProperty(key);
		    }
		    return value;
	}
}
