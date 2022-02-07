package Tests;

import Pages.BasePage;
import Pages.SearchPage;
import Pages.UserFavouritesPage;
import Pages.UserWelcomePage;
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
     * CASE 1: Follow ad
     * PRECONDITION: Sign in with valid credentials
     * Test steps:
     * 1. Upon login click on KupujemProdajemTitleIcon and redirect to base page
     * 2. Click inside search field
     * 3. Enter search keyword into search field
     * 4. Click on "Trazi" button
     * 5. Navigate to ad
     * 6. Click on follow button
     * <p>
     * Expected result:
     * 1. Verify that the user is redirected to the Search page URL
     * 2. Verify that first displayed result contains searched keyword
     * 3. Verify that current page is ad page
     * 4. Verify that follow counter is incremented by 1
     */

    @Test(priority = 0)
    public void followProduct() throws InterruptedException {
        clearPreviousData();
        login(VALID_EMAIL, VALID_PASSWORD);
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        wait.until(ExpectedConditions.urlContains(WELCOME));
        welcomePage.clickKupujemProdajemTitleIcon();
        welcomePage.enterTextInSearchField(AUTO_NEWLINE);
        wait.until(ExpectedConditions.urlContains(SEARCH));
        String currentURL = driver.getCurrentUrl();
        assert currentURL.contains(SEARCH) : NOT_ON_SEARCH_RESULTS;
        SearchPage searchPage = new SearchPage(driver);
        assert searchPage.getAdTitleFromList(0).contains(AUTO) : SEARCH_KEYWORD_NOT_PRESENT;
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

    /**
     * CASE 2: Unfollow all ads
     * PRECONDITION: Sign in with valid credentials
     * PRECONDITION: Have at least one ad followed
     * Test steps:
     * 1. Upon login click on KupujemProdajemTitleIcon and redirect to base page
     * 2. Click on followed ads
     * 3. Unfollow all ads
     * 4. Navigate to base page
     * 5. Fetch the number of all ads
     * <p>
     * Expected result:
     * 1. Verify that the user is redirected to Base page URL
     * 2. Verify that the user is redirected to favourites page URL
     * 3. Verify that number of followed ads equals 0
     */

    @Test(priority = 1)
    public void cleanFavourites() throws InterruptedException {
        clearPreviousData();
        login(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains(WELCOME));
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        welcomePage.clickKupujemProdajemTitleIcon();
        BasePage basePage = new BasePage(driver);
        basePage.clickOnFollowedAds();
        wait.until(ExpectedConditions.urlContains(FAVOURITES));
        UserFavouritesPage favoritesPage = new UserFavouritesPage(driver);
        favoritesPage.unfollowFavourites();
        Thread.sleep(500);
        basePage.clickKupujemProdajemTitleIcon();
        Thread.sleep(2500);
        assert basePage.getNumberOfFollowedAds() == 0 : NOT_UNFOLLOWED;
    }
}

