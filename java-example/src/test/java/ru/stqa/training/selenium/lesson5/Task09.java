package ru.stqa.training.selenium.lesson5;

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

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task09 {
    private WebDriver driver;

    @Before
    public void start() {
        // System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 2);
        // Открытие страницы
        driver.get("http://localhost/litecart/admin");
        // Вход под пользователем admin
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
        // Переход к странам
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));
    }

    @Test
    public void boxTest() {
        boolean sort = true;
        List<WebElement> countries = driver.findElements(By.cssSelector("tr.row"));
        int count = countries.size();
        for (int i=0; i<count-1; i++){
            String name1 = countries.get(i).findElement(By.cssSelector("a")).getAttribute("textContent");
            String name2 = countries.get(i+1).findElement(By.cssSelector("a")).getAttribute("textContent");
            if ((name1.compareTo(name2))>0) {
                sort = false;
            }
        }
        assertTrue(sort);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}