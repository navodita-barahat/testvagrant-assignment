package com.assignment.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.assignment.core.Commons;
import com.assignment.core.Library;
import com.assignment.core.ReadConfig;
import com.assignment.utils.Util;

public class WeatherPage extends Library {

	public WeatherPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "searchBox")
    public WebElement citySearchBox;
	
	@FindBy(xpath = "//*[@id='messages']/parent::*//input[@type='checkbox']")
    public WebElement cityCheckBox;
	
	@FindBy(className = "cityText")
    public List<WebElement> cityOnMap;
	
	@FindBy(xpath = "//*[@class='leaflet-pane leaflet-popup-pane']//following-sibling::*")
    public List<WebElement> weatherInfoPopUp;
	
	@FindBy(xpath = "//*[@class='leaflet-pane leaflet-popup-pane']//parent::*//b")
    public List<WebElement> weatherInfo;
	
	
	Commons common = new Commons(driver);
	Util util = new Util();
	
	//This method will search for a city obtained from testDataFile and check if city is displayed
	public void searchForCity(int rowIndex) throws IOException {
		logger.info("Get the name of city to search from testData file");
		String cityToSearch = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		logger.info("Enter city name in search box");
		citySearchBox.sendKeys(cityToSearch);
		logger.info("check if the entered city name is displayed in Searchable drop down");
		try {
			if(cityCheckBox.isDisplayed() && cityCheckBox.isEnabled())
				cityCheckBox.click();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed : Could not find searched city name");
			// TODO Auto-generated catch block
		}
	}
	
	//This methos will verify whether the city searched for is displayed on map
	public boolean checkIfSearchedCityIsDisplayedOnMap(int rowIndex) throws IOException {
		logger.info("Get the name of city to check on map from testData file");
		String searched_city = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		boolean result = false;
		for(WebElement city:cityOnMap) {
			logger.info("Get the name of city from map");
			String cityText = city.getText();
			if(cityText.equalsIgnoreCase(searched_city)) {
				if(city.isDisplayed()) {
				result = true;
				break;
				}
			}
		}
		return result;
	}

	//This method will check if the weather infomation pop up is displayed when clicked on the city
	public boolean checkIfWeatherInfoIsDisplayed(int rowIndex) throws IOException {
		logger.info("Get the name of city to check weather information from testData file");
		String searched_city = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		Actions action = new Actions(driver);
		boolean result = false;
		for(WebElement city:cityOnMap) {
			String cityText = city.getText();
			if(cityText.equalsIgnoreCase(searched_city)) {
				action.moveToElement(city).click().build().perform();
				int childSize = weatherInfoPopUp.size();
				if(childSize > 1) {
					for(WebElement child:weatherInfoPopUp) {
						if(child.getText().contains("Temp in Fahrenheit:")) {
							System.out.println("Weather Information is displayed");
							result=true;
						break;
						}
					}
				}			
			}
			if(result)
				break;
		}
		return result;
	}
	
	//This method will extract the temperature in degree from UI
	public Double getTemperatureInDegreeFromWeatherInfo(int rowIndex) throws IOException {
		logger.info("Get the temperature in degrees from weather information");
		String searched_city = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		Actions action = new Actions(driver);
		boolean result = false;
		int count = 1;
		Double temperatureInDegree = null;
		for(WebElement city:cityOnMap) {
			String cityText = city.getText();
			if(cityText.equalsIgnoreCase(searched_city)) {
				action.moveToElement(city).click().build().perform();
					for(WebElement child:weatherInfo) {
						if(child.getText().contains("Temp in Degrees:")) {
							WebElement temperatureText = driver.findElement(By.xpath("(//*[@class='leaflet-pane leaflet-popup-pane']//parent::*//b)["+count+"]"));
							String text = temperatureText.getText();
							temperatureInDegree = Double.valueOf(text.substring(17));//returns temperature in degrees
							System.out.println(temperatureInDegree);
							result=true;
						break;
						}
						count+=1;
					}
			}
			if(result)
				break;
		}
		return temperatureInDegree;
	}
	
	//This method compares temperature obtained via API and from Application UI and throws runtime error if difference > delta(maximum deviation allowed)
	public String comapareAPIAndApplicationTemperature(int rowIndex) throws NumberFormatException, Exception {
		Double allowed_deviation = Double.parseDouble(ReadConfig.config("delta"));
		Double temperatureFromApplication = getTemperatureInDegreeFromWeatherInfo(rowIndex);
		Double temperatureFromAPI = kelvinToCelsiusCoverter(rowIndex);
		String status;
		//check the difference between temperatureFromApplication and temperatureFromAPI 
		//should not be greater than the allowed deviation mentioned in config.properties
		logger.info("Find difference between temperature displayed on application vs temperature from API");
		Double deviation = temperatureFromApplication - temperatureFromAPI;
		logger.info("Check that deviation should not be grater than maximum allowed deviation mentioned in config file");
		if(deviation>allowed_deviation)
			status = "success";
		else
			try {
				status = "fail";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("Failed : Temperature deviation is greater than" + allowed_deviation);
			}
		return status;
	}
	
}
