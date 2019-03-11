package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AssignDriverToBusForm {

    private final WebDriver webDriver;

    AssignDriverToBusForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findBusDropdown() {
        return webDriver.findElement(By.className("driver-to-bus__bus-dropdown"));
    }

    public WebElement findBusDropdownOption(String busSerialNumber) {
        return webDriver.findElement(By.className("driver-to-bus__bus-dropdown-option-" + busSerialNumber));
    }

    public WebElement findDriverDropdown() {
        return webDriver.findElement(By.className("driver-to-bus__driver-dropdown"));
    }

    public WebElement findDriverDropdownOption(String email) {
        return webDriver.findElement(By.className("driver-to-bus__driver-dropdown-option-" + email.replace('@', '_')));
    }

    public WebElement findSubmitButton() {
        return webDriver.findElement(By.className("driver-to-bus__submit-button"));
    }
}
