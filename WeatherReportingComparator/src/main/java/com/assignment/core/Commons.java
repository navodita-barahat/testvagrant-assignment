package com.assignment.core;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.assignment.utils.Util;

public class Commons extends Library{

	public Commons(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//*[@class='topnav_cont']/a")
    public List<WebElement> navigationItems;
	
	@FindBy(id = "topnav_section")
    public WebElement sections;
	
	@FindBy(xpath = "//*[@id='topnav']/ul/li")
    public List<WebElement> hamburgerMenuItems;
	
	
	
	Util util = new Util();
	
	//select an option from home page like Offers, Pooled Campaign, Reverse Query
	public void homeNavigationOption(int rowIndex) throws IOException {
		String menuItems = (String) util.getCellData("HomeNavigationOptions", "testData.xlsx", "MenuItems", rowIndex); 
		for(WebElement item:navigationItems) {
			if(item.getText().contains(menuItems)) {
				if(item.isEnabled()) {
				item.click();
				break;
				}
			}
		}
	}
		
	//Select a hamburger option 
	public void selectHamburgerOption(int rowIndex) throws IOException {
		sections.click();
		String menuItems = (String) util.getCellData("hamburgerItem", "testData.xlsx", "HamburgerItems", rowIndex); 
		for(WebElement item:hamburgerMenuItems) {
			if(item.getText().contains(menuItems)) {
				if(item.isEnabled()) {
				item.click();
				break;
				}
			}
		}
	}

}
