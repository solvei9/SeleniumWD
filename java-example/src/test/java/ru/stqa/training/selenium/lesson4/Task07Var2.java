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

public class Task07Var2 {
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
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void testProcedure(int i) {
        // Проверяем элемент верхнего уровня
        WebElement menu = driver.findElement(By.cssSelector("ul#box-apps-menu li:nth-child(" + i + ")"));
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
        }
    }

    @Test
    public void test1() {
        testProcedure(1);
    }

    @Test
    public void test2() {
        testProcedure(2);
    }

    @Test
    public void test3() {
        testProcedure(3);
    }

    @Test
    public void test4() {
        testProcedure(4);
    }

    @Test
    public void test5() {
        testProcedure(5);
    }

    @Test
    public void test6() {
        testProcedure(6);
    }

    @Test
    public void test7() {
        testProcedure(7);
    }

    @Test
    public void test8() {
        testProcedure(8);
    }

    @Test
    public void test9() {
        testProcedure(9);
    }

    @Test
    public void test10() {
        testProcedure(10);
    }

    @Test
    public void test11() {
        testProcedure(11);
    }

    @Test
    public void test12() {
        testProcedure(12);
    }

    @Test
    public void test13() {
        testProcedure(13);
    }

    @Test
    public void test14() {
        testProcedure(14);
    }

    @Test
    public void test15() {
        testProcedure(15);
    }

    @Test
    public void test16() {
        testProcedure(16);
    }

    @Test
    public void test17() {
        testProcedure(17);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
