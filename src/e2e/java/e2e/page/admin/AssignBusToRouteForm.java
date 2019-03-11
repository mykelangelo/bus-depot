package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AssignBusToRouteForm {
    private final WebDriver webDriver;

    AssignBusToRouteForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findRouteDropdown() {
        return webDriver.findElement(By.className("bus-to-route__route-dropdown"));
    }

    public WebElement findRouteDropdownOption(String routeName) {
        return webDriver.findElement(By.className("bus-to-route__route-dropdown-option-" + routeName));
    }

    public WebElement findBusDropdown() {
        return webDriver.findElement(By.className("bus-to-route__bus-dropdown"));
    }

    public WebElement findBusDropdownOption(String busSerialNumber) {
        return webDriver.findElement(By.className("bus-to-route__bus-dropdown-option-" + busSerialNumber));
    }

    public WebElement findSubmitButton() {
        return webDriver.findElement(By.className("bus-to-route__submit-button"));
    }
}
