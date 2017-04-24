package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;
import static org.apache.commons.lang3.StringUtils.join;

public class alterTrip {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8081/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testAlterTrip() throws Exception {
		selenium.open("/trip/8");
		selenium.click("link=Alter this trip");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=name", "TC2");
		selenium.click("css=input.btn.btn-primary");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
