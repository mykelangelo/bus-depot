package e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AssignBusToRouteForm {
    private final WebDriver webDriver;

    AssignBusToRouteForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement routeDropdown() {
        return webDriver.findElement(By.className("bus-to-route__route-dropdown"));
    }

    public WebElement routeDropdownOption(String routeName) {
        return webDriver.findElement(By.className("bus-to-route__route-dropdown-option-" + routeName));
    }

    public WebElement busDropdown() {
        return webDriver.findElement(By.className("bus-to-route__bus-dropdown"));
    }

    public WebElement busDropdownOption(String busSerialNumber) {
        return webDriver.findElement(By.className("bus-to-route__bus-dropdown-option-" + busSerialNumber));
    }

    public WebElement submitButton() {
        return webDriver.findElement(By.className("bus-to-route__submit-button"));
    }
}
