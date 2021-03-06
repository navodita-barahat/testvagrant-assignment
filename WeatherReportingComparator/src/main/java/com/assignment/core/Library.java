package com.assignment.core;

import java.util.logging.Logger;

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
	
	APIRequests request = new APIRequests(driver);
	
	 protected Logger logger = Logger.getLogger(Library.class.getName());

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
	
	//This method converts temperature received from API in kelvin to degree celsius
	public double kelvinToCelsiusCoverter(int rowIndex) throws NumberFormatException, Exception {
		//°C + 273.15 = 306.15K
		double kelvin = Double.parseDouble(request.postRequest(rowIndex));
		double celsius = kelvin - 273.15;
		System.out.println(celsius);
		return celsius;
	}
	
 }
