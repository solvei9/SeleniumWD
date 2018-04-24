package ru.stqa.training.selenium.lesson5;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.Rule;
import static org.hamcrest.core.Is.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task09 {
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
        // Переход к странам
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));
    }

    private boolean isSortedAlphabetically(List<WebElement> list, By locator, String attr) {
        boolean sort = true;
        int count = list.size();
        for (int i = 0; i < count - 1; i++) {
            String name1 = list.get(i).findElement(locator).getAttribute(attr);
            String name2 = list.get(i + 1).findElement(locator).getAttribute(attr);
            if ((name1.compareTo(name2)) > 0) {
                sort = false;
            }
        }
        return sort;
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void countryTest() {
        // Сортировка стран
        List<WebElement> countries = driver.findElements(By.cssSelector("tr.row"));
        collector.checkThat(isSortedAlphabetically(countries, By.cssSelector("a"), "textContent"), is(true));

        // Сортировка географических зон
        countries = driver.findElements(By.cssSelector("tr.row"));
        for (int i = 0; i < countries.size()-1; i++){
            List<WebElement> list = countries.get(i).findElements(By.cssSelector("td"));
            int geozoneCount = Integer.parseInt(list.get(5).getAttribute("textContent"));
            String country = list.get(4).getAttribute("textContent");
            if (geozoneCount>0) {
                countries.get(i).findElement(By.cssSelector("a")).click();
                wait.until(titleIs("Edit Country | My Store"));
                List<WebElement> geozoneList = driver.findElements(By.cssSelector("table#table-zones tr"));
                geozoneList.remove(0);
                geozoneList.remove(geozoneList.size()-1);
                collector.checkThat("Страна: " + country, isSortedAlphabetically(geozoneList, By.cssSelector("input[name*='name']"), "value"), is(true));
            }
            driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            wait.until(titleIs("Countries | My Store"));
            countries = driver.findElements(By.cssSelector("tr.row"));
        }

        // Сортировка на странице редактирования географических зон
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        countries = driver.findElements(By.cssSelector("tr.row"));
        for (int i = 0; i < countries.size(); i++){
            List<WebElement> list = countries.get(i).findElements(By.cssSelector("td"));
            String country = list.get(2).getAttribute("textContent");
            countries.get(i).findElement(By.cssSelector("a")).click();
            wait.until(titleIs("Edit Geo Zone | My Store"));
            List<WebElement> geozoneList = driver.findElements(By.cssSelector("table#table-zones tr"));
            geozoneList.remove(0);
            geozoneList.remove(geozoneList.size()-1);
            collector.checkThat("Страна: " + country, isSortedAlphabetically(geozoneList, By.cssSelector("select[name*='zone_code'] option[selected='selected']"), "textContent"), is(true));
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
            countries = driver.findElements(By.cssSelector("tr.row"));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}