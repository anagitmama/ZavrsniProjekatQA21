package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UserFavouritesPage extends BasePage {
    @FindBy(xpath = "//*[@action-item='unfollow']")
    List<WebElement> unfollowAdList;

    public UserFavouritesPage(WebDriver driver) {
        super(driver);
    }

    public void unfollowFavourites() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        for (WebElement webElement : unfollowAdList) {
            jse.executeScript(Strings.JS_CLICK, webElement);
        }
    }

}
