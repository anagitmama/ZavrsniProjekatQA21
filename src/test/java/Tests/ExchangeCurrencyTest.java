package Tests;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.ParseException;

public class ExchangeCurrencyTest extends BaseTest {

    /**
     *
     * Test conversion EUR to RSD currency in NBSwidget
     * PRECONDITION: Clear previous entries from amount to exchange field
     * <p>
     * Test steps:
     * 1. Navigate to https://www.kupujemprodajem.com/
     * 2. Close LogIn modal
     * 3. Enter desired amount into exchange NBS amount field
     * <p>
     * Expected result:
     * 1. Verify that calculated exchange value matches with value in exchanged value field on NBSwidget
     *
     **/

    public WebDriver driver;
    @BeforeTest
    public void initDriver(){
        driver = OpenChromeDriver();
    }

    @AfterTest
    public void killDriver() {
        driver.quit();
    }

   @Test
    public void EURtoRSDconversion() throws ParseException, InterruptedException {
       BasePage basePage = new BasePage(driver);
       basePage.closeLogInModal();
       assert basePage.isElementPresent(basePage.getNBSwidget()) : "Element nije prisutan";
       double amount = 200 ;
       basePage.enterValueIntoNbsAmountField(amount);
       double exchangedValue = basePage.getExchangedValue();
       print(Double.toString(exchangedValue));
       double expectedValue = amount*basePage.getExchangeRate();
       System.out.println(expectedValue);
       assert Math.abs(expectedValue - exchangedValue) < 0.001 : "Values not matching";
       Thread.sleep(3000);
   }
    @Test
    public void RSDtoEURconversion() throws InterruptedException, ParseException {
        BasePage basePage = new BasePage(driver);
        basePage.closeLogInModal();
        assert basePage.isElementPresent(basePage.getNBSwidget()) : "Element nije prisutan";
        basePage.getEURdropdown().click();
        basePage.getRSDValue().click();
        Thread.sleep(3000);
        double amount = 2000;
        basePage.enterValueIntoNbsAmountField(amount);
        double exchangedValue = basePage.getExchangedValue();
        print(Double.toString(exchangedValue));
        double expectedValue = amount/basePage.getExchangeRate();
        System.out.println(expectedValue);
        assert Math.abs(expectedValue - exchangedValue) < 0.001 : "Values not matching";
        Thread.sleep(3000);
    }

   }




