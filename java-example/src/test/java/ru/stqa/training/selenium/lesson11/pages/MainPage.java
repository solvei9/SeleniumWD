package ru.stqa.training.selenium.lesson11.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MainPage extends Page {
    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(){
        driver.get("http://localhost/litecart");
        wait.until(titleIs("Online Store | My Store"));
    }

    @FindBy(css="li.product")
    public WebElement firstProduct;
}
