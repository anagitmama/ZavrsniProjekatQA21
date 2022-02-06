package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class BasePage {

    WebDriver driver;
    WebDriverWait wait;
    @FindBy(className = "kpBoxCloseButton")
    WebElement kpBoxCloseLogInModalButton;
    @FindBy(className = "kpBoxContentHolder")
    WebElement kpBoxContentHolder;
    @FindBy(partialLinkText = "Ulogujte se")
    WebElement logInButton;
    @FindBy(xpath = "//*[text()='KP Blog']")
    WebElement KPBlogRedirect;
    @FindBy(xpath = "//*[text()= 'Reklamirajte svoj biznis']")
    WebElement reklamirajteSvojBiznisRedirect;
    @FindBy(className = "atractive-link")
    WebElement kakoDaPostaviteOglasRedirect;
    @FindBy(className = "fb")
    WebElement facebookRedirect;
    @FindBy(className = "li")
    WebElement linkedInRedirect;
    @FindBy(className = "instagram")
    WebElement instagramRedirect;
    @FindBy(xpath = "//*[@class='nbs-widget squareBorder']")
    WebElement NBSwidget;
    @FindBy(xpath = "//*[@id='caption' and text()='EUR']")
    WebElement EURdropdown;
    @FindBy(xpath = "//*[@data-value='RSD']")
    WebElement RSDValue;
    @FindBy(xpath = "//*[@name='nbsAmount']")
    WebElement nbsAmount;
    @FindBy(xpath = "//*[@name='exchanges']/table[1]/tbody/tr[2]/td[2]")
    WebElement exchangeRate;
    @FindBy(xpath = "//*[@id='nbsConverted']")
    WebElement convertedValue;
    @FindBy(xpath = "//*[@class='searchKeywordsInput']")
    WebElement searchInputField;
    @FindBy(xpath = "//*[@class='searchButton']")
    WebElement submitSearchButton;
    @FindBy(xpath = "//*[contains(@id, 'adDescription')]")
    WebElement adList;
    @FindBy(xpath = "//*[@class='adName']")
    WebElement adName;
    @FindBy(xpath = "//*[@class='oglas-title']")
    WebElement oglasTitle;
    @FindBy(xpath = "//*[@class='sendMessageBtn3 ']")
    WebElement sendMessageButton;
    @FindBy(xpath = "//*[@id='searchKeywordsInput']")
    WebElement searchKeywordsInputField;
    @FindBy(xpath = "//*[text()='Mesto/Grad']")
    WebElement locationSort;
    @FindBy(xpath = "//*[@data-text='Kragujevac']")
    WebElement locationKragujevac;
    @FindBy(xpath = "//*[@id='mojKpFollowedAdsCount']")
    WebElement followedAdCount;
    @FindBy(xpath = "//*[@title='KupujemProdajem']")
    WebElement kupujemProdajemTitleIcon;

    /*konstruktor*/
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 15);
    }

    public WebElement getFollowedAdCount() {
        return followedAdCount;
    }

    public WebElement getLocationKragujevac() {
        return locationKragujevac;
    }

    public WebElement getLocationSort() {
        return locationSort;
    }

    public WebElement getRSDValue() {
        return RSDValue;
    }

    public WebElement getSendMessageButton() {
        return sendMessageButton;
    }

    public WebElement getOglasTitle() {
        return oglasTitle;
    }

    public WebElement getSubmitSearchButton() {
        return submitSearchButton;
    }

    public WebElement getSearchInputField() {
        return searchInputField;
    }

    public WebElement getEURdropdown() {
        return EURdropdown;
    }

    public WebElement getNBSwidget() {
        return NBSwidget;
    }

    public WebElement getKpBoxCloseButton() {
        return kpBoxCloseLogInModalButton;
    }

    public WebElement getKpBoxContentHolder() {
        return kpBoxContentHolder;
    }

    public WebElement getLogInButton() {
        return logInButton;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getKPBlogRedirect() {
        return KPBlogRedirect;
    }

    public WebElement getReklamirajteSvojBiznisRedirect() {
        return reklamirajteSvojBiznisRedirect;
    }

    public WebElement getKakoDaPostaviteOglasRedirect() {
        return kakoDaPostaviteOglasRedirect;
    }

    public WebElement getFacebookRedirect() {
        return facebookRedirect;
    }

    public WebElement getLinkedInRedirect() {
        return linkedInRedirect;
    }

    public WebElement getInstagramRedirect() {
        return instagramRedirect;
    }

    //metode nad web elementima

    public void clickOpenLogInFormButton() {
        click(logInButton);
    }

    public void clickKPBlogRedirect() {
        click(KPBlogRedirect);
    }

    public void clickReklamirajteSvojBiznisRedirect() {
        click(reklamirajteSvojBiznisRedirect);
    }

    public void clickKakoDaPostaviteOglasRedirect() {
        click(kakoDaPostaviteOglasRedirect);
    }

    public void clickFacebookRedirect() {
        click(facebookRedirect);
    }

    public void clickLinkedInRedirect() {
        click(linkedInRedirect);
    }

    public void clickInstagramRedirect() {
        click(instagramRedirect);
    }

    public void click(WebElement element) {
        element.click();
    }


    public void print(String text) {
        System.out.println(text);
    }

    public boolean isElementPresent(WebElement element) {
        print(Strings.IS_ELEMENT_PRESENT);
        try {
            boolean isPresent = element.isDisplayed();
            return isPresent;
        } catch (Exception e) {
            print(e.getMessage());
            print(Strings.ELEMENT_NOT_PRESENT);
            return false;
        }


    }

    public void enterValueIntoNbsAmountField(double amount) {
        enterValueIntoNbsAmountField(Double.toString(amount));
    }

    public void enterValueIntoNbsAmountField(String amount) {
        click(nbsAmount);
        nbsAmount.clear();
        nbsAmount.sendKeys(amount);
    }

    public double getExchangedValue() throws ParseException {
        assert isElementPresent(convertedValue) : Strings.ELEMENT_NOT_PRESENT;
        NumberFormat format = NumberFormat.getInstance(Locale.ITALIAN);
        return format.parse(convertedValue.getText()).doubleValue();
    }

    public double getExchangeRate() throws ParseException {
        assert isElementPresent(exchangeRate) : Strings.ELEMENT_NOT_PRESENT;
        NumberFormat format = NumberFormat.getInstance(Locale.ITALIAN);
        return format.parse(exchangeRate.getText()).doubleValue();

    }

    //neke helper metode

    public void closeLogInModal() {
        kpBoxCloseLogInModalButton.click();
    }

    public void getAdFromList(int i) {
        List<WebElement> adListofElements = driver.findElements(By.xpath("//*[contains(@id, 'adDescription')]"));
        WebElement element = adListofElements.get(i);
        print(element.getText());
    }

    public void clickOnFollowedAds() {
        wait.until(ExpectedConditions.visibilityOf(followedAdCount));
        jsClick(followedAdCount);
    }

    public void clickOnAdFromList(int i) throws InterruptedException {
        List<WebElement> adListofElements = driver.findElements(By.xpath("//*[contains(@class, 'adName')]"));
        WebElement element = adListofElements.get(i);
        wait.until(ExpectedConditions.elementToBeClickable(element));
//        element.click();
//        Thread.sleep(5000);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(Strings.JS_CLICK, element);
    }

    public void enterTextinSearchField(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(searchKeywordsInputField));
        searchKeywordsInputField.click();
        searchKeywordsInputField.sendKeys(text);
    }

    public void jsClick(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(Strings.JS_CLICK, element);
    }

    public void clickKupujemProdajemTitleIcon() {
        click(kupujemProdajemTitleIcon);
        wait.until(ExpectedConditions.urlContains(Strings.INDEX));
    }

    public int getNumberOfFollowedAds() {
        if (driver.findElements(By.xpath("//*[@id='mojKpFollowedAdsCount']")).size() != 0) {
            if (!followedAdCount.getText().isEmpty()) {
                return Integer.parseInt(followedAdCount.getText());
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}

