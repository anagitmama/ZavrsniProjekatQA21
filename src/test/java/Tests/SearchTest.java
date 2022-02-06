package Tests;

import Pages.BasePage;
import Pages.SearchPage;
import Pages.UserWelcomePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static Pages.Strings.*;

public class SearchTest extends BaseTest {
    public BasePage basePage;

    @BeforeTest
    public void initDriver() {
        OpenChromeDriver();
        basePage = new BasePage(driver);
    }

    @AfterTest
    public void killDriver() {
        driver.quit();
    }

    @Test
    public void searchForProduct() throws InterruptedException {
        searchForKeyword();
        String currentURL = driver.getCurrentUrl();
        boolean b = currentURL.contains(SEARCH);
        assert b : NOT_ON_SEARCH_RESULTS;
        SearchPage searchPage = new SearchPage(driver);
        searchPage.getAdTitleFromList(0);
        boolean e = searchPage.getAdTitleFromList(0).contains(AUTO);
        assert e : SEARCH_KEYWORD_NOT_PRESENT;
    }

    private void searchForKeyword() {
        clearPreviousData();
        login(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains(WELCOME));
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        welcomePage.clickKupujemProdajemTitleIcon();
        basePage.enterTextinSearchField(AUTO_NEWLINE);
    }

    @Test
    public void sortingByHighestPrice() throws InterruptedException, ParseException {
        searchForKeyword();
        wait.until(ExpectedConditions.urlContains(SEARCH));
        SearchPage searchPage = new SearchPage(driver);
        searchPage.jsClick(searchPage.getSortPrices());
        searchPage.jsClick(searchPage.getSkupljeSort());
        searchPage.jsClick(searchPage.getSearchButton());
        Thread.sleep(3000);
        List<Double> listaCena = new ArrayList<>();
        for (int i = 1; i < searchPage.getAdPrices().size(); i++) {
            String[] cena = searchPage.getAdPrices().get(i).getText().split(SPACE_NEWLINE_REGEX);
            if (cena[1].equals(EUR_SIGN)) {
                listaCena.add(konvertujUDIN(cena[0]));
            } else {
                NumberFormat format = NumberFormat.getInstance(Locale.ITALIAN);
                listaCena.add(format.parse(cena[0]).doubleValue());
            }
        }
        List<Double> sortiranaLista = new ArrayList<>();
        sortiranaLista.addAll(listaCena);
        Collections.sort(sortiranaLista);
        Collections.reverse(sortiranaLista);
        assert sortiranaLista.equals(listaCena) : WRONG_PRICE_SORT;

    }

    public double konvertujUDIN(String vrednostUEUR) throws ParseException {
        basePage.enterValueIntoNbsAmountField(vrednostUEUR);
        return basePage.getExchangedValue();
    }

    @Test
    public void sortByLocation() {
        searchForKeyword();
        basePage.jsClick(basePage.getLocationSort());
        basePage.jsClick(basePage.getLocationKragujevac());
        SearchPage searchPage = new SearchPage(driver);
        searchPage.jsClick(searchPage.getSearchButton());
        List<WebElement> elementiLokacije = new ArrayList<>(searchPage.getLocationPlace());
        elementiLokacije.remove(0);
        elementiLokacije.forEach(i -> {
            assert i.getText().equals(KRAGUJEVAC) : "Pogresna lokacija, dobijeno: " + i.getText() + ", ocekivano: Kragujevac";
        });
    }
}
