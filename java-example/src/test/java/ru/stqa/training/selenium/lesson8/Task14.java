package ru.stqa.training.selenium.lesson8;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task14 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        // System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        // System.setProperty("webdriver.chrome.driver", "C:/Tools/chromedriver.exe"); // Windows
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
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

    @Test
    public void addNewProductTest() {
        // Открытие страницы добавления страны
        driver.findElement(By.cssSelector("a.button")).click();
        wait.until(titleIs("Add New Country | My Store"));
        // Получение списка внешних ссылок
        List<WebElement> externalLinks = driver.findElements(By.cssSelector("i.fa-external-link"));
        // Открытие страницы для каждой внешней ссылки
        for (WebElement externalLink : externalLinks) {
            checkExternalLink(externalLink);
        }
    }

    private void checkExternalLink(WebElement externalLink) {
        // Получение списка окон
        Set<String> existingWindows = driver.getWindowHandles();
        // Получение текущего окна
        String mainWindow = driver.getWindowHandle();
        // Клик по элементу
        externalLink.click();
        // Ожидание нового окна
        String newWindow = wait.until(newWindowAppeared(existingWindows));
        // Переключение на новое окно
        driver.switchTo().window(newWindow);
        // Закрытие нового окна
        driver.close();
        // Переключение на главное окно
        driver.switchTo().window(mainWindow);
    }

    private ExpectedCondition<String> newWindowAppeared(Set<String> existingWindows){
        return driver -> {
            Set<String> newWindows = driver.getWindowHandles();
            newWindows.removeAll(existingWindows);
            if (newWindows.size()>0) {
                return newWindows.iterator().next();
            }
            else {
                return null;
            }
        };
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
