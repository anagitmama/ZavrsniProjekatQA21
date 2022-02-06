package Tests;

import Pages.BasePage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.ParseException;

import static Pages.Strings.ELEMENT_NOT_PRESENT;
import static Pages.Strings.VALUES_NOT_MATCHING;

public class ExchangeCurrencyTest extends BaseTest {


    public static final double TOLERANCE = 0.001;

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

    @BeforeTest
    public void initDriver(){
        OpenChromeDriver();
    }

    @AfterTest
    public void killDriver() {
        driver.quit();
    }

   @Test
    public void EURtoRSDconversion() throws ParseException, InterruptedException {
       BasePage basePage = new BasePage(driver);
       basePage.closeLogInModal();
       assert basePage.isElementPresent(basePage.getNBSwidget()) : ELEMENT_NOT_PRESENT;
       double amount = 100 ;
       basePage.enterValueIntoNbsAmountField(amount);
       double exchangedValue = basePage.getExchangedValue();
       print(Double.toString(exchangedValue));
       double expectedValue = amount*basePage.getExchangeRate();
       assert Math.abs(expectedValue - exchangedValue) < TOLERANCE : VALUES_NOT_MATCHING;
   }
    @Test
    public void RSDtoEURconversion() throws InterruptedException, ParseException {
        BasePage basePage = new BasePage(driver);
        basePage.closeLogInModal();
        assert basePage.isElementPresent(basePage.getNBSwidget()) : ELEMENT_NOT_PRESENT;
        basePage.getEURdropdown().click();
        basePage.getRSDValue().click();
        Thread.sleep(3000);
        double amount = 2000;
        basePage.enterValueIntoNbsAmountField(amount);
        double exchangedValue = basePage.getExchangedValue();
        print(Double.toString(exchangedValue));
        double expectedValue = amount/basePage.getExchangeRate();
        assert Math.abs(expectedValue - exchangedValue) < TOLERANCE : VALUES_NOT_MATCHING;
    }

   }




