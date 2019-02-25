package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class AssignBusToRouteForm {
    private final WebDriver webDriver;

    public AssignBusToRouteForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    WebElement routeDropdown() {
        return webDriver.findElement(By.className("bus-to-route__route-dropdown"));
    }

    WebElement routeDropdownOption(String routeName) {
        return webDriver.findElement(By.className("bus-to-route__route-dropdown-option-" + routeName));
    }

    WebElement busDropdown() {
        return webDriver.findElement(By.className("bus-to-route__bus-dropdown"));
    }

    WebElement busDropdownOption(String busSerialNumber) {
        return webDriver.findElement(By.className("bus-to-route__bus-dropdown-option-" + busSerialNumber));
    }

    WebElement submitButton() {
        return webDriver.findElement(By.className("bus-to-route__submit-button"));
    }
}
