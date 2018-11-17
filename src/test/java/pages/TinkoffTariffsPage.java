package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TinkoffTariffsPage extends Page {
    public TinkoffTariffsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.navigate().to("https://www.tinkoff.ru/mobile-operator/tariffs/");
        isLoaded("Тарифы Тинькофф Мобайл");
    }

    public void changeRegion(String city) {
        WebElement el;
        logger.info("Меняем регион на " + city + "....");
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
        logger.info("Сравниваем текущий регион с " + city + ".....");
        WebElement el = driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]"));
        return el.getText().contains(city);
    }

    public String getCost() {
        wait.until(d -> driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")));
        return driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")).getText();
    }

    public void setMaximum() {
        logger.info("Устанавливаем максимальные значения в форме...");
        Select selectInternet = new Select("Интернет");
        Select selectCalls = new Select("Звонки");
        selectCalls.setValue(-1);
        selectInternet.setValue(-1);
        CheckBox boxModem = new CheckBox("модем");
        CheckBox boxSMS = new CheckBox("SMS");
        boxModem.setActive(true);
        boxSMS.setActive(true);
        logger.info("Все значения в форме были установлены на максимальные");
    }

    public void setMinimum() {
        logger.info("Устанавливаем минимальные значения в форме...");
        Select selectInternet = new Select("Интернет");
        Select selectCalls = new Select("Звонки");
        selectCalls.setValue(0);
        selectInternet.setValue(0);
        CheckBox boxMes = new CheckBox("Мессенджеры");
        CheckBox boxSocialNets = new CheckBox("Социальные сети");
        boxMes.setActive(false);
        boxSocialNets.setActive(false);
        logger.info("Все значения в форме были установлены на минимальные");
    }

    public boolean isActiveSendButton() {
        Button sendButton = new Button("Далее");
        return sendButton.isActive();
    }

    private class Select {
        private WebElement field;
        private String xpathToField, xpathToDrop, name;

        public Select(String name) {
            logger.info("Инициализация выпадающего списка '" + name + "'....");
            this.name = name;
            xpathToField = "//span[contains(text(),'" + name + "')]/../div/span";
            xpathToDrop = "//span[contains(text(),'" + name + "')]/ancestor::div[contains(@class, 'drop')]" +
                    "//span[contains(@class, 'drop')]";
            field = driver
                    .findElement(By.xpath(xpathToField + "[1]"));
            logger.info("Инициализация выпадающего списка '" + name + "' прошла успешно");
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
            logger.info(String.format("Устанавливаем значение с индексом %d в поле %s...", index, name));
            field.click();
            List<WebElement> elements = driver.findElements(By.xpath(xpathToDrop));
            int curInd = (index + elements.size()) % elements.size();
            elements.get(curInd).click();
            logger.info(String.format("Значение с индексом %d в поле %s успешно установлено", index, name));
        }

        public void setValue(String name) {
            logger.info(String.format("Устанавливаем значение %s в поле %s...", name, getCurText()));
            field.click();
            WebElement element = driver.findElement(
                    By.xpath(xpathToDrop.replace(
                            "//span[contains(@class, 'drop')]",
                            "//span[contains(@class, 'drop') and contains(text(),'" + name + "')]"))
            );
            element.click();
            String.format("Значение %s в поле %s успешно установлено", name, getCurText());

        }
    }

    private class CheckBox {
        private WebElement box;
        private String text;
        private String xpath;

        public CheckBox(String name) {
            logger.info("Инициализация CheckBox'а '" + name + "'....");
            xpath = "//span[contains(text(), '" + name + "') and contains(@class, 'checkbox')]/..";
            text = driver
                    .findElement(By.xpath(xpath + "/span"))
                    .getText();
            box = driver
                    .findElement(By.xpath(xpath + "/div"));
            logger.info("Инициализация CheckBox'а '" + name + "' успешно завершена");
        }

        public void setActive(boolean flag) {
            logger.info(String.format("Установка активности '%s' на %s....", getBoxText(), flag));
            WebElement element;
            element = driver.findElement(By.xpath(xpath));
            if (element.getAttribute("class").contains("ui-checkbox  ui-checkbox_checked")) {
                if (flag) {
                    logger.info("Кнопка уже активна");
                    return;
                } else
                    box.click();
            } else {
                if (flag)
                    box.click();
                else {
                    logger.info("Кнопка уже неактивна");
                    return;
                }
            }
            logger.info(String.format("Активность '%s' успешна установлена на %s", getBoxText(), flag));
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
            logger.info("Инициализация кнопки '" + name + "'....");
            xpath = "//button//div[contains(text(),'"+name+"')]";
            button = driver.findElement(By.xpath(xpath+"/../.."));
            text = driver.findElement(By.xpath(xpath)).getText();
            logger.info("Инициализация кнопки '" + name + "' произошла успешна");
        }

        public boolean isActive() {
            logger.info("Проверяем активности кнопки '" + getButtonText() + "'....");
            List<WebElement> elements;
            elements = driver.findElements(By.xpath(xpath+"//ancestor::button[@disabled]"));
            return elements.isEmpty();
        }

        public String getButtonText() {
            return text;
        }

        public void click() {
            logger.info("Нажимаем на кнопку " + getButtonText() + "....");
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
            logger.info("Отправка " + message + "в" + field.getText() + "....");
            field.sendKeys(message);
            logger.info(message + "успешно отправлено в" + field.getText());
        }

        public String getNameField() {
            return text;
        }

        public boolean isEmpty() {
            logger.info("Проверяем на заполненность " + getNameField() + "...");
            String curString = field.getAttribute("value");
            return curString.equals("+7(") || curString.equals("");
        }

        public String getValue() {
            logger.info("Получаем содержание в " + getNameField() + "...");
            return field.getAttribute("value");
        }
    }
}
