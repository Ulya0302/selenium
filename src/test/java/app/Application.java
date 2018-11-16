package app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GoogleMainPage;
import pages.GoogleResultPage;
import pages.TinkoffJobPage;
import pages.TinkoffTarifsPage;
import tests.BrowserFactory;

import java.util.concurrent.TimeUnit;

public class Application {
    private WebDriverWait wait;
    private WebDriver driver;
    public GoogleMainPage google;
    public GoogleResultPage googleResults;
    public TinkoffJobPage tinkoffJob;
    public TinkoffTarifsPage tinkoffTariffs;


    public Application() {
        driver = getDriver();
        wait = new WebDriverWait(driver, 30);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //pages
        google = new GoogleMainPage(driver);
        googleResults = new GoogleResultPage(driver);
        tinkoffJob = new TinkoffJobPage(driver);
        tinkoffTariffs = new TinkoffTarifsPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    private WebDriver getDriver() {
        String browserName = System.getProperty("browser") == null ? "chrome" : System.getProperty("browser");
        return BrowserFactory.buildDriver(browserName);
    }
}
