package com.assignment.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.assignment.core.DriverInit;
import com.assignment.core.ReadConfig;
import com.assignment.utils.Util;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.base.Throwables;

public class BaseTest {

	protected WebDriver driver;
	Util util = new Util();
	ReadConfig config = new ReadConfig();
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	
	@BeforeSuite(alwaysRun = true)
	public void intit() throws Exception {
//	DriverInit.openBrowser();
	System.out.println("in before suite");
	//Copy screenshots 
	util.moveFileFromSrcToDest("ScreenShots", "C:\\Users\\Navodita\\Downloads\\screenshots");
	//copy reports
	util.moveFileFromSrcToDest("reports", "C:\\Users\\Navodita\\Downloads\\Reports");
	//delete reports folder
	util.deleteFolderAtGivenLocation("reports");
	//delete screenshot folder
	util.deleteFolderAtGivenLocation("ScreenShots");
	System.out.println("deleted reports and screenshots successfully");
	util.createFolderAtGivenLocation(System.getProperty("user.dir") + "\\ScreenShots");
	util.createFolderAtGivenLocation(System.getProperty("user.dir") + "\\reports");
	System.out.println("folder created successfully");
	}
	
	
	//@BeforeTest
	@BeforeClass(alwaysRun = true)
	public void startReport() throws Exception {
    DriverInit.openBrowser();
	String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	 htmlReporter = new ExtentHtmlReporter("reports\\" + dateName + ".html");
	 // Create an object of Extent Reports
	 extent = new ExtentReports();  
	 extent.attachReporter(htmlReporter);
	 extent.setSystemInfo("Host Name", "Navodita");
	 extent.setSystemInfo("Environment", "Test");
	 extent.setSystemInfo("User Name", "Navodita");
	 htmlReporter.config().setDocumentTitle("TestReport"); 
	 // Name of the report
	 htmlReporter.config().setReportName("Test Execution Report"); 
	 // Theme
	 htmlReporter.config().setTheme(Theme.STANDARD); 
 
	 }
	
	@BeforeMethod(alwaysRun = true)
	public void setup(Method method) {
	    String testMethodName = method.getName(); //This will be:verifySaveButtonEnabled
	    String descriptiveTestName = method.getAnnotation(Test.class).testName(); //This will be: 'Verify if the save button is enabled'
	    try {
			logger = extent.createTest("Test Name:" + descriptiveTestName + " " + testMethodName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("testname not avaialable");
		}
	}
	
	//This method is to capture the screenshot and return the path of the screenshot.
	public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
		driver = DriverInit.driver;
	 String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	 String destination = null;
	// if(driver!=null) {
	 try {
		TakesScreenshot ts = (TakesScreenshot) driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		 // after execution, you could see a folder "FailedTestsScreenshots" under src folder
		 destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		 if(destination !=null) {
		 File finalDestination = new File(destination);
		 FileUtils.copyFile(source, finalDestination);
		 }
		 
	} catch (WebDriverException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Error capturing screenshot or screenshot is not available");
		
	//}
	 }
	 return destination;
	 }
	
	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) throws Exception{
	
	 if(result.getStatus() == ITestResult.FAILURE){
	 //MarkupHelper is used to display the output in different colors
	 logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
	 logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable().getMessage() + " - Test Case Failed", ExtentColor.RED));
	 logger.log(Status.FAIL, Throwables.getStackTraceAsString(result.getThrowable()));
	 //To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
	 //We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method. 
	 //String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
	 
		String screenshotPath = getScreenShot(driver, result.getName());
		 //To add it in the extent report 
		if(screenshotPath!=null)
		 logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
	 }
	 else if(result.getStatus() == ITestResult.SKIP){
	 logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE)); 
	 //logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable().getStackTrace() + " - Test Skipped", ExtentColor.RED));
	 } 
	 else if(result.getStatus() == ITestResult.SUCCESS)
	 {
	 logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Tests PASSED", ExtentColor.GREEN));
	 }
	 }
	 
	//@AfterTest()
	@AfterClass(alwaysRun = true)
	public void endReport() {
	extent.flush();
	DriverInit.driver.quit();
	 }
	
}
