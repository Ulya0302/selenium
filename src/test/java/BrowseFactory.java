import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public enum  BrowseFactory {
        chrome {
            public WebDriver create() {
                updateProperty("chrome");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                return new ChromeDriver();
            }
        },
        firefox {
            public WebDriver create() {
                updateProperty("firefox");
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "./src/test/java/firefox.log");
                FirefoxOptions options = new FirefoxOptions();
                options.addPreference("dom.webnotifications.enabled", false);
                return new FirefoxDriver();
            }
        },
        opera {
            public WebDriver create() {
                updateProperty("opera");
                OperaOptions opt = new OperaOptions();
                String path = System.getProperty("operaBinary");
                System.out.println(path);
                if (path == null) {
                    System.setProperty("operaBinary", "C:\\Program Files\\Opera\\56.0.3051.52\\opera.exe");
                }
                opt.setBinary(System.getProperty("operaBinary"));

                return new OperaDriver(opt);
            }

        };
        public WebDriver create() {
            return null;
        }

        public void updateProperty(String browserName) {
            if (System.getProperty("browser") == null)
            System.setProperty("browser", browserName);
        }
    }

