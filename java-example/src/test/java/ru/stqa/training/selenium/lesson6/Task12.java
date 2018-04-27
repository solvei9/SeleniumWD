package ru.stqa.training.selenium.lesson6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task12 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        // System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 2);
        // Открытие страницы
        driver.get("http://localhost/litecart/admin");
        // Вход под пользователем admin
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
        // Переход к товарам
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        wait.until(titleIs("Catalog | My Store"));
    }

    @Test
    public void addNewGoodTest() {
        // Переход на страницу регистрации
        driver.findElement(By.cssSelector("a.button[href*=edit_product]")).click();
        wait.until(titleIs("Add New Product | My Store"));

        // Заполнение закладки General

        // Переход на закладку Information
        driver.findElement(By.cssSelector("ul.index a[href='#tab-information']")).click();

        // Переход на закладку Prices
        driver.findElement(By.cssSelector("ul.index a[href='#tab-зrices']")).click();

        // Сохранение товара
        driver.findElement(By.cssSelector("button[name=save]")).click();

        // Проверка в каталоге товаров
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
