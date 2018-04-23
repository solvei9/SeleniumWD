package ru.stqa.training.selenium.lesson5;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task10 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        // System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 2);
        // Открытие страницы
        driver.get("http://localhost/litecart");
        wait.until(titleIs("Online Store | My Store"));
    }

    @Test
    public void productTest() {
        // Проверка цен на главной странице
        WebElement product = driver.findElement(By.cssSelector("div#box-campaigns a.link"));
        checkPrices(product);

        // Сохранение свойств с главной страницы
        String productName = product.findElement(By.cssSelector("div.name")).getAttribute("textContent");
        String regularPrice = product.findElement(By.cssSelector("div.price-wrapper s.regular-price")).getAttribute("textContent");
        String campaignPrice = product.findElement(By.cssSelector("div.price-wrapper strong.campaign-price")).getAttribute("textContent");

        // Переход на страницу продукта
        product.click();
        wait.until(titleIs("Yellow Duck | Subcategory | Rubber Ducks | My Store"));

        // Сверка свойств страницы продукта со свойствами главной страницы
        product = driver.findElement(By.cssSelector("div#box-product"));
        checkEquals(product, productName, regularPrice, campaignPrice);

        // Проверка цен на странице продукта
        checkPrices(product);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private void checkPrices(WebElement product) {
        WebElement prices = product.findElement(By.cssSelector("div.price-wrapper"));
        // Обычная цена перечеркнута
        assertEquals("regular-price", prices.findElement(By.tagName("s")).getAttribute("class"));
        // Обычная цена серая
        assertTrue(isGrey(prices.findElement(By.cssSelector("s.regular-price")).getCssValue("color")));
        // Акционная цена жирная
        assertEquals("campaign-price", prices.findElement(By.tagName("strong")).getAttribute("class"));
        // Акционная цена красная
        assertTrue(isRed(prices.findElement(By.cssSelector("strong.campaign-price")).getCssValue("color")));
        // Акционная цена крупнее, чем обычная
        assertTrue(isGreater(product.findElement(By.cssSelector("div.price-wrapper strong.campaign-price")).getSize(),
                product.findElement(By.cssSelector("div.price-wrapper s.regular-price")).getSize()));
    }

    private boolean isRed (String color) {
        String colorSet = color.substring(color.indexOf("(")+1, color.indexOf(")"));
        colorSet = colorSet.substring(colorSet.indexOf(",")+2);
        String green = colorSet.substring(0, colorSet.indexOf(","));
        colorSet = colorSet.substring(colorSet.indexOf(",")+2);
        String blue = colorSet.substring(0, colorSet.indexOf(","));
        return ((green.equals("0"))&&(blue.equals("0")));
    }

    private boolean isGrey (String color) {
        String colorSet = color.substring(color.indexOf("(")+1, color.indexOf(")"));
        String red = colorSet.substring(0, colorSet.indexOf(","));
        colorSet = colorSet.substring(colorSet.indexOf(",")+2);
        String green = colorSet.substring(0, colorSet.indexOf(","));
        colorSet = colorSet.substring(colorSet.indexOf(",")+2);
        String blue = colorSet.substring(0, colorSet.indexOf(","));
        return ((red.equals(green))&&(green.equals(blue)));
    }

    private boolean isGreater (Dimension size1, Dimension size2) {
        return ((size1.width>size2.width)&&(size1.height>size2.height));
    }

    private void checkEquals(WebElement product, String productName, String regularPrice, String campaignPrice) {
        // Название товара совпадает
        assertEquals(productName, product.findElement(By.cssSelector("h1.title")).getAttribute("textContent"));
        // Обычная цена товара совпадает
        assertEquals(regularPrice, product.findElement(By.cssSelector("div.price-wrapper s.regular-price")).getAttribute("textContent"));
        // Акционная цена товара совпадает
        assertEquals(campaignPrice, product.findElement(By.cssSelector("div.price-wrapper strong.campaign-price")).getAttribute("textContent"));
    }
}
