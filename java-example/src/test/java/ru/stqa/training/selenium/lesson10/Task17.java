package ru.stqa.training.selenium.lesson10;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task17 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        // System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        // Открытие страницы
        driver.get("http://localhost/litecart/admin");
        // Вход под пользователем admin
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
        // Переход к категориям товаров
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        wait.until(titleIs("Catalog | My Store"));
    }

    @Test
    public void logTest() {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.cssSelector("tr.row a"));
        for (int i = 0; i < list.size(); i++) {
            new Actions(driver)
                    .keyDown(Keys.CONTROL)
                    .keyDown(Keys.SHIFT)
                    .click(list.get(i))
                    .keyUp(Keys.SHIFT)
                    .keyUp(Keys.CONTROL)
                    .perform();
            Assert.assertEquals(driver.manage().logs().get("browser").getAll().size(), 0);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Actions(driver)
                    .sendKeys(Keys.CONTROL + "w")
                    .perform();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
