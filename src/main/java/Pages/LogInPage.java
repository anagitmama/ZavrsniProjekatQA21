package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LogInPage extends BasePage {

    @FindBy(xpath = "//*[@id='email']")
    WebElement emailField;

    @FindBy(xpath = "//*[@id='password']")
    WebElement passwordField;

    @FindBy(xpath = "//*[@id='submitButton']")
    WebElement submitLogInButton;

    @FindBy(xpath = "//*[@error-name='email_format' and @class='kp-error']")
    WebElement invalidCredentialsError;

    @FindBy(xpath = "//*[@error-name='user_password_match' and @class='kp-error']")
    WebElement invalidPasswordErrorBox;

    @FindBy(xpath = "//*[@error-name='email_required' and @class='kp-error']")
    WebElement emptyEmailFieldErrorBox;

    @FindBy(xpath = "//*[@error-name='user_email_exist' and @class='kp-error']")
    WebElement unknownEmailAdress;

    @FindBy(xpath = "//*[@error-name='email_required' and @class='kp-error']")
    WebElement emailRequired;

    @FindBy(xpath = "//*[@error-name='password_required' and @class='kp-error']")
    WebElement passwordRequired;

    public LogInPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        assert isElementPresent(emailField) : "Error login form is not open";
        emailField.click();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        assert isElementPresent(passwordField) : "Error login form is not open";
        passwordField.click();
        passwordField.sendKeys(password);

    }

    public void clickSubmitLogInButton() {
        submitLogInButton.click();
    }

    public void assertInvalidLogIn() {
        wait.until(ExpectedConditions.visibilityOf(invalidCredentialsError));
        assert isElementPresent(invalidCredentialsError) : "Error invalid credentials";
        assert invalidCredentialsError.getText().equals("Neispravan format e-mail adrese") : "ne radi";
    }


    public void assertValidEmailInvalidPasswordLogIn() {
        wait.until(ExpectedConditions.visibilityOf(invalidPasswordErrorBox));
        assert isElementPresent(invalidPasswordErrorBox) : "Error invalid password";
        assert invalidPasswordErrorBox.getText().equals("Pogrešna lozinka") : "ne radi";
    }

    public void assertNonexistentEmailValidPasswordLogIn() {
        wait.until(ExpectedConditions.visibilityOf(unknownEmailAdress));
        assert isElementPresent(unknownEmailAdress) : "Error invalid email";
        assert unknownEmailAdress.getText().equals("Nepoznata e-mail adresa") : "ne radi";
    }

    public void assertEmptyCredentialsLogIn() {
        wait.until(ExpectedConditions.visibilityOf(emailRequired));
        wait.until(ExpectedConditions.visibilityOf(passwordRequired));
        assert emailRequired.getText().equals("Polje ne može biti prazno") : "ne radi";
        assert passwordRequired.getText().equals("Polje ne može biti prazno") : "ne radi";
    }

}
