import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

public class TestSuit extends BaseRunner {

    @Test
    public void testCase1(){
        driver.get(baseUrl);
        driver.findElement(By.xpath("//input[@name='fio']")).click();
        driver.findElement(By.xpath("//input[@name='fio']")).sendKeys("Петр");
        driver.findElement(By.xpath("//div[@class='Header__centered_mGkXM']")).click();
        driver.findElement(By.xpath("//input[@name='email']")).click();
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("wrong");
        driver.findElement(By.xpath("//input[@name='city']")).click();
        driver.findElement(By.xpath("//input[@name='city']")).sendKeys("Ростов1");
        driver.findElement(By.xpath("//input[@name='phone']")).click();
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+7 (90");
        driver.findElement(By.xpath("//div[@class='Header__centered_mGkXM']")).click();
        assertEquals("Недостаточно информации. " +
                        "Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)",
                    driver.findElement(By.xpath
                            ("//div[@data-qa='fio']/../following-sibling::div[@class='Error__errorMessage_q8BBY']"))
                            .getText()
        );
        assertEquals("Введите корректный адрес эл. почты",
                driver.findElement(By.xpath
                        ("//div[@data-qa='email']/following-sibling::div[@class='Error__errorMessage_q8BBY']"))
                        .getText()
        );
        assertEquals("Допустимо использовать только буквы русского, латинского алфавита и дефис",
                driver.findElement(By.xpath
                        ("//div[@class='Row__row_AjrJL'][3]//div[@class='Error__errorMessage_q8BBY']"))
                        .getText()
        );
        assertEquals("Номер телефона должен состоять из 10 цифр, начиная с кода оператора",
                driver.findElement(By.xpath
                        ("//div[@data-qa='email']/../following-sibling::div//div[@class='Error__errorMessage_q8BBY']"))
                        .getText()
        );
    }

    @Test
    public void testCase2() {
        driver.get(baseUrl);
        driver.findElement(By.cssSelector("input[name='fio']")).click();
        driver.findElement(By.cssSelector("input[name='phone']")).click();
        driver.findElement(By.cssSelector("input[name='email']")).click();
        driver.findElement(By.cssSelector("input[name='city']")).click();
        driver.findElement(By.cssSelector("div[class*='SelectItem__place']")).click();
        driver.findElement(By.xpath("//div[@class='Header__centered_mGkXM']")).click();
        assertEquals("Поле обязательное",
                driver.findElement(By.cssSelector
                        ("#form div.Row__row_AjrJL:nth-child(1) div.Error__errorMessage_q8BBY"))
                        .getText()
        );
        assertEquals("Поле обязательное",
                driver.findElement(By.cssSelector
                        ("#form div.Row__row_AjrJL:nth-child(2) div[class*=FormField]:nth-child(1) div[class*=Error]"))
                        .getText()
        );
        assertEquals("Необходимо указать номер телефона",
                driver.findElement(By.cssSelector
                        ("#form div.Row__row_AjrJL:nth-child(2)  div[class*=FormField]:nth-child(2) div[class*=Error]"))
                        .getText())
        ;
        assertEquals("Поле обязательное",
                driver.findElement(By.cssSelector
                        ("#form div.Row__row_AjrJL:nth-child(3) div[class*=Error]"))
                        .getText()
        );
        assertEquals("Поле обязательное",
                driver.findElement(By.cssSelector
                        ("#form div.Row__row_AjrJL:nth-child(4) div[class*=Error]"))
                        .getText());
    }
}
