package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class GoogleMainPage extends Page {
    public GoogleMainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "q")
    public WebElement searchField;


    public void open() {
        driver.navigate().to("https://www.google.ru/");
        isLoaded("Google");
    }

    public void changeTabWithSearch(String request) {
        searchField.sendKeys(request);
        wait
            .ignoring(StaleElementReferenceException.class)
            .withMessage("Something wrongs...")
            .pollingEvery(Duration.ofSeconds(5))
            .until(d ->{
                By listItems = By.xpath("//li[@role='presentation']//div[@class='sbqs_c']");
                List<WebElement> elements = driver.findElements(listItems);
                for (WebElement el : elements) {
                    if (el.getText().equals(request.toLowerCase()))
                        el.click();
                    break;
                }
                return isLoaded(request + " - Поиск в Google");
            });
    }
}
