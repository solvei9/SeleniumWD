package ru.stqa.training.selenium.lesson6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task11 {
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
    public void registrationTest() {
        // Переход на страницу регистрации
        driver.findElement(By.cssSelector("form[name=login_form] a")).click();
        wait.until(titleIs("Create Account | My Store"));
        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys("Ivanov");
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys("Ivan");
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys("DefaultCity");
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys("DefaultCity");
        Select country = new Select(driver.findElement(By.cssSelector("span.select2-selection__rendered")));
        country.selectByVisibleText("United States");
        Select state = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        state.selectByVisibleText("California");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
