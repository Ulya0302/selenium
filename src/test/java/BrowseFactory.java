import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public enum  BrowseFactory {
        chrome {
            public WebDriver create() {
                updateProperty("chrome");
                return new ChromeDriver();
            }
        },
        firefox {
            public WebDriver create() {
                updateProperty("firefox");
                return new FirefoxDriver();
            }
        },
        opera {
            public WebDriver create() {
                updateProperty("opera");
                //"C:\\Program Files\\Opera\\56.0.3051.43\\opera.exe"
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

