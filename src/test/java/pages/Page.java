package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void refreshTab() {
        driver.navigate().refresh();
    }

    public boolean isLoaded(String title) {
        /**
         * Можно передавать как полностью название заголовка, так и часть
         */
        wait.until(d -> d.getTitle().contains(title));
        return true;
    }

    public void switchToTab(String tabName) {
        /**
         * Можно передавать как полностью название заголовка, так и часть
         */
        wait.until(d -> {
            boolean check = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                System.out.println(d.getTitle());
                check = d.getTitle().contains(tabName);
            }
            return check;
        });
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    public void closeCurTab() {
        driver.close();
    }

    public boolean checkURL(String url) {
        return driver.getCurrentUrl().contains(url);
    }
}
