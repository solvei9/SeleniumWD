package ru.stqa.training.selenium.lesson1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.*;

import java.io.File;

public class SimpleTest {
    private WebDriver driver;

    @Before
    public void start() {
        //System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        //System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        //driver = new ChromeDriver();
        //driver = new InternetExplorerDriver();
        //DesiredCapabilities caps = new DesiredCapabilities();
        //caps.setCapability(FirefoxDriver.MARIONETTE, true);
        //driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\Program Files\\Firefox Nightly\\firefox.exe")), new FirefoxProfile(), caps);
        driver = new FirefoxDriver();
    }

    @Test
    public void FirstSimpleTest() {
        // Открытие страницы
        driver.get("http://www.google.com/");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
