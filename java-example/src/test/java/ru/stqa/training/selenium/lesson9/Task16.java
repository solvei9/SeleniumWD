package ru.stqa.training.selenium.lesson9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Task16 {
    private WebDriver driver;
    public static final String USERNAME = "catherine83";
    public static final String AUTOMATE_KEY = "Pwis15yeoC1anQ6TTqxx";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    @Before
    public void start() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "firefox");
        caps.setCapability("os_version", "Win8");
        driver = new RemoteWebDriver(new URL(URL), caps);
    }

    @Test
    public void FirstSimpleTest() {
        // Открытие страницы
        driver.get("http://www.google.com/");
        WebElement element = driver.findElement(By.name("q"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
