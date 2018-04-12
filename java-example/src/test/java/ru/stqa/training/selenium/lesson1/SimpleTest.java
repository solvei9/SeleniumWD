package ru.stqa.training.selenium.lesson1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SimpleTest {
    private WebDriver driver;

    @Before
    public void start() {
        //System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
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
