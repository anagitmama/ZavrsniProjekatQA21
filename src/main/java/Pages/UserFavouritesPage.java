package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UserFavouritesPage extends BasePage {
    @FindBy(xpath = "//*[@id='mojKpFollowedAdsCount']")
    WebElement addsCount;

    public UserFavouritesPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getAddsCount() {
        return addsCount;
    }

    public void unfollowFavourites() {
        List<WebElement> favouritesList = driver.findElements(By.xpath("//*[@action-item='unfollow']"));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        for (int i = 0; i < favouritesList.size(); i++) {
            jse.executeScript("arguments[0].click()", favouritesList.get(i));
        }
    }

}
