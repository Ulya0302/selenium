import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TextInput {
    public static WebDriver driver = null;
    private WebElement field;
    private String xpath, text;

    public TextInput(String name) {
        xpath = "//div[contains(@class, 'ui-input')]//span[contains(text(),'"+name+"')]";
        text = driver.findElement(By.xpath(xpath)).getText();
        List<WebElement> els = driver.findElements(By.xpath(xpath + "/../../input"));
        if (els.size() == 2)
            field = els.get(1);
        else
            field = els.get(0);
    }

    public void fill(String message) {
        field.sendKeys(message);
    }

    public String getNameField() {
        return text;
    }

    public boolean isEmpty() {
        String curString = field.getAttribute("value");
        return curString.equals("+7(") || curString.equals("");
    }

    public String getValue() {
        return field.getAttribute("value");
    }
}
