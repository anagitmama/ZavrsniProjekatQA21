package Tests;

import Pages.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static Pages.Strings.*;

public class FollowUnfollowTest extends BaseTest {

    @BeforeTest
    public void initDriver() {
        OpenChromeDriver();
    }

    @AfterTest
    public void killDriver() {
        driver.quit();
    }

    /**
     * PRECONDITION: Sign in with valid credentials
     * Test steps:
     * 1. Upon login click on KupujemProdajemTitleIcon and redirect to welcome page
     * 2. Click inside search field
     * 3. Enter text into search field
     * 4. Click on "Trazi" button
     * <p>
     * Expected result:
     * 1. Verify that the user is redirected to the Search page URL
     * 2. Verify that first displayed result contains searched keyword
     */


    @Test(priority = 0)
    public void followProduct() throws InterruptedException {
        clearPreviousData();
        login(VALID_EMAIL, VALID_PASSWORD);
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        wait.until(ExpectedConditions.urlContains(WELCOME));
        welcomePage.clickKupujemProdajemTitleIcon();
        welcomePage.enterTextinSearchField(AUTO_NEWLINE);
        wait.until(ExpectedConditions.urlContains(SEARCH));
        String currentURL = driver.getCurrentUrl();
        boolean b = currentURL.contains(SEARCH);
        assert b : NOT_ON_SEARCH_RESULTS;
        SearchPage searchPage = new SearchPage(driver);
        boolean e = searchPage.getAdTitleFromList(0).contains(AUTO);
        assert e : SEARCH_KEYWORD_NOT_PRESENT;
        BasePage basePage = new BasePage(driver);
        basePage.clickOnAdFromList(0);
        wait.until(ExpectedConditions.urlContains(AD));
        assert basePage.isElementPresent(basePage.getSendMessageButton()) : NOT_ON_SEARCH_RESULTS;
        int numberOfFollowedAds = basePage.getNumberOfFollowedAds();
        searchPage.followAd();
        Thread.sleep(1000);
        basePage.clickKupujemProdajemTitleIcon();
        int newNumberOfFollowedAds = basePage.getNumberOfFollowedAds();
        assert numberOfFollowedAds + 1 == newNumberOfFollowedAds : AD_NOT_FOLLOWED;
    }

    @Test(priority = 1)
    public void cleanFavourites() throws InterruptedException {
        clearPreviousData();
        login(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains(WELCOME));
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        welcomePage.clickKupujemProdajemTitleIcon();
        BasePage basePage = new BasePage(driver);
        basePage.clickOnFollowedAds();
        UserFavouritesPage favoritesPage = new UserFavouritesPage(driver);
        favoritesPage.unfollowFavourites();
        Thread.sleep(500);
        basePage.clickKupujemProdajemTitleIcon();
        Thread.sleep(2500);
        assert basePage.getNumberOfFollowedAds() == 0 : NOT_UNFOLLOWED;
    }
}

