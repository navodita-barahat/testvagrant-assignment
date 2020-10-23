package com.assignment.core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


public class Library extends DriverInit {
	
	public Library(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

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
	
	//open a new tab
	public void openNewTab() throws AWTException {
		Robot robot = new Robot();                          
		robot.keyPress(KeyEvent.VK_CONTROL); 
		robot.keyPress(KeyEvent.VK_T); 
		robot.keyRelease(KeyEvent.VK_CONTROL); 
		robot.keyRelease(KeyEvent.VK_T);
		log("Opened a new tab");
	}
	
	//switch to another tab
	public void switchTab(int index) {
		//Switch focus to new tab
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(index));
		log("Switched to tab with index " + index);
		driver.navigate().refresh();
		log("Page refreshed");
	}
			
	//select from a drop down
	public void selectFromDropDown(WebElement element, String option) {
		Select select = new Select(element);
    	select.selectByValue(option);
	}
			
	public void pageRefresh() {
		driver.navigate().refresh();
	}
	
	//scroll page
	public void pageScroll(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//This will scroll the page till the element is found		
        js.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	//mouse hover
	public void museHover(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}
	
	//get date
	public String getDate(int days) {
		 String s;
		 Date date;
		 Format formatter;
		 Calendar calendar = Calendar.getInstance();

		 calendar.add(Calendar.DATE, days);
		 date = calendar.getTime();
		 formatter = new SimpleDateFormat("dd/MM/yyyy");
		 s = formatter.format(date);
		 return s;
	}
	
	//genearte rondom string
	public String generateRandomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
	    return generatedString;
	}
	
	//generate random number of type string 
	public String generateRandomNumber() {
		String generatedString = RandomStringUtils.randomNumeric(10);
	    return generatedString;
	}
	
	
 }
