package tests;


import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.logging.Level;

import java.io.File;
import java.io.IOException;

public class BrowserFactory {

    public static class MyListener extends AbstractWebDriverEventListener {

        Logger logger = LoggerFactory.getLogger(BrowserFactory.class);

        @Override
        public void afterNavigateTo(String url, WebDriver driver) {
            logger.info("Переход по: " + url);
        }

        @Override
        public void afterNavigateRefresh(WebDriver driver) {
            logger.info("Обновление страницы: '" + driver.getTitle() + "'");
        }

        /*
        @Override
        public void afterGetText(WebElement element, WebDriver driver, String text) {
            logger.info("Получен '" + text + "' из " + element.getText());
        }
        */

        @Override
        public void afterSwitchToWindow(String windowName, WebDriver driver) {
            logger.info("Переход к вкладке: '" + driver.getTitle() + "'");
        }
        /*
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            logger.info("Обращение к элементу " + by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            logger.info("Найден элемент " + by);
        }
            */
        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File file = new File("target", "sccreen-" + System.currentTimeMillis() + ".png");
            try {
                Files.copy(tmp, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.error(file.getAbsolutePath());
        }
    }

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
                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                optChrome.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                return new ChromeDriver(optChrome);
        }
    }
}
