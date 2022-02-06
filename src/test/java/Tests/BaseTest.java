package Tests;

import Pages.BasePage;
import Pages.LogInPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Pages.Strings.*;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public void OpenChromeDriver() {
        print(OPENING_CHROME_DRIVER);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.get(HOME_PAGE);
        wait = new WebDriverWait(driver, 15);
    }

    protected LogInPage login(String username, String password) {
        BasePage basePage = new BasePage(driver);
        basePage.closeLogInModal();
        basePage.clickOpenLogInFormButton();
        LogInPage logInPage = new LogInPage(driver);
        logInPage.enterEmail(username);
        logInPage.enterPassword(password);
        logInPage.clickSubmitLogInButton();
        return logInPage;
    }

    public void print(String text) {
        System.out.println(text);
    }

    public boolean isCurrentURLEqualTo(String expectedURL) {
        String currentURL = driver.getCurrentUrl();
        print(USER_IS_ON + currentURL);
        boolean b = currentURL.equals(expectedURL);
        return b;
    }

    // Clear Cookies on the browser
    public void clearPreviousData() {
        driver.manage().deleteAllCookies();
        driver.get(HOME_PAGE);
    }

}
