import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TestSuit2 extends BaseRunner {

    @Test
    public void changeTabTest() {
        driver.get("https://www.google.ru/");
        driver.findElement(By.xpath("//*[@name='q']")).sendKeys("мобайл тинькофф");
        driver.findElement(By.xpath("//li[@role='presentation']//div[@class='sbqs_c']/b[text()=' тарифы']/..")).click();
        driver.findElement(By.xpath("//*[text()='Тарифы Тинькофф Мобайл']/..")).click();
        String curTitle = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(d -> {
            boolean check = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                check = d.getTitle().equals("Тарифы Тинькофф Мобайл");
                if (check) {
                    driver.switchTo().window(curTitle);
                    driver.close();
                    driver.switchTo().window(title);
                    break;
                }
            }
            return check;
        });
        boolean check = driver.getCurrentUrl().equals("https://www.tinkoff.ru/mobile-operator/tariffs/");
        Assert.assertTrue("URL is wrong", check);
        System.out.println("Successful");
    }

    @Test
    public void changeRegionTest() {
        WebElement el;
        String defaultValMoscow, defaultValKrasnodar, maxValMoscow, maxValKrasnodar;
        driver.get("https://www.tinkoff.ru/mobile-operator/tariffs/");
        Assert.assertTrue("Fail in changing region", changeReg("Москва"));
        System.out.println("Region changed successfully");
        driver.navigate().refresh();
        Assert.assertTrue("", checkRegion("Москва"));
        System.out.println("After refresh region is OK");
        defaultValMoscow = driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")).getText();
        setMaximum();
        maxValMoscow = driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")).getText();
        Assert.assertTrue("Fail in changing region", changeReg("Краснодар"));
        System.out.println("Region changed successfully");
        defaultValKrasnodar = driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")).getText();
        setMaximum();
        maxValKrasnodar = driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")).getText();
        Assert.assertNotEquals("Default costs in Moscow and Krasnodar are same, but should be different",
                defaultValMoscow, defaultValKrasnodar);
        System.out.println("Default costs in Moscow and Krasnodar are different.");
        Assert.assertEquals("Maximum costs in Moscow and Krasnodar are different, but should be same",
                maxValMoscow, maxValKrasnodar);
        System.out.println("Maximum costs in Moscow and Krasnodar are same.");
    }

    @Test
    public void notActiveButton() {
        String value;

        driver.get("https://www.tinkoff.ru/mobile-operator/tariffs/");
        setMinimum();
        value = driver.findElement(By.xpath("//h3[contains(text(), 'цена')]")).getText();
        if (value.equals("Общая цена: 0 \u20BD")) {
            System.out.println("Cost is equal 0");
        }
        else {
            System.out.println("Cost isn't equal 0");
        }

        Button.driver = driver;
        Button sendButton = new Button("Заказать");
        boolean flag = sendButton.isActive();
        Assert.assertFalse("Button is enabled", flag);
        System.out.println("Button is disabled");
    }

    private boolean changeReg(String city) {
        WebElement el;
        WebDriverWait wait = new WebDriverWait(driver, 60);
        List<WebElement> elements = driver.findElements(By.xpath("//span[contains(@class, 'regionName')]"));
        if (elements.size() > 0) {
            el = elements.get(0);
            if (el.getText().contains(city)) {
                driver.findElement(By.xpath("//span[contains(@class, 'optionAgreement')]")).click();
            } else {
                driver.findElement(By.xpath("//span[contains(@class, 'optionRejectionRegion')]")).click();
                driver.findElement(By.xpath("//div[contains(text(),"+city+")]")).click();
            }
        }
        else {
            driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]/..")).click();
            driver.findElement(By.xpath("//div[contains(text(),'" + city + "')]")).click();
        }
        return checkRegion(city);
    }

    private boolean checkRegion(String city) {
        WebElement el = driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]"));
        if (el.getText().contains(city)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void setMaximum() {
        Select.driver = driver;
        CheckBox.driver = driver;
        Select selectInternet = new Select("Интернет");
        Select selectCalls = new Select("Звонки");
        selectCalls.setValue(-1);
        selectInternet.setValue(-1);
        CheckBox boxModem = new CheckBox("модем");
        CheckBox boxSMS = new CheckBox("SMS");
        boxModem.setActive(true);
        boxSMS.setActive(true);
    }

    private void setMinimum() {
        Select.driver = driver;
        CheckBox.driver = driver;
        Select selectInternet = new Select("Интернет");
        Select selectCalls = new Select("Звонки");
        selectCalls.setValue(0);
        selectInternet.setValue(0);
        CheckBox boxMes = new CheckBox("Мессенджеры");
        CheckBox boxSocialNets = new CheckBox("Социальные сети");
        boxMes.setActive(false);
        boxSocialNets.setActive(false);
    }
}
