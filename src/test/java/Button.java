import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Button {
    public static WebDriver driver = null;
    private WebElement button;
    private String xpath, text;

    public Button(String name) {
        xpath = "//button//div[contains(text(),'"+name+"')]";
        button = driver.findElement(By.xpath(xpath+"/../.."));
        text = driver.findElement(By.xpath(xpath)).getText();
    }

    public boolean isActive() {
        List<WebElement> elements;
        elements = driver.findElements(By.xpath(xpath+"//ancestor::button[@disabled]"));
        return elements.isEmpty();
    }

    public String getButtonText() {
        return text;
    }

    public void click() {
        button.click();
    }
}
