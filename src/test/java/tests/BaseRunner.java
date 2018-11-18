package tests;

import app.Application;
import org.junit.After;
import org.junit.Before;

public class BaseRunner {
    public Application app;

    @Before
    public void start() {
        app = new Application();
    }

    @After
    public void tearDown() {
        app.quit();
    }


}