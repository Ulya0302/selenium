package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;


public class BrowserFactory {
    public static WebDriver buildDriver(String browserName) {
        switch (browserName) {
            case "firefox":
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                System.setProperty(
                        FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
                        "./src/test/java/logs/firefox/firefox.log"
                );
                FirefoxOptions optFirefox = new FirefoxOptions();
                optFirefox.addPreference("dom.webnotifications.enabled", false);
                return new FirefoxDriver(optFirefox);
            case "opera":
                OperaOptions optOpera = new OperaOptions();
                String path = System.getProperty("operaBinary");
                if (path == null) {
                    System.setProperty("operaBinary", "C:\\Program Files\\Opera\\56.0.3051.52\\opera.exe");
                }
                optOpera.setBinary(System.getProperty("operaBinary"));
                return new OperaDriver(optOpera);
            default:
                ChromeOptions optChrome = new ChromeOptions();
                optChrome.addArguments("--disable-notifications");
                return new ChromeDriver(optChrome);
        }
    }
}
