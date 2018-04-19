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
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
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

    // Определение существования элемента
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void mostPopularTest() {
        /*WebElement menu = driver.findElement(By.cssSelector("ul#box-apps-menu li:nth-child(" + i + ")"));
        menu.click();
        assertTrue(isElementPresent(By.cssSelector("h1")));

        // Проверяем элементы второго уровня
        menu = driver.findElement(By.cssSelector("ul#box-apps-menu li:nth-child(" + i + ")"));
        List<WebElement> menuItems = menu.findElements(By.cssSelector("ul.docs li"));
        for (int j = 0; j < menuItems.size(); j++) {
            menuItems.get(j).click();
            assertTrue(isElementPresent(By.cssSelector("h1")));
            menu = driver.findElement(By.cssSelector("ul#box-apps-menu li:nth-child(" + i + ")"));
            menuItems = menu.findElements(By.cssSelector("ul.docs li"));
        }*/
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
