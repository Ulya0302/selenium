package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.security.Key;
import java.util.List;

public class GoogleResultPage extends Page {
    public GoogleResultPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickLinkByURL(String url) {
        logger.info("Переходим по " + url + "....");
        By listItems = By.xpath(String.format("//a[contains(@href, '%s')]", url));
        List<WebElement> elementList = driver.findElements(listItems);
        wait.until(d -> elementList.size() > 0);
        elementList.get(0).click();

        logger.info("Успешный переход по '" + elementList.get(0).getText() + "'. Текущая вкладка была закрыта.");
    }
}
