package ru.stqa.training.selenium.lesson5;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.Rule;
import static org.hamcrest.core.Is.is;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task10 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        // System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        // System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 2);
        // Открытие страницы
        driver.get("http://localhost/litecart");
        wait.until(titleIs("Online Store | My Store"));
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();

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
        WebElement regularPrice = prices.findElement(By.className("regular-price"));
        WebElement campaignPrice = prices.findElement(By.className("campaign-price"));
        // Обычная цена перечеркнута
        collector.checkThat(regularPrice.getTagName(), is("s"));
        // Обычная цена серая
        collector.checkThat(isGrey(regularPrice.getCssValue("color")), is(true));
        // Акционная цена жирная
        collector.checkThat(campaignPrice.getTagName(), is("strong"));
        // Акционная цена красная
        collector.checkThat(isRed(campaignPrice.getCssValue("color")), is(true));
        // Акционная цена крупнее, чем обычная
        collector.checkThat(isGreater(campaignPrice.getSize(), regularPrice.getSize()), is(true));
    }

    private boolean isRed (String color) {
        String colorSet = color.substring(color.indexOf("(")+1, color.indexOf(")"));
        colorSet = colorSet.substring(colorSet.indexOf(",")+2);
        String green = colorSet.substring(0, colorSet.indexOf(","));
        colorSet = colorSet.substring(colorSet.indexOf(",")+2);

        // Ветвление для firefox
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        String blue;
        if (browserName.equals("firefox")) {
            blue = colorSet;
        }
        else {
            blue = colorSet.substring(0, colorSet.indexOf(","));
        }
        return ((green.equals("0"))&&(blue.equals("0")));
    }

    private boolean isGrey (String color) {
        String colorSet = color.substring(color.indexOf("(")+1, color.indexOf(")"));
        String red = colorSet.substring(0, colorSet.indexOf(","));
        colorSet = colorSet.substring(colorSet.indexOf(",")+2);
        String green = colorSet.substring(0, colorSet.indexOf(","));
        colorSet = colorSet.substring(colorSet.indexOf(",")+2);

        // Ветвление для firefox
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        String blue;
        if (browserName.equals("firefox")) {
            blue = colorSet;
        }
        else {
            blue = colorSet.substring(0, colorSet.indexOf(","));
        }
        return ((red.equals(green))&&(green.equals(blue)));
    }

    private boolean isGreater (Dimension size1, Dimension size2) {
        return ((size1.width>size2.width)&&(size1.height>size2.height));
    }

    private void checkEquals(WebElement product, String productName, String regularPrice, String campaignPrice) {
        // Название товара совпадает
        collector.checkThat(product.findElement(By.cssSelector("h1.title")).getAttribute("textContent"), is(productName));
        // Обычная цена товара совпадает
        collector.checkThat(product.findElement(By.cssSelector("div.price-wrapper s.regular-price")).getAttribute("textContent"), is(regularPrice));
        // Акционная цена товара совпадает
        collector.checkThat(product.findElement(By.cssSelector("div.price-wrapper strong.campaign-price")).getAttribute("textContent"), is(campaignPrice));
    }
}
