package ru.stqa.training.selenium.lesson6;

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

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task12 {
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
    public void addNewProductTest() {
        // Переход на страницу создания товара
        driver.findElement(By.cssSelector("a.button[href*=edit_product]")).click();
        wait.until(titleIs("Add New Product | My Store"));

        // Ввод данных на закладке General
        driver.findElement(By.xpath("//*[contains(text(),' Enabled')]")).click();
        String name = "OneMoreDuck" + new Random().nextInt();
        driver.findElement(By.name("name[en]")).sendKeys(name);
        driver.findElement(By.name("code")).sendKeys("Code" + new Random().nextInt());
        driver.findElement(By.cssSelector("input[data-name='Rubber Ducks']")).click();
        WebElement female = driver.findElement(By.xpath("//*[contains(text(),'Female')]/parent::tr"));
        female.findElement(By.cssSelector("input")).click();
        driver.findElement(By.cssSelector("input[name='quantity']")).clear();
        driver.findElement(By.cssSelector("input[name='quantity']")).sendKeys("5");
        try {
            String imageFile = getClass().getClassLoader().getResource("one-more-duck.jpg").getFile();
            driver.findElement(By.cssSelector("input[name='new_images[]']")).sendKeys(imageFile);
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        driver.findElement(By.cssSelector("input[name='date_valid_from']")).sendKeys("04/27/2018");
        driver.findElement(By.cssSelector("input[name='date_valid_to']")).sendKeys("04/27/2019");

        // Переход на закладку Information
        driver.findElement(By.cssSelector("ul.index a[href='#tab-information']")).click();
        wait.until(ExpectedConditions.attributeContains(By.xpath("//a[@href='#tab-information']/parent::li"),
                "class", "active"));
        // Ввод данных на закладке Information
        Select manufacturer = new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]")));
        manufacturer.selectByIndex(1);
        driver.findElement(By.cssSelector("input[name='keywords']")).sendKeys("OneMoreDuck");
        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys("Short description");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Full description");
        driver.findElement(By.cssSelector("input[name='head_title[en]']")).sendKeys("Head title");
        driver.findElement(By.cssSelector("input[name='meta_description[en]']")).sendKeys("Meta description");

        // Переход на закладку Prices
        driver.findElement(By.cssSelector("ul.index a[href='#tab-prices']")).click();
        wait.until(ExpectedConditions.attributeContains(By.xpath("//a[@href='#tab-prices']/parent::li"),
                "class", "active"));
        // Ввод данных на закладке Prices
        driver.findElement(By.cssSelector("input[name='purchase_price'")).clear();
        driver.findElement(By.cssSelector("input[name='purchase_price'")).sendKeys("2");
        Select currency = new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
        currency.selectByVisibleText("Euros");
        driver.findElement(By.cssSelector("input[name='gross_prices[USD]'")).clear();
        driver.findElement(By.cssSelector("input[name='gross_prices[USD]'")).sendKeys("5");
        driver.findElement(By.cssSelector("input[name='gross_prices[EUR]'")).clear();
        driver.findElement(By.cssSelector("input[name='gross_prices[EUR]'")).sendKeys("4");

        // Сохранение товара
        driver.findElement(By.cssSelector("button[name=save]")).click();

        // Проверка в каталоге товаров
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        wait.until(titleIs("Catalog | My Store"));
        wait.until(presenceOfElementLocated(By.xpath("//*[contains(text(),'" + name + "')]")));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
