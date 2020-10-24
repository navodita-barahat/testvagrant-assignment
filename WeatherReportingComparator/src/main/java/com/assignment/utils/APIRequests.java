package com.assignment.utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.assignment.core.ReadConfig;

public class APIRequests {
	
	Util util = new Util();
	
	public String postRequest(int rowIndex) throws Exception {
		String searched_city = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		String app_id = ReadConfig.config("appid");
		Matcher m = null;

		   try {
		    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+searched_city+"&appid="+app_id);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("POST");
		   // conn.setRequestProperty("Accept", ""); // add your content mime type

		    if (conn.getResponseCode() != 200) {
		        throw new RuntimeException("Failed : HTTP error code : "
		                + conn.getResponseCode());
		    }

		    BufferedReader br = new BufferedReader(new InputStreamReader(
		    (conn.getInputStream())));
			FileWriter file = new FileWriter("resources/response.txt");
			String regex = "\"temp\":(\\d[0-9]\\S\\.\\d\\S)";
		    String output;
		    while ((output = br.readLine()) != null) {
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
		APIRequests request = new APIRequests();
		System.out.println(request.postRequest(0));
	}
}
