package Tests;

import Pages.LogInPage;
import Pages.UserWelcomePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static Pages.Strings.*;

public class AuthenticationTest extends BaseTest {


    @BeforeTest
    public void initDriver() {
        OpenChromeDriver();
    }

    @AfterTest
    @AfterSuite
    public void killDriver() {
        driver.quit();
    }

    /**
     * CASE 1: Sign in with valid credentials
     * Test steps:
     * 1. Navigate to https://www.kupujemprodajem.com/
     * 2. Turn off pop up dialog (Chrome does not block it as predicted)
     * 3. Click on "Ulogujte se" from the header navigation menu
     * 4. Enter valid email in email input field
     * 5. Enter valid password in password input field
     * 6. Click on "Ulogujte se" button
     * Expected result:
     * 1. Verify that the user is on the Welcome page URL
     * 2. Verify that the "Izlogujte se" button is displayed on the header navigation menu of Welcome Page
     */
    @Test(priority = 0)
    public void testLogInWithValidCredentials() {
        clearPreviousData();
        login(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlToBe(WELCOME_PAGE_URL));
        assert this.isCurrentURLEqualTo(WELCOME_PAGE_URL) : WRONG_PAGE_MESSAGE;
        UserWelcomePage welcomePage = new UserWelcomePage(driver);
        welcomePage.assertLogoutButtonPresent();
    }

    /**
     * CASE 2: Login test with Invalid Email and Invalid Password
     * <p>
     * Test steps:
     * 1. Navigate to https://www.kupujemprodajem.com/
     * 2. Turn off pop up dialog (Chrome does not block it as predicted)
     * 3. Click on "Ulogujte se" from the header navigation menu
     * 4. Enter Invalid email in email input field
     * 5. Enter Invalid password in password input field
     * 6. Click on "Ulogujte se" button
     * Expected result:
     * 1. Verify that the user is not on the Welcome page URL
     * 2. Verify that the "Error invalid password" error box is present
     */

    @Test(priority = 1)
    public void
    testLogInWithInvalidEmailInvalidPassword() {
        clearPreviousData();
        LogInPage login = login(INVALID_EMAIL, INVALID_PASSWORD);
        assert !this.isCurrentURLEqualTo(WELCOME_PAGE_URL) : WRONG_PAGE_MESSAGE;
        login.assertInvalidLogIn();
    }

    /**
     * CASE 3: Login test with valid Email and Invalid Password
     * <p>
     * Test steps:
     * 1. Navigate to https://www.kupujemprodajem.com/
     * 2. Turn off pop up dialog (Chrome does not block it as predicted)
     * 3. Click on "Ulogujte se" from the header navigation menu
     * 4. Enter valid email in email input field
     * 5. Enter Invalid password in password input field
     * 6. Click on "Ulogujte se" button
     * Expected result:
     * 1. Verify that the user is not on the Welcome page URL
     * 2. Verify that the "Error invalid password" error box is present
     */
    @Test(priority = 2)
    public void testLogInWithValidEmailInvalidPassword() {
        clearPreviousData();
        LogInPage login = login(VALID_EMAIL, INVALID_PASSWORD);
        assert !this.isCurrentURLEqualTo(WELCOME_PAGE_URL) : WRONG_PAGE_MESSAGE;
        login.assertValidEmailInvalidPasswordLogIn();
    }

    /**
     * CASE 4: Login test with NonExistent Email and valid Password
     * <p>
     * Test steps:
     * 1. Navigate to https://www.kupujemprodajem.com/
     * 2. Turn off pop up dialog (Chrome does not block it as predicted)
     * 3. Click on "Ulogujte se" from the header navigation menu
     * 4. Enter NonExistent email in email input field
     * 5. Enter valid password in password input field
     * 6. Click on "Ulogujte se" button
     * Expected result:
     * 1. Verify that the user is not on the Welcome page URL
     * 2. Verify that the "Error invalid email" error box is present
     */
    @Test(priority = 3)
    public void testLogInWithNonExistentEmailValidPassword() {
        clearPreviousData();
        LogInPage login = login(NONEXISTENT_EMAIL, VALID_PASSWORD);
        assert !this.isCurrentURLEqualTo(WELCOME_PAGE_URL) : WRONG_PAGE_MESSAGE;
        login.assertNonexistentEmailValidPasswordLogIn();
    }

    /**
     * CASE 5: Login test with empty credentials
     * <p>
     * Test steps:
     * 1. Navigate to https://www.kupujemprodajem.com/
     * 2. Turn off pop up dialog (Chrome does not block it as predicted)
     * 3. Click on "Ulogujte se" from the header navigation menu
     * 4. Leave empty email input field
     * 5. Leave empty password input field
     * 6. Click on "Ulogujte se" button
     * Expected result:
     * 1. Verify that the user is not on the Welcome page URL
     * 2. Verify that the "email required" error box is present
     * 3. Verify that the "password required" error box is present
     */
    @Test(priority = 4)
    public void testLogInWithEmptyCredentials() {
        clearPreviousData();
        LogInPage login = login(EMPTY_STRING, EMPTY_STRING);
        assert !this.isCurrentURLEqualTo(WELCOME_PAGE_URL) : WRONG_PAGE_MESSAGE;
        login.assertEmptyCredentialsLogIn();
    }


}
