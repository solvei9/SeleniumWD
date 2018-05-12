package ru.stqa.training.selenium.lesson11.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.training.selenium.lesson11.pages.CartPage;
import ru.stqa.training.selenium.lesson11.pages.MainPage;
import ru.stqa.training.selenium.lesson11.pages.ProductPage;

public class Application {

    private WebDriver driver;

    private MainPage mainPage;
    private ProductPage productPage;
    private CartPage cartPage;

    public Application() {
        System.setProperty("webdriver.chrome.driver", "/home/serene/Downloads/chromedriver"); // Ubuntu
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    public void quit() {
        driver.quit();
        driver = null;
    }

    public void addProductsToCart(int productsCount) {
        for (int i = 1; i < productsCount+1; i++) {
            mainPage.open();
            productPage.open(mainPage.firstProduct);
            productPage.selectSize("Small");
            productPage.addProductToCart("" + i);
        }
    }

    public void deleteProductsFromCart() {
        cartPage.open();
        cartPage.deleteProducts();
    }
}
