package com.assignment.utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

import com.assignment.core.Library;
import com.assignment.core.ReadConfig;
import com.aventstack.extentreports.model.Log;

public class APIRequests extends Library {
	
	public APIRequests(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	Util util = new Util();
	
	public String postRequest(int rowIndex) throws Exception {
		//get name of the city from testData file
		String searched_city = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		//get app id from config.properties
		String app_id = ReadConfig.config("appid");
		Matcher m = null;
		   try {
		    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+searched_city+"&appid="+app_id);				
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("POST");
		    //validate response code is 200
		    if (conn.getResponseCode() != 200) {
		        throw new RuntimeException("Failed : HTTP error code : "
		                + conn.getResponseCode());
		    }
		    
		    //read the response
		    BufferedReader br = new BufferedReader(new InputStreamReader(
		    (conn.getInputStream())));
			FileWriter file = new FileWriter("resources/response.txt");
			String regex = "\"temp\":(\\d[0-9]\\S\\.\\d\\S)";
		    String output;
		    while ((output = br.readLine()) != null) {
		    	//extract temperature from response using regular expression regex for temp key
		        Pattern p = Pattern.compile(regex);
		        m = p.matcher(output);
		        if (m.find()) {
		            System.out.println(m.group(1));
		        }
		        System.out.println(output);
			    try {
					file.write(output);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    finally {
			    	 
		            try {
		                file.flush();
		                file.close();
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		        }
		    }
		    
	    conn.disconnect();

		  } catch (Exception e) {

		    e.printStackTrace();

		  } 
		   //return value of temperature is in kelvin
		   return m.group(1);
	}
	
	public static void main(String args[]) throws Exception {
		APIRequests request = new APIRequests(driver);
		System.out.println(request.postRequest(0));
	}
}
