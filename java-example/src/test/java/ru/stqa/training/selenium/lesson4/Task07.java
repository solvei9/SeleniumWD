package ru.stqa.training.selenium.lesson4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.lesson4.TestConsts;

import java.io.File;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task07 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        // System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,2);
        // Открытие страницы
        driver.get("http://localhost/litecart/admin");
        // Вход под пользователем admin
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }

    @Test
    public void appearenceTest() {
        // Проверяем элемент верхнего уровня
        WebElement menu = driver.findElement(By.cssSelector("ul#box-apps-menu li:nth-child(2)"));
        menu.click();
        wait.until(titleIs("Catalog | My Store"));

        // Проверяем элементы второго уровня
        menu = driver.findElement(By.cssSelector("ul#box-apps-menu li:nth-child(2)"));
        List<WebElement> menuItems = menu.findElements(By.cssSelector("ul.docs li"));
        for (int i=1; i<menuItems.size(); i++) {
            String id = menuItems.get(i).getAttribute("id");
            menuItems.get(i).click();
            switch (id) {
                case "doc-catalog": wait.until(titleIs(TestConsts.doc_catalog + " | My Store"));
                default: break;
            }
            menu = driver.findElement(By.cssSelector("ul#box-apps-menu li:nth-child(2)"));
            menuItems = menu.findElements(By.cssSelector("ul.docs li"));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
