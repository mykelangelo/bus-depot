package e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private static final String LOGIN_PAGE_URL = "http://localhost:8080/login";
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public static String getPageUrl() {
        return LOGIN_PAGE_URL;
    }

    public void goToPage() {
        driver.get(LOGIN_PAGE_URL);
    }

    public WebElement findEmailField() {
        return driver.findElement(By.className("login__email"));
    }

    public WebElement findPasswordField() {
        return driver.findElement(By.className("login__password"));
    }

    public WebElement findSubmitButton() {
        return driver.findElement(By.className("login__submit"));
    }

    public WebElement findLoginErrorMessage() {
        return driver.findElement(By.className("login__error-message"));
    }
}
