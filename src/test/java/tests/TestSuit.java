package tests;

import org.junit.Test;
import pages.TinkoffJobPage;

import static org.junit.Assert.assertTrue;

public class TestSuit extends BaseRunner {

    @Test
    public void InvalidInputTest() {
        TinkoffJobPage tinkoffJob = app.tinkoffJob;
        tinkoffJob.open();
        tinkoffJob.clickOnFieldByName("fio");
        tinkoffJob.sendStringInFieldByName("fio", "Петр");
        tinkoffJob.clickOnFreeSpace();
        tinkoffJob.clickOnFieldByName("email");
        tinkoffJob.sendStringInFieldByName("email", "testemail@");
        tinkoffJob.clickOnFieldByName("phone");
        tinkoffJob.sendStringInFieldByName("phone", "+7 (90");
        tinkoffJob.clickOnFieldByName("city");
        tinkoffJob.sendStringInFieldByName("city", "Ростов1");
        tinkoffJob.clickOnFreeSpace();
        assertTrue(tinkoffJob.checkErrorMessageByName("fio", "Недостаточно информации. " +
                "Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)"));
        assertTrue(tinkoffJob.checkErrorMessageByName("email", "Введите корректный адрес эл. почты"));
        assertTrue(tinkoffJob.checkErrorMessageByName("phone",
                "Номер телефона должен состоять из 10 цифр, начиная с кода оператора"));
        assertTrue(tinkoffJob.checkErrorMessageByName("city",
                "Допустимо использовать только буквы русского, латинского алфавита и дефис"));

    }
    @Test
    public void EmptyInputTest() {
        TinkoffJobPage tinkoffJob = app.tinkoffJob;
        tinkoffJob.open();
        tinkoffJob.clickOnFieldByName("fio");
        tinkoffJob.clickOnFieldByName("email");
        tinkoffJob.clickOnFieldByName("phone");
        tinkoffJob.clickOnFieldByName("city");
        tinkoffJob.clickOnFieldVacancy();
        tinkoffJob.clickOnFreeSpace();
        assertTrue(tinkoffJob.checkErrorMessageByName("fio", "Поле обязательное"));
        assertTrue(tinkoffJob.checkErrorMessageByName("email", "Поле обязательное"));
        assertTrue(tinkoffJob.checkErrorMessageByName("city", "Поле обязательное"));
        assertTrue(tinkoffJob.checkErrorMessageInVacancy("Поле обязательное"));
        assertTrue(tinkoffJob.checkErrorMessageByName("phone", "Необходимо указать номер телефона"));
    }
    /*

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
    */
}
