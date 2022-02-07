package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchPage extends BasePage {

    @FindBy(xpath = "//*[@action-item='follow']")
    WebElement followAd;
    @FindBy(xpath = "//*[@class='oglas-option following-true']")
    WebElement adFollowed;
    @FindBy(xpath = "//*[@class='priceSec']")
    List<WebElement> adPrices;
    @FindBy(xpath = "//*[@class='locationSec']")
    List<WebElement> locationPlace;
    @FindBy(xpath = " //*[@class='searchButton secondarySearchButton']")
    WebElement searchButton;
    @FindBy(xpath = "//*[text()='Skuplje']")
    WebElement skupljeSort;
    @FindBy(xpath = "//*[text()='Sortiraj']")
    WebElement sortPrices;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getFollowAd() {
        return followAd;
    }

    public WebElement getAdFollowed() {
        return adFollowed;
    }

    public List<WebElement> getAdPrices() {
        return adPrices;
    }

    public List<WebElement> getLocationPlace() {
        return locationPlace;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public WebElement getSortPrices() {
        return sortPrices;
    }

    public WebElement getSkupljeSort() {
        return skupljeSort;
    }

    public String getAdTitleFromList(int i) {
        List<WebElement> adListofElements = driver.findElements(By.xpath("//*[contains(@id, 'adDescription')]"));
        WebElement element = adListofElements.get(i);
        print(element.getText());
        String adTitle = element.getText();
        return adTitle;
    }

    public void followAd() {
        wait.until(ExpectedConditions.elementToBeClickable(followAd));
        click(followAd);
    }


}


