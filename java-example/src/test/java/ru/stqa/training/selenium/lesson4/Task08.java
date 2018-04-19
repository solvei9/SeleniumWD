package ru.stqa.training.selenium.lesson4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task08 {
    private WebDriver driver;

    @Before
    public void start() {
        // System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 2);
        // Открытие страницы
        driver.get("http://localhost/litecart");
        wait.until(titleIs("Online Store | My Store"));
    }

    private void boxTest(By boxLocator) {
        WebElement box = driver.findElement(boxLocator);
        List<WebElement> boxItems = box.findElements(By.cssSelector("ul.listing-wrapper li"));
        for (WebElement item : boxItems) {
            List<WebElement> itemSticks = item.findElements(By.cssSelector("div.sticker"));
            assertEquals(1, itemSticks.size());
        }
    }

    @Test
    public void mostPopularTest() {
        boxTest(By.cssSelector("div#box-most-popular"));
    }

    @Test
    public void campaignsTest() {
        boxTest(By.cssSelector("div#box-campaigns"));
    }

    @Test
    public void latestProductsTest() {
        boxTest(By.cssSelector("div#box-latest-products"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
