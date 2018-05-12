package ru.stqa.training.selenium.lesson11.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CartPage extends Page {
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(){
        cart.click();
        wait.until(titleIs("Checkout | My Store"));
    }

    public void deleteProducts() {
        while (cartProductList.size()>0) {
            removeButton.click();
            wait.until(stalenessOf(cartProductList.get(0)));
        }
    }

    @FindBy(css="button[name='remove_cart_item']")
    public WebElement removeButton;

    @FindBy(css="td.item")
    public List<WebElement> cartProductList;
}