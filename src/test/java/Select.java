import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.nio.file.WatchEvent;
import java.util.List;

public class Select {
    public static WebDriver driver = null;
    private WebElement field;
    private String xpathToField, xpathToDrop;

    public Select(String name) {
        xpathToField = "//span[contains(text(),'"+name+"')]/../div/span";

        xpathToDrop = "//span[contains(text(),'"+name+"')]/ancestor::div[contains(@class, 'drop')]" +
                "//span[contains(@class, 'drop')]";
        field = driver
                .findElement(By.xpath(xpathToField + "[1]"));
    }

    public String getCurText() {
        String text;
        text = driver
                .findElement(By.xpath(xpathToField + "[1]"))
                .getText();
        text += " " + driver
                .findElement(By.xpath(xpathToField + "[2]"))
                .getText();
        return text;
    }

    public void setValue(int index) {
        /**
         * Введите -1, чтобы получить последний элемент
         */
        field.click();
        List<WebElement> elements = driver.findElements(By.xpath(xpathToDrop));
        int curInd = (index + elements.size()) % elements.size();
        elements.get(curInd).click();

    }

    public void setValue(String name) {
        field.click();
        WebElement element = driver.findElement(
                By.xpath(xpathToDrop.replace(
                        "//span[contains(@class, 'drop')]",
                        "//span[contains(@class, 'drop') and contains(text(),'"+name+"')]"))
                );
        element.click();

    }
}
