package ru.stqa.training.selenium.lesson10;

import org.junit.*;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.core.Is.is;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task17 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

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
        // Переход к категории товаров
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        wait.until(titleIs("Catalog | My Store"));
    }

    @Test
    public void logTest() {
        // Получение списка товаров
        List<WebElement> list = driver.findElements(By.cssSelector("tr.row a[title='Edit']"));
        for (int i = 1; i < list.size(); i++) {
            // Проверка лога браузера при переходе на страницу товара
            checkLink(list.get(i));
        }
    }

    private void checkLink(WebElement link) {
        // Получение списка окон
        Set<String> existingWindows = driver.getWindowHandles();
        // Получение текущего окна
        String mainWindow = driver.getWindowHandle();
        // Клик по элементу
        new Actions(driver)
                .keyDown(Keys.CONTROL)
                .keyDown(Keys.SHIFT)
                .click(link)
                .keyUp(Keys.SHIFT)
                .keyUp(Keys.CONTROL)
                .perform();
        // Ожидание нового окна
        String newWindow = wait.until(newWindowAppeared(existingWindows));
        // Переключение на новое окно
        driver.switchTo().window(newWindow);
        // Проверка лога браузера
        collector.checkThat(driver.manage().logs().get("browser").getAll().size(), is(0));
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
