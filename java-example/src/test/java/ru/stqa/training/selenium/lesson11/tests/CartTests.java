package ru.stqa.training.selenium.lesson11.tests;

import org.junit.Test;

public class CartTests extends TestBase {

    @Test
    public void addAndRemoveProductsTest() {
        app.addProductsToCart(3);
        app.deleteProductsFromCart();
    }
}