package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddRouteForm {
    private WebDriver webDriver;

    public AddRouteForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findNameField() {
        return webDriver.findElement(By.className("add-route__route-name"));
    }

    public WebElement findAddButton() {
        return webDriver.findElement(By.className("add-route__submit-button"));
    }
}
