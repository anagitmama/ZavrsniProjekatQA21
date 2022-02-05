package Tests;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class RedirectionTest extends BaseTest {
    /**
     * Test Redirection from Top Navigation holder
     * <p>
     * Test steps:
     * 1. Navigate to https://www.kupujemprodajem.com/
     * 2. In header section click KP blog
     * 3. In header section click Reklamirajte svoj biznis
     * 4. In header section click Kako da postavite oglas?
     * 5. In header section click Fejsbuk ikonicu
     * 6. In header section click LinkedIn ikonicu
     * 7. In header section click Instagram
     * <p>
     * Expected result:
     * 2. Verify you are navigated to https://blog.kupujemprodajem.com/
     * 3. Verify you are navigated to https://www.kupujemprodajem.com/malaprivreda
     * 4. Verify you are navigated to https://blog.kupujemprodajem.com/kako-da/kako-da-postavite-prvi-oglas-preko-desktop-sajta/
     * 5. Verify you are navigated to https://www.facebook.com/KupujemProdajem
     * 6. Verify you are navigated to https://www.linkedin.com/company/kupujemprodajem-com/
     * 7. Verify you are navigated to https://www.instagram.com/kupujemprodajem/
     **/

    public WebDriver driver;
    public BasePage basePage;

    @BeforeTest
    public void initDriver() {
        driver = OpenChromeDriver();
        basePage = new BasePage(driver);

    }

    @AfterTest
    public void killDriver() {
        driver.quit();
    }

    @Test(priority = 0)
    public void redirectToKPblog() {
        clickAndAssertNewTab(basePage.getKPBlogRedirect(), "https://blog.kupujemprodajem.com/");
    }

    @Test(priority = 1)
    public void redirectToReklamirajteSvojBiznis() {
        clickAndAssertNewTab(basePage.getReklamirajteSvojBiznisRedirect(), "https://www.kupujemprodajem.com/malaprivreda");
    }

    @Test(priority = 2)
    public void redirectToKakoDaPostaviteOglas() {
        clickAndAssertNewTab(basePage.getKakoDaPostaviteOglasRedirect(), "https://blog.kupujemprodajem.com/kako-da/kako-da-postavite-prvi-oglas-preko-desktop-sajta/");
    }

    @Test(priority = 3)
    public void redirectToFacebook() {
        clickAndAssertNewTab(basePage.getFacebookRedirect(), "https://www.facebook.com/KupujemProdajem");
    }

    @Test(priority = 4)
    public void redirectToLinkedIn() {
        clickAndAssertNewTab(basePage.getLinkedInRedirect(), "https://www.linkedin.com/company/kupujemprodajem-com");
    }

    @Test(priority = 5)
    public void redirectToInstagram() {
        try {
            clickAndAssertNewTab(basePage.getInstagramRedirect(), "https://www.instagram.com/accounts/login/");
        } catch (AssertionError e){
            clickAndAssertNewTab(basePage.getInstagramRedirect(), "https://www.instagram.com/kupujemprodajem/");
        }
    }

    private void closeTab(String mainTab) {
        if (!driver.getWindowHandle().equals(mainTab)) {
            driver.close();
        }
        driver.switchTo().window(mainTab);
    }

    private void clickAndAssertNewTab(WebElement linkElement, String expectedUrl) {
        String mainTab = null;
        try {
            mainTab = driver.getWindowHandle();
            clearPreviousData();
            basePage.closeLogInModal();
            basePage.click(linkElement);
            List<String> tabs = new ArrayList<>(driver.getWindowHandles());
            tabs.remove(mainTab);
            driver.switchTo().window(tabs.get(0));
            assert isCurrentURLEqualTo(expectedUrl) : "Wrong page!";
        } finally {
            closeTab(mainTab);
        }
    }

}