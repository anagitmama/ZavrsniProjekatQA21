package Tests;

import Pages.BasePage;
import Pages.SearchPage;
import Pages.Strings;
import Pages.UserWelcomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.DoubleAccumulator;

public class SearchTest extends BaseTest{
    public WebDriver driver;
    public BasePage basePage;
    @BeforeTest
    public void initDriver(){
        driver = OpenChromeDriver();
        basePage = new BasePage(driver);
    }

    @AfterTest
    public void killDriver(){
        driver.quit();
    }

    @Test
    public void searchForProduct() throws InterruptedException {
        searchForKeyword();
        String currentURL = driver.getCurrentUrl();
        boolean b = currentURL.contains("search");
        assert b: "Ne nalazi se na rezultatima pretrage";
        SearchPage searchPage = new SearchPage(driver);
        searchPage.getAdTitleFromList(0);
        boolean e = searchPage.getAdTitleFromList(0).contains("auto");
        assert e : "Ne sadrzi keyword pretrage";
    }

    private void searchForKeyword() {
        clearPreviousData();
        login(Strings.VALID_EMAIL, Strings.VALID_PASSWORD);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlContains("welcome"));
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        welcomePage.clickKupujemProdajemTitleIcon();
        basePage.enterTextinSearchField("auto\n");
    }

    @Test
    public void sortingByHighestPrice() throws InterruptedException, ParseException {
        searchForKeyword();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlContains("search"));
        SearchPage searchPage = new SearchPage(driver);
        searchPage.jsClick(searchPage.getSortPrices());
        searchPage.jsClick(searchPage.getSkupljeSort());
        searchPage.jsClick(searchPage.getSearchButton());
        Thread.sleep(3000);
        searchPage.getAdPrices().forEach(webElement -> System.out.println(webElement.getText()));
        System.out.println(searchPage.getAdPrices().size());
        List<Double> listaCena = new ArrayList<>();
        for (int i = 1; i < searchPage.getAdPrices().size(); i++) {
            String[] cena = searchPage.getAdPrices().get(i).getText().split(" |\\n");
            System.out.println(cena[1]);
            if (cena[1].equals("€")){
                listaCena.add(konvertujUDIN(cena[0]));
            }else{
                NumberFormat format = NumberFormat.getInstance(Locale.ITALIAN);
                listaCena.add(format.parse(cena[0]).doubleValue());
            }
        }
        List<Double> sortiranaLista = new ArrayList<>();
        sortiranaLista.addAll(listaCena);
        Collections.sort(sortiranaLista);
        Collections.reverse(sortiranaLista);
        assert sortiranaLista.equals(listaCena): "Cene nisu sortirane dobro";

    }
    public double konvertujUDIN(String vrednostUEUR) throws ParseException {
    basePage.enterValueIntoNbsAmountField(vrednostUEUR);
    return basePage.getExchangedValue();
    }

    @Test
    public void sortByLocation(){
        searchForKeyword();
        basePage.jsClick(basePage.getLocationSort());
        basePage.jsClick(basePage.getLocationKragujevac());
        SearchPage searchPage = new SearchPage(driver);
        searchPage.jsClick(searchPage.getSearchButton());
        List<WebElement> elementiLokacije = new ArrayList<>(searchPage.getLocationPlace());
        elementiLokacije.remove(0);
        elementiLokacije.forEach(i -> {
            assert i.getText().equals("Kragujevac") : "Pogresna lokacija, dobijeno: "+ i.getText() +", ocekivano: Kragujevac";
        });
    }
}
