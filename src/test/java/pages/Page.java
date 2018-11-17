package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;
    Logger logger = LoggerFactory.getLogger(Page.class);

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
        logger.info(String.format("Страница '%s' успешно загружена", title));
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
                check = d.getTitle().contains(tabName);
            }
            return check;
        });
    }

    public void switchToTabByHandle(String handle) {
        driver.switchTo().window(handle);
    }

    public void goToUrl(String url) {
        driver.navigate().to(url);
    }

    public void closeCurTab() {
        driver.close();
        logger.info("Вкладка '"+ driver.getTitle() + "' закрыта");
    }

    public boolean checkURL(String url) {
        return driver.getCurrentUrl().contains(url);
    }
}
