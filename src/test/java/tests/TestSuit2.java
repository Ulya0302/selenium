package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.GoogleMainPage;
import pages.GoogleResultPage;
import pages.TinkoffJobPage;
import pages.TinkoffTarifsPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSuit2 extends BaseRunner {
    @Test
    public void changeTabTest() {
        GoogleMainPage googleMain = app.google;
        googleMain.open();
        googleMain.changeTabWithSearch("тинькофф мобайл тарифы");
        GoogleResultPage googleResults = app.googleResults;
        googleResults.clickLinkByURL("tinkoff.ru/mobile-operator/tariffs");
        googleResults.closeCurTab();
        TinkoffJobPage jobPage = app.tinkoffJob;
        jobPage.switchToTab("Тарифы Тинькофф Мобайл");
        jobPage.isLoaded("Тарифы Тинькофф Мобайл");
        assertTrue("URL is wrong", jobPage.checkURL("tinkoff.ru/mobile-operator/tariffs"));
    }

    @Test
    public void changeRegionTest() {
        String defaultValMoscow, defaultValKrasnodar, maxValMoscow, maxValKrasnodar;
        TinkoffTarifsPage tinkoffTarifs = app.tinkoffTariffs;
        tinkoffTarifs.open();
        tinkoffTarifs.changeRegion("Москва");
        assertTrue("Fail in changing region", tinkoffTarifs.checkRegion("Москва"));
        System.out.println("Region changed successfully");
        tinkoffTarifs.refreshTab();
        assertTrue("Fail in region after refresh", tinkoffTarifs.checkRegion("Москва"));        
        System.out.println("After refresh region is OK");
        defaultValMoscow = tinkoffTarifs.getCost();
        tinkoffTarifs.setMaximum();
        maxValMoscow = tinkoffTarifs.getCost();
        tinkoffTarifs.changeRegion("Краснодар");
        assertTrue("Fail in changing region", tinkoffTarifs.checkRegion("Краснодар"));
        System.out.println("Region changed successfully");
        defaultValKrasnodar = tinkoffTarifs.getCost();
        tinkoffTarifs.setMaximum();
        maxValKrasnodar = tinkoffTarifs.getCost();
        Assert.assertNotEquals("Default costs in Moscow and Krasnodar are same, but should be different",
                defaultValMoscow, defaultValKrasnodar);
        System.out.println("Default costs in Moscow and Krasnodar are different.");
        assertEquals("Maximum costs in Moscow and Krasnodar are different, but should be same",
                maxValMoscow, maxValKrasnodar);
        System.out.println("Maximum costs in Moscow and Krasnodar are same.");
    }

    @Test
    public void notActiveButtonTest() {
        String value;
        TinkoffTarifsPage tinkoffTarifs = app.tinkoffTariffs;
        tinkoffTarifs.open();
        tinkoffTarifs.setMinimum();
        value = tinkoffTarifs.getCost();
        assertEquals(value, "Общая цена: 0 \u20BD");
        boolean flag = tinkoffTarifs.isActiveSendButton();
        assertFalse("Button is enabled", flag);
        System.out.println("Button is disabled");
    }


}
