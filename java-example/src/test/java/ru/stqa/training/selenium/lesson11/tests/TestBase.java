package ru.stqa.training.selenium.lesson11.tests;

import org.junit.After;
import ru.stqa.training.selenium.lesson11.app.Application;

public class TestBase {
    public Application app = new Application();

    @After
    public void tearDown() {
        app.quit();
        app = null;
    }
}