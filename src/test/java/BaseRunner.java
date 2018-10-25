import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BaseRunner {
    WebDriver driver;
    String baseUrl;

    @Before
    public void setUp() {
        driver = BrowseFactory.chrome.create();
        driver.manage().window().maximize();
        baseUrl = "https://moscow-job.tinkoff.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @After
    public void tearDown() {
        driver.quit();
    }

}
