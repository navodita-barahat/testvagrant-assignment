package com.assignment.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.assignment.utils.APIRequests;


public class Library extends DriverInit {
	
	public Library(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	APIRequests request = new APIRequests();

	//open a url
	public void openUrl(String url) throws Exception {
		driver.get(url);		
		Reporter.log("Open Url"+ url, true);
	}
	
	//explicit wait
	public WebDriverWait waitMethod() {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		return wait;
	}
	
	//get page title
	public String getTitle() {
		String title = driver.getTitle();
		Reporter.log("Title is " + title, true);
		return title;
	}
	
	//add console log 
	public void log(String logText) {
		Reporter.log(logText, true);
	}
	
	//mouse hover
	public void museHover(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}
	
	//convert temperature from kelvin to celsius
	public double kelvinToCelsiusCoverter() throws NumberFormatException, Exception {
		//°C + 273.15 = 306.15K
		double kelvin = Double.parseDouble(request.postRequest(0));
		double celsius = kelvin - 273.15;
		System.out.println(celsius);
		return celsius;
	}
	
 }
