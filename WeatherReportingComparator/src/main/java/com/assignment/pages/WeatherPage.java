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
	
	public void searchForCity(int rowIndex) throws IOException {
		String cityToSearch = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		citySearchBox.sendKeys(cityToSearch);
		try {
			if(cityCheckBox.isDisplayed() && cityCheckBox.isEnabled())
				cityCheckBox.click();
			
		} catch (Exception e) {
			System.out.println("City is not displayed");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	public void fetchWeather(int homeNavigationItems_rowIndex,int selectHamburgerOption_rowIndex,int citySearchBox_rowIndex) throws Exception {
		common.GetInstance(Commons.class).homeNavigationItems(homeNavigationItems_rowIndex);
		common.GetInstance(Commons.class).selectHamburgerOption(selectHamburgerOption_rowIndex);
		searchForCity(citySearchBox_rowIndex);
	
	}
	
	public boolean checkIfSearchedCityIsDisplayedOnMap(int rowIndex) throws IOException {
		String searched_city = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		boolean result = false;
		for(WebElement city:cityOnMap) {
			String cityText = city.getText();
			if(cityText.equalsIgnoreCase(searched_city)) {
				result = true;
			break;
			}
		}
		return result;
	}

	public boolean checkIfWeatherInfoIsDisplayed(int rowIndex) throws IOException {
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
	
	public int getTemperatureInDegreeFromWeatherInfo(int rowIndex) throws IOException {
		String searched_city = (String) util.getCellData("cityName", "testData.xlsx", "Weatherpage", rowIndex); 
		Actions action = new Actions(driver);
		boolean result = false;
		int count = 1;
		int temperatureInDegree = 0;
		for(WebElement city:cityOnMap) {
			String cityText = city.getText();
			if(cityText.equalsIgnoreCase(searched_city)) {
				action.moveToElement(city).click().build().perform();
					for(WebElement child:weatherInfo) {
						if(child.getText().contains("Temp in Degrees:")) {
							WebElement temperatureText = driver.findElement(By.xpath("(//*[@class='leaflet-pane leaflet-popup-pane']//parent::*//b)["+count+"]"));
							String text = temperatureText.getText();
							temperatureInDegree = Integer.valueOf(text.substring(17));//returns temperature in degrees
							System.out.println("");
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
	
	
	
}
