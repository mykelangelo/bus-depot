package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class AssignDriverToBusForm {

    private final WebDriver webDriver;

    public AssignDriverToBusForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    WebElement busDropdown() {
        return webDriver.findElement(By.className("driver-to-bus__bus-dropdown"));
    }

    WebElement busDropdownOption(String busSerialNumber) {
        return webDriver.findElement(By.className("driver-to-bus__bus-dropdown-option-" + busSerialNumber));
    }

    WebElement driverDropdown() {
        return webDriver.findElement(By.className("driver-to-bus__driver-dropdown"));
    }

    WebElement driverDropdownOption(String email) {
        return webDriver.findElement(By.className("driver-to-bus__driver-dropdown-option-" + email.replace('@', '_')));
    }

    WebElement submitButton() {
        return webDriver.findElement(By.className("driver-to-bus__submit-button"));
    }
}
