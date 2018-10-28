import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.util.concurrent.TimeUnit;

public class BaseRunner {
    WebDriver driver;
    String baseUrl;
    String browserName;

    @Before
    public void setUp() {
        driver = getDriver();
        driver.manage().window().maximize();
        baseUrl = "https://moscow-job.tinkoff.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private WebDriver getDriver() {
        try {
            browserName = System.getProperty("browser");
            BrowseFactory.valueOf(browserName);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.setProperty("browser", "chrome");
            browserName = System.getProperty("browser");
        }
        return BrowseFactory.valueOf(browserName).create();
    }
}