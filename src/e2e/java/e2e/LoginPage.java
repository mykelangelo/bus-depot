package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class LoginPage {
    private static final String LOGIN_PAGE_URL = "http://localhost:8080/login";
    private final WebDriver driver;

    LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    void goToPage() {
        driver.get(LOGIN_PAGE_URL);
    }

    WebElement findEmailField() {
        return driver.findElement(By.className("login__email"));
    }

    WebElement findPasswordField() {
        return driver.findElement(By.className("login__password"));
    }

    WebElement findSubmitButton() {
        return driver.findElement(By.className("login__submit"));
    }

    String getPageUrl() {
        return LOGIN_PAGE_URL;
    }

    WebElement findLoginErrorMessage() {
        return driver.findElement(By.className("login__error-message"));
    }
}
