package ru.stqa.training.selenium.lesson11.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

public class ProductPage extends Page {
    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(WebElement product) {
        product.click();
        wait.until(titleContains("Duck"));
    }

    public void selectSize(String value) {
        try {
            Select select = new Select(size);
            select.selectByVisibleText(value);
        }
        catch (NoSuchElementException e) {}
    }

    public void addProductToCart(String count) {
        addButton.click();
        wait.until(ExpectedConditions.textToBePresentInElement(cartCount, count));
    }

    @FindBy(css="select[name='options[Size]']")
    public WebElement size;

    @FindBy(css="button[name='add_cart_product']")
    public WebElement addButton;

    @FindBy(css="span.quantity")
    public WebElement cartCount;
}
