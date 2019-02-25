package e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private static final String LOGIN_PAGE_URL = "http://localhost:8080/login";
    private final WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public static String getPageUrl() {
        return LOGIN_PAGE_URL;
    }

    public void goToPage() {
        webDriver.get(LOGIN_PAGE_URL);
    }

    public WebElement findEmailField() {
        return webDriver.findElement(By.className("login__email"));
    }

    public WebElement findPasswordField() {
        return webDriver.findElement(By.className("login__password"));
    }

    public WebElement findSubmitButton() {
        return webDriver.findElement(By.className("login__submit"));
    }

    public WebElement findLoginErrorMessage() {
        return webDriver.findElement(By.className("login__error-message"));
    }
}
