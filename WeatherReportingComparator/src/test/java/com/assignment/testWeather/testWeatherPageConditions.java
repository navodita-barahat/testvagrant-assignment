package com.assignment.testWeather;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.assignment.base.BaseTest;
import com.assignment.core.Commons;
import com.assignment.core.Library;
import com.assignment.core.ReadConfig;
import com.assignment.pages.WeatherPage;
import com.assignment.utils.APIRequests;


public class testWeatherPageConditions extends BaseTest {
	
	Commons common = new Commons(driver);
	WeatherPage weather = new WeatherPage(driver);
	APIRequests request = new APIRequests(driver);
	Library lib = new Library(driver);
	
	//Phase 1 test
	@Test
	public void testForWeatherPage() throws IOException, Exception {
		//go to menu option India
		common.GetInstance(Commons.class).homeNavigationOption(0);
		//select option weather from the hamburger on India page
		common.GetInstance(Commons.class).selectHamburgerOption(0);
		//search for city to view weather details
		weather.GetInstance(WeatherPage.class).searchForCity(0);
		//verify that the searched city is displayed on the map 
		weather.GetInstance(WeatherPage.class).checkIfSearchedCityIsDisplayedOnMap(0);
		//verify when user clicks on a particular city then weather information pop up is displayed
		weather.GetInstance(WeatherPage.class).checkIfWeatherInfoIsDisplayed(0);
	}
	
	//Phase 2 and Phase 3 test
	@Test
	public void getTemperatureOfCityViaAPIAndCompareWithUI() throws Exception {
		//get the temperature from weather API
		Double temperature_API = lib.GetInstance(Library.class).kelvinToCelsiusCoverter(0);
		Double temperature_UI =  weather.GetInstance(WeatherPage.class).getTemperatureInDegreeFromWeatherInfo(0);
		Assert.assertEquals(temperature_UI, temperature_API, ReadConfig.config("delta"));
	}
	
	//Phase 3 test
	@Test
	public void compareTemperatureFromAPIAndApplicatio() throws IOException, Exception {
		/*
		 * check if temperature returned by API matches the Temperature displayed in
		 * application any difference in temperature should fall with max allowed
		 * deviated
		 */
		String status = weather.GetInstance(WeatherPage.class).comapareAPIAndApplicationTemperature(0);
		//add assertion to check if temperature matches or is within max deviation specified
		Assert.assertEquals("success",status);
	}

}
