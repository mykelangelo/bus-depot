package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AssignDriverToBusForm {

    private final WebDriver webDriver;

    AssignDriverToBusForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement busDropdown() {
        return webDriver.findElement(By.className("driver-to-bus__bus-dropdown"));
    }

    public WebElement busDropdownOption(String busSerialNumber) {
        return webDriver.findElement(By.className("driver-to-bus__bus-dropdown-option-" + busSerialNumber));
    }

    public WebElement driverDropdown() {
        return webDriver.findElement(By.className("driver-to-bus__driver-dropdown"));
    }

    public WebElement driverDropdownOption(String email) {
        return webDriver.findElement(By.className("driver-to-bus__driver-dropdown-option-" + email.replace('@', '_')));
    }

    public WebElement submitButton() {
        return webDriver.findElement(By.className("driver-to-bus__submit-button"));
    }
}
