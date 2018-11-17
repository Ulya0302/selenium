package tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.TinkoffJobPage;

import static org.junit.Assert.assertTrue;

public class TestSuit extends BaseRunner {
    Logger logger = LoggerFactory.getLogger(TestSuit.class);
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
        assertTrue("FAIL Текст ошибки другой", tinkoffJob.checkErrorMessageByName("fio",
                "Недостаточно информации. " +
                "Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)"));
        logger.info("OK");
        assertTrue("FAIL Текст ошибки другой",
                tinkoffJob.checkErrorMessageByName("email", "Введите корректный адрес эл. почты"));
        logger.info("OK");
        assertTrue("FAIL Текст ошибки другой",
                tinkoffJob.checkErrorMessageByName("phone",
                        "Номер телефона должен состоять из 10 цифр, начиная с кода оператора"));
        logger.info("OK");
        assertTrue("FAIL Текст ошибки другой",
                tinkoffJob.checkErrorMessageByName("city",
                "Допустимо использовать только буквы русского, латинского алфавита и дефис"));
        logger.info("OK");
        logger.info("Тест завершен");

    }
    @Test
    public void EmptyInputTest() {
        logger.info("Тест начат");
        TinkoffJobPage tinkoffJob = app.tinkoffJob;
        tinkoffJob.open();
        tinkoffJob.clickOnFieldByName("fio");
        tinkoffJob.clickOnFieldByName("email");
        tinkoffJob.clickOnFieldByName("phone");
        tinkoffJob.clickOnFieldByName("city");
        tinkoffJob.clickOnFieldVacancy();
        tinkoffJob.clickOnFreeSpace();
        assertTrue("FAIL Текст ошибки другой",
                tinkoffJob.checkErrorMessageByName("fio", "Поле обязательное"));
        logger.info("OK");
        assertTrue("FAIL Текст ошибки другой",
                tinkoffJob.checkErrorMessageByName("email", "Поле обязательное"));
        logger.info("OK");
        assertTrue("FAIL Текст ошибки другой",
                tinkoffJob.checkErrorMessageByName("city", "Поле обязательное"));
        logger.info("OK");
        assertTrue("FAIL Текст ошибки другой",
                tinkoffJob.checkErrorMessageInVacancy("Поле обязательное"));
        logger.info("OK");
        assertTrue("FAIL Текст ошибки другой",
                tinkoffJob.checkErrorMessageByName("phone", "Необходимо указать номер телефона"));
        logger.info("OK");
        logger.info("Тест завершен");
    }

}
