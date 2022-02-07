package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserWelcomePage extends BasePage {

    @FindBy(xpath = "//*[@data-role='auth-link']")
    WebElement logOutButton;


    public UserWelcomePage(WebDriver driver) {
        super(driver);
    }

    public void assertLogoutButtonPresent() {
        wait.until(ExpectedConditions.visibilityOf(logOutButton));
        assert isElementPresent(logOutButton) : "Error log out button not present";
        assert logOutButton.getText().equals("Izlogujte se") : "ne radi";
    }


}
