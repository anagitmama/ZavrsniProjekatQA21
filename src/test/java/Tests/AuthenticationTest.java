package Tests;

import Pages.BasePage;
import Pages.LogInPage;
import Pages.Strings;
import Pages.UserWelcomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AuthenticationTest extends BaseTest{

    public WebDriver driver;
    @BeforeTest
    public void initDriver(){driver = OpenChromeDriver();
    }

    @AfterTest
    public void killDriver(){
        driver.quit();
    }

    /**
     * CASE 1: Sign in with valid credentials
     Test steps:
     1. Navigate to https://www.kupujemprodajem.com/
     2. Turn off pop up dialog (Chrome does not block it as predicted)
     3. Click on "Ulogujte se" from the header navigation menu
     4. Enter valid email in email input field
     5. Enter valid password in password input field
     6. Click on "Ulogujte se" button
     Expected result:
     1. Verify that the user is on the Welcome page URL
     2. Verify that the "Izlogujte se" button is displayed on the header navigation menu of Welcome Page
     */
    @Test(priority = 0)
    public void testLogInWithValidCredentials(){
        clearPreviousData();
        login(Strings.VALID_EMAIL, Strings.VALID_PASSWORD);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlToBe("https://www.kupujemprodajem.com/user.php?action=welcome"));
        assert this.isCurrentURLEqualTo("https://www.kupujemprodajem.com/user.php?action=welcome") : "Wrong page!";
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        welcomePage.assertLogoutButtonPresent();
    }
    @Test(priority = 1)
    public void
    testLogInWithInvalidEmailInvalidPassword(){
        clearPreviousData();
        LogInPage login = login(Strings.INVALID_EMAIL, Strings.INVALID_PASSWORD);
        assert !this.isCurrentURLEqualTo("https://www.kupujemprodajem.com/user.php?action=welcome") : "Wrong page!";
        login.assertInvalidLogIn();
    }
    @Test(priority = 2)
    public void testLogInWithValidEmailInvalidPassword(){
        clearPreviousData();
        LogInPage login = login(Strings.VALID_EMAIL, Strings.INVALID_PASSWORD);
        assert !this.isCurrentURLEqualTo("https://www.kupujemprodajem.com/user.php?action=welcome") : "Wrong page!";
        login.assertValidEmailInvalidPasswordLogIn();
    }
    @Test(priority = 3)
    public void testLogInWithNonExistentEmailValidPassword(){
        clearPreviousData();
        LogInPage login = login(Strings.NONEXISTENT_EMAIL, Strings.VALID_PASSWORD);
        assert !this.isCurrentURLEqualTo("https://www.kupujemprodajem.com/user.php?action=welcome") : "Wrong page!";
        login.assertNonexistentEmailValidPasswordLogIn();
    }
    @Test(priority = 4)
    public void testLogInWithEmptyCredentials(){
        clearPreviousData();
        LogInPage login = login("","");
        assert !this.isCurrentURLEqualTo("https://www.kupujemprodajem.com/user.php?action=welcome") : "Wrong page!";
        login.assertEmptyCredentialsLogIn();
    }


}
