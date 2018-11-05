import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BaseRunner {
    WebDriver driver;
    String baseUrl;

    @Before
    public void setUp() {
        driver = getDriver();
        driver.manage().window().maximize();
        baseUrl = "https://moscow-job.tinkoff.ru/";
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private WebDriver getDriver() {
        String browserName;
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