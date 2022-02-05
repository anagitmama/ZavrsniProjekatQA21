package Tests;

import Pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FollowUnfollowTest extends BaseTest{

    public WebDriver driver;
    @BeforeTest
    public void initDriver(){driver = OpenChromeDriver();
    }

    @AfterTest
    public void killDriver(){
        driver.quit();
    }

    /**
     PRECONDITION: Sign in with valid credentials
     Test steps:
     1. Upon login click on KupujemProdajemTitleIcon and redirect to welcome page
     2. Click inside search field
     3. Enter text into search field
     4. Click on "Trazi" button

     Expected result:
     1. Verify that the user is redirected to the Search page URL
     2. Verify that first displayed result contains searched keyword
     */



    @Test
    public void followProduct() throws InterruptedException {
        clearPreviousData();
        login(Strings.VALID_EMAIL, Strings.VALID_PASSWORD);
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        Thread.sleep(1000);
        welcomePage.clickKupujemProdajemTitleIcon();
        welcomePage.enterTextinSearchField("auto\n");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlContains("search"));
        String currentURL = driver.getCurrentUrl();
        boolean b = currentURL.contains("search");
        assert b: "Ne nalazi se na rezultatima pretrage";
        SearchPage searchPage = new SearchPage(driver);
        searchPage.getAdTitleFromList(0);
        boolean e = searchPage.getAdTitleFromList(0).contains("auto");
        assert e : "Ne sadrzi keyword pretrage";
        BasePage basePage = new BasePage(driver);
        basePage.clickOnAdFromList(0);
        assert basePage.isElementPresent(basePage.getSendMessageButton()):"Nisi na rezultatu pretrage";
        searchPage.followAdd();

        //WebDriverWait wait = new WebDriverWait(driver, 20);
        //assert searchPage.isElementPresent(searchPage.getAdFollowed()): "Add not followed";
    }

    @Test
    public void cleanFavourites() throws InterruptedException {
        clearPreviousData();
        login(Strings.VALID_EMAIL, Strings.VALID_PASSWORD);
        UserFavouritesPage favoritesPage = new UserFavouritesPage(driver);
        Thread.sleep(5000);
        favoritesPage.getAddsCount().click();
        Thread.sleep(5000);
        favoritesPage.unfollowFavourites();
       // Thread.sleep(5000);
        //assert favoritesPage.getAddsCount().getText().contains("2"): "nije otpraceno";
    }
}

