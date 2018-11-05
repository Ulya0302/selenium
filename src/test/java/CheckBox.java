import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CheckBox {
    public static WebDriver driver = null;
    private WebElement box;
    private String text;
    private String xpath;

    public CheckBox(String name) {
        xpath = "//span[contains(text(), '"+name+"') and contains(@class, 'checkbox')]/..";
        text = driver
                .findElement(By.xpath(xpath + "/span"))
                .getText();
        box = driver
                .findElement(By.xpath(xpath + "/div"));
    }

    public void setActive(boolean flag) {
        WebElement element;
        element = driver.findElement(By.xpath(xpath));
        if (element.getAttribute("class").contains("ui-checkbox  ui-checkbox_checked")) {
            if (flag)
                System.out.println("Button is active already");
            else
                box.click();
        }
        else {
            if (flag)
                box.click();
            else
                System.out.println("Button is inactive already");
        }
    }

    public String getBoxText() {
        return text;
    }

    public boolean getActive() {
        WebElement element;
        element = driver.findElement(By.xpath(xpath));
        boolean curState;
        if (element.getAttribute("class").contains("ui-checkbox  ui-checkbox_checked")) {
            curState = true;
        }
        else {
            curState = false;
        }
        return curState;
    }









    //By.xpath("../label")).getAttribute("class").contains("ui-checkbox  ui-checkbox_checked")
}
