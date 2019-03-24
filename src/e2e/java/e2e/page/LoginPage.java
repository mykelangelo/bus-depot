package e2e.page;

import e2e.constant.ApplicationEndpointsURLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private final WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void goToPage() {
        webDriver.get(ApplicationEndpointsURLs.LOGIN_PAGE_URL);
    }

    public WebElement findEmailField() {
        return webDriver.findElement(By.className("login__email"));
    }

    public WebElement findPasswordField() {
        return webDriver.findElement(By.className("login__password"));
    }

    public WebElement findLogInButton() {
        return webDriver.findElement(By.className("login__submit"));
    }

    public WebElement findLoginErrorMessage() {
        return webDriver.findElement(By.className("login__error-message"));
    }

    public WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("login__greeting-message"));
    }

    public WebElement findLanguagesDropdown() {
        return webDriver.findElement(By.className("login__language-dropdown"));
    }

    public WebElement findLanguagesDropdownOption(String language) {
        return webDriver.findElement(By.className("login__language-dropdown-option-" + language));
    }
}
