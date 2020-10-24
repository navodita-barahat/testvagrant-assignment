package com.assignment.core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverInit {

	public static WebDriver driver;
	
	public DriverInit(WebDriver driver) {
		DriverInit.driver = driver;
	}
	
	public static void openBrowser() throws Exception {
		
		String browser = ReadConfig.config("browser");
		
		if(browser.equalsIgnoreCase("chrome")) {		
			WebDriverManager.chromedriver().version("84.0.4147.89").setup();
			DesiredCapabilities handlSSLErr = DesiredCapabilities.chrome();       
			handlSSLErr.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}		
		else if(browser.equalsIgnoreCase("InternetExplorer")) {
			 WebDriverManager.iedriver().setup();
			 driver = new InternetExplorerDriver();
		}		
		driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
	    driver.manage().window().maximize();
	    //open url passed in config file
        driver.get(ReadConfig.config("url"));		
   	}
	
	//JAVA Generics to Create and return a New Page
    public <TPage extends DriverInit> TPage GetInstance (Class<TPage> pageClass) throws Exception {
        try {
            //Initialize the Page with its elements and return it.
            return PageFactory.initElements(driver,  pageClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}   