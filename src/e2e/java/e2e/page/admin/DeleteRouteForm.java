package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DeleteRouteForm {
    private WebDriver webDriver;

    DeleteRouteForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findRouteDropdown() {
        return webDriver.findElement(By.className("delete-route__route-dropdown"));
    }

    public WebElement findRouteDropdownOption(String routeName) {
        return webDriver.findElement(By.className("delete-route__route-dropdown-option-" + routeName));
    }

    public WebElement findDeleteButton() {
        return webDriver.findElement(By.className("delete-route__submit-button"));
    }
}
