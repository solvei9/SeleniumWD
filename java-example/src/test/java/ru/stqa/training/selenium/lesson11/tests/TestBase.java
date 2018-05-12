package ru.stqa.training.selenium.lesson11.tests;

import org.junit.After;
import ru.stqa.training.selenium.lesson11.application.Application;

public class TestBase {
    public Application application = new Application();

    @After
    public void tearDown() {
        application.quit();
        application = null;
    }
}