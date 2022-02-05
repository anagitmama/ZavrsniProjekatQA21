package Tests;

import Pages.BasePage;
import Pages.Strings;
import Pages.UserWelcomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class E2Etest extends BaseTest{
    public WebDriver driver;
    @BeforeTest
    public void initDriver(){driver = OpenChromeDriver();
    }

    @AfterTest
    public void killDriver(){
        driver.quit();
    }

    @Test(priority = 0)
    public void E2Etest() throws InterruptedException {
        clearPreviousData();
        login(Strings.VALID_EMAIL, Strings.VALID_PASSWORD);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlToBe("https://www.kupujemprodajem.com/user.php?action=welcome"));
        assert this.isCurrentURLEqualTo("https://www.kupujemprodajem.com/user.php?action=welcome") : "Wrong page!";
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        welcomePage.clickKupujemProdajemTitleIcon();
        BasePage basePage = new BasePage(driver);
        wait.until(ExpectedConditions.urlToBe("https://www.kupujemprodajem.com/index.php"));
        assert this.isCurrentURLEqualTo("https://www.kupujemprodajem.com/index.php") : "Wrong page!";
        basePage.isElementPresent(basePage.getSearchInputField());
        basePage.getSearchInputField().sendKeys("auto\n");
//      basePage.getSubmitSearchButton().click();
        basePage.getAdFromList(0);
        basePage.clickOnAdFromList(0);
        assert basePage.isElementPresent(basePage.getOglasTitle());
        print(basePage.getOglasTitle().getText());
    }


    }

