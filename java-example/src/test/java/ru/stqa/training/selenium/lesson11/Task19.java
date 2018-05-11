package ru.stqa.training.selenium.lesson11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Task19 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        // System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        // System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
    }

    @Test
    public void cartTest() {
        // Добавление товара в корзину
        for (int i = 1; i < 4; i++) {
            addProductToCart(""+i);
        }

        // Переход в корзину
        driver.findElement(By.cssSelector("a[href='http://localhost/litecart/en/checkout']")).click();
        wait.until(titleIs("Checkout | My Store"));

        // Удаление товаров из корзины
        List<WebElement> cart = driver.findElements(By.cssSelector("td.item"));
        while (cart.size()>0) {
            driver.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
            wait.until(stalenessOf(cart.get(0)));
            cart = driver.findElements(By.cssSelector("td.item"));
        }
    }

    private void addProductToCart(String count) {
        driver.get("http://localhost/litecart");
        wait.until(titleIs("Online Store | My Store"));
        driver.findElement(By.cssSelector("li.product")).click();
        wait.until(titleContains("Duck"));
        List<WebElement> sizeSelects = driver.findElements(By.cssSelector("select[name='options[Size]']"));
        if (sizeSelects.size()>0) {
            Select size = new Select(sizeSelects.get(0));
            size.selectByVisibleText("Small");
        }
        WebElement cartCount = driver.findElement(By.cssSelector("span.quantity"));
        driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
        wait.until(ExpectedConditions.textToBePresentInElement(cartCount, count));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
