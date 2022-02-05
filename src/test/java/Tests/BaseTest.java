package Tests;

import Pages.BasePage;
import Pages.LogInPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    WebDriver driver = null;

    public WebDriver OpenChromeDriver(){
        print("Opening ChromeDriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(new String []{"--ignore-certificate-errors"});
        options.addArguments(new String []{"--disable-popup-blocking"});
        options.addArguments(new String []{"--incognito"});
        driver = new ChromeDriver(options);
        driver.get("https://www.kupujemprodajem.com/");
        return driver;
    }

    protected LogInPage login(String username, String password){
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

    public boolean isCurrentURLEqualTo(String expectedURL){
        print("is current URL equal to (" + expectedURL+ ")");
        String currentURL = driver.getCurrentUrl();
        print("User is on " + currentURL);
        boolean b = currentURL.equals(expectedURL);
        return b;
    }
    // Clear Cookies on the browser
    public void clearPreviousData() {
        driver.manage().deleteAllCookies();
        driver.get("https://www.kupujemprodajem.com");
    }

}
