package com.assignment.base;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.assignment.core.DriverInit;
import com.assignment.core.ReadConfig;
import com.assignment.utils.Util;


public class BaseTest {

	protected WebDriver driver;
	Util util = new Util();
	ReadConfig config = new ReadConfig();

	@BeforeClass()
	public void startReport() throws Exception {
		DriverInit.openBrowser();
	}
	 
	@AfterClass(alwaysRun = true)
	public void endReport() {
		if(driver!=null)
			DriverInit.driver.quit();
	}
	
}
