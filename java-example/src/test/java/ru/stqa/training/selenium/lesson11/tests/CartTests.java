package ru.stqa.training.selenium.lesson11.tests;

import org.junit.Test;

public class CartTests extends TestBase {

    @Test
    public void addAndRemoveProductsTest() {
        application.addProductsToCart(3);
        application.deleteProductsFromCart();
    }
}