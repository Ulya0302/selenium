package tests;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.GoogleMainPage;
import pages.GoogleResultPage;
import pages.TinkoffTariffsPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSuit2 extends BaseRunner {
    Logger logger = LoggerFactory.getLogger(TestSuit2.class);

    @Test
    public void changeTabTest() {
        logger.info("Тест начат");
        GoogleMainPage googleMain = app.google;
        googleMain.open();
        googleMain.changeTabWithSearch("тинькофф мобайл тарифы");
        GoogleResultPage googleResults = app.googleResults;
        googleResults.clickLinkByURL("tinkoff.ru/mobile-operator/tariffs");
        googleResults.closeCurTab();
        TinkoffTariffsPage tinkoffTariffs = app.tinkoffTariffs;
        tinkoffTariffs.switchToTab("Тарифы Тинькофф Мобайл");
        tinkoffTariffs.isLoaded("Тарифы Тинькофф Мобайл");
        assertTrue("FAIL URL не совпадает", tinkoffTariffs.checkURL("tinkoff.ru/mobile-operator/tariffs"));
        logger.info("Тест завершен");
    }

    @Test
    public void changeRegionTest() {
        logger.info("Тест начат");
        String defaultValMoscow, defaultValKrasnodar, maxValMoscow, maxValKrasnodar;
        TinkoffTariffsPage tinkoffTarifs = app.tinkoffTariffs;
        tinkoffTarifs.open();
        tinkoffTarifs.changeRegion("Москва");
        assertTrue("FAIL Неудача при смене региона", tinkoffTarifs.checkRegion("Москва"));
        logger.info("Регион успешно сменен");
        tinkoffTarifs.refreshTab();
        assertTrue("FAIL После обновления регион сбросился", tinkoffTarifs.checkRegion("Москва"));
        logger.info("После обновления регион в порядке");
        defaultValMoscow = tinkoffTarifs.getCost();
        tinkoffTarifs.setMaximum();
        maxValMoscow = tinkoffTarifs.getCost();
        tinkoffTarifs.changeRegion("Краснодар");
        assertTrue("FAIL Неудача в смене региона", tinkoffTarifs.checkRegion("Краснодар"));
        logger.info("Регион изменен успешно");
        defaultValKrasnodar = tinkoffTarifs.getCost();
        tinkoffTarifs.setMaximum();
        maxValKrasnodar = tinkoffTarifs.getCost();
        Assert.assertNotEquals("FAIL Стандартные цены в Москве и Краснодаре совпали, а должны различаться",
                defaultValMoscow, defaultValKrasnodar);
        logger.info("Стандартные цены в Москве и Краснодаре различаются ОК");
        assertEquals("FAIL Максимальные цены в Москве и Краснодаре различаются, а должны совпадать",
                maxValMoscow, maxValKrasnodar);
        logger.info("Максимальные цены в Мосве и Краснодаре различаются ОК");
        logger.info("Тест завершен");
    }

    @Test
    public void notActiveButtonTest() {
        logger.info("Тест начат");
        String value;
        TinkoffTariffsPage tinkoffTarifs = app.tinkoffTariffs;
        tinkoffTarifs.open();
        tinkoffTarifs.setMinimum();
        value = tinkoffTarifs.getCost();
        assertEquals(value, "Общая цена: 0 \u20BD");
        boolean flag = tinkoffTarifs.isActiveSendButton();
        assertFalse("FAIL Кнопка активна", flag);
        System.out.println("Кнопка неактивна OK");
        logger.info("Тест завершен");
    }


}
