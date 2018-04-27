package ru.stqa.training.selenium.lesson6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task11 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        // System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        // System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);
        // Открытие страницы
        driver.get("http://localhost/litecart");
        wait.until(titleIs("Online Store | My Store"));
    }

    @Test
    public void registrationTest() {
        // Переход на страницу регистрации
        driver.findElement(By.cssSelector("form[name=login_form] a")).click();
        wait.until(titleIs("Create Account | My Store"));
        // Заполнение полей
        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys("Ivanov");
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys("Ivan");
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys("DefaultCity");
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys("DefaultCity");
        driver.findElement(By.cssSelector("span.select2-selection__arrow")).click();
        driver.findElement(By.className("select2-search__field")).sendKeys("United States");
        driver.findElement(By.className("select2-search__field")).sendKeys(Keys.ENTER);
        Select state = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        state.selectByVisibleText("California");
        String email = "n" + ((new Date()).getTime())%1000000 + "@mail.com";
        System.out.println("email: "+ email);
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys(Keys.HOME);
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+1 999 123 4567");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys("password123");
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys("password123");
        // Регистрация
        driver.findElement(By.cssSelector("button[name=create_account]")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("div.success")));

        // Выход из аккаунта
        driver.findElement(By.cssSelector("div#box-account li:nth-child(4) a")).click();

        // Повторный вход
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys("password123");
        driver.findElement(By.cssSelector("button[name=login]")).click();

        // Выход из аккаунта
        driver.findElement(By.cssSelector("div#box-account li:nth-child(4) a")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
