package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddDriverForm {
    private WebDriver webDriver;

    AddDriverForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findDriverEmailField() {
        return webDriver.findElement(By.className("add-driver__email"));
    }

    public WebElement findDriverPasswordField() {
        return webDriver.findElement(By.className("add-driver__password"));
    }

    public WebElement findAddButton() {
        return webDriver.findElement(By.className("add-driver__submit-button"));
    }
}
