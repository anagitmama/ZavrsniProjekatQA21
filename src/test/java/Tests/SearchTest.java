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

    /**
     * CASE 1: Follow ad
     * PRECONDITION: Sign in with valid credentials
     * Test steps:
     * 1. Upon login click on KupujemProdajemTitleIcon and redirect to base page
     * 2. Click inside search field
     * 3. Enter search keyword into search field
     * 4. Click on "Trazi" button
     * 5. Get the title of the first ad
     * <p>
     * Expected result:
     * 1. Verify that the user is redirected to the Search page URL
     * 2. Verify that first displayed result contains searched keyword
     */

    @Test
    public void searchForProduct() throws InterruptedException {
        searchForKeyword();
        String currentURL = driver.getCurrentUrl();
        boolean b = currentURL.contains(SEARCH);
        assert b : NOT_ON_SEARCH_RESULTS;
        SearchPage searchPage = new SearchPage(driver);
        boolean e = searchPage.getAdTitleFromList(0).contains(AUTO);
        assert e : SEARCH_KEYWORD_NOT_PRESENT;
    }

    /**
     * CASE 2: Sort ads by highest price first
     * PRECONDITION: Sign in with valid credentials
     * Test steps:
     * 1. Upon login click on KupujemProdajemTitleIcon and redirect to base page
     * 2. Click inside search field
     * 3. Enter search keyword into search field
     * 4. Click on "Trazi" button
     * 5. Click on the "Sortiraj" drop down
     * 6. Select "Skuplje"
     * 7. Click on "Trazi" button
     * 8. Extract all prices from the page
     * 9. Split the price string so that first member is value and second member is currency
     * 10. Convert prices that are shown in EUR to the RSD, by using converter widget
     * 11. Convert prices from string to double, and store them in a list
     * 12. Create new list, and sort it by descending value
     * <p>
     * Expected result:
     * 1. Verify that the user is redirected to the Search page URL
     * 2. Verify that a list containing prices is equal to the created sorted list
     */
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
        List<Double> sortiranaLista = new ArrayList<>(listaCena);
        Collections.sort(sortiranaLista);
        Collections.reverse(sortiranaLista);
        assert sortiranaLista.equals(listaCena) : WRONG_PRICE_SORT;

    }

    /**
     * CASE 3: Filter ads by locations
     * PRECONDITION: Sign in with valid credentials
     * Test steps:
     * 1. Upon login click on KupujemProdajemTitleIcon and redirect to base page
     * 2. Click inside search field
     * 3. Enter search keyword into search field
     * 4. Click on "Trazi" button
     * 5. Click on "Lokacija" drop-down
     * 6. Select "Kragujevac"
     * 7. Click on "Trazi" button
     * 6. Extract data from "Mesto/Grad" column into a list
     * <p>
     * Expected result:
     * 1. Verify that the user is redirected to the Search page URL
     * 2. Verify that url contains location parameter when the location filter is applied
     * 3. Verify that each element in the list of locations is equal to "Kragujevac"
     */
    @Test
    public void sortByLocation() {
        searchForKeyword();
        basePage.jsClick(basePage.getLocationFilter());
        basePage.jsClick(basePage.getLocationKragujevac());
        SearchPage searchPage = new SearchPage(driver);
        searchPage.jsClick(searchPage.getSearchButton());
        wait.until(ExpectedConditions.urlContains(LOCATION_ID));
        for (int i = 1; i < searchPage.getLocationPlace().size(); i++){
            assert searchPage.getLocationPlace().get(i).getText().equals(KRAGUJEVAC): "Pogresna lokacija, dobijeno: " + i + ", ocekivano: Kragujevac";

        }
    }

    private double konvertujUDIN(String vrednostUEUR) throws ParseException {
        basePage.enterValueIntoNbsAmountField(vrednostUEUR);
        return basePage.getExchangedValue();
    }

    private void searchForKeyword() {
        clearPreviousData();
        login(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains(WELCOME));
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        welcomePage.clickKupujemProdajemTitleIcon();
        basePage.enterTextInSearchField(AUTO_NEWLINE);
        wait.until(ExpectedConditions.urlContains(SEARCH));
    }
}
