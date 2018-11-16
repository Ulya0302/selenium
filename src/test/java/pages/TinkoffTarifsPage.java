package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TinkoffTarifsPage extends Page {
    public TinkoffTarifsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.tinkoff.ru/mobile-operator/tariffs/");
        isLoaded("Тарифы Тинькофф Мобайл");
    }

    public void changeRegion(String city) {
        WebElement el;
        List<WebElement> elements = driver.findElements(By.xpath("//span[contains(@class, 'regionName')]"));
        if (elements.size() > 0) {
            el = elements.get(0);
            if (el.getText().contains(city)) {
                driver.findElement(By.xpath("//span[contains(@class, 'optionAgreement')]")).click();
            } else {
                driver.findElement(By.xpath("//span[contains(@class, 'optionRejectionRegion')]")).click();
                driver.findElement(By.xpath(String.format("//div[contains(text(),'%s')]", city))).click();
            }
        } else {
            driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]/..")).click();
            driver.findElement(By.xpath(String.format("//div[contains(text(),'%s')]", city))).click();
        }
    }

    public boolean checkRegion(String city) {
        WebElement el = driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]"));
        if (el.getText().contains(city)) {
            return true;
        } else {
            return false;
        }
    }

    public String getCost() {
        wait.until(d -> driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")));
        return driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")).getText();
    }

    public void setMaximum() {
        //Select.driver = driver;
        //CheckBox.driver = driver;
        Select selectInternet = new Select("Интернет");
        Select selectCalls = new Select("Звонки");
        selectCalls.setValue(-1);
        selectInternet.setValue(-1);
        CheckBox boxModem = new CheckBox("модем");
        CheckBox boxSMS = new CheckBox("SMS");
        boxModem.setActive(true);
        boxSMS.setActive(true);
    }

    public void setMinimum() {
        //Select.driver = driver;
        //CheckBox.driver = driver;
        Select selectInternet = new Select("Интернет");
        Select selectCalls = new Select("Звонки");
        selectCalls.setValue(0);
        selectInternet.setValue(0);
        CheckBox boxMes = new CheckBox("Мессенджеры");
        CheckBox boxSocialNets = new CheckBox("Социальные сети");
        boxMes.setActive(false);
        boxSocialNets.setActive(false);
    }

    public boolean isActiveSendButton() {
        Button sendButton = new Button("Далее");
        return sendButton.isActive();
    }

    private class Select {
        //public  WebDriver driver = null;
        private WebElement field;
        private String xpathToField, xpathToDrop;

        public Select(String name) {
            xpathToField = "//span[contains(text(),'" + name + "')]/../div/span";
//span[contains(@class, 'select__value') and //*[contains(text(),'')]]
            xpathToDrop = "//span[contains(text(),'" + name + "')]/ancestor::div[contains(@class, 'drop')]" +
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
                            "//span[contains(@class, 'drop') and contains(text(),'" + name + "')]"))
            );
            element.click();
        }
    }

    private class CheckBox {
        //public static WebDriver driver = null;
        private WebElement box;
        private String text;
        private String xpath;

        public CheckBox(String name) {
            xpath = "//span[contains(text(), '" + name + "') and contains(@class, 'checkbox')]/..";
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
            } else {
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
            } else {
                curState = false;
            }
            return curState;
        }

    }

    private class Button {
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

    private class TextInput {
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
}
