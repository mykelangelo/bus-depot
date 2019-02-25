package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class VacateDriverForm {

    private final WebDriver webDriver;

    public VacateDriverForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    WebElement driverDropdown() {
        return webDriver.findElement(By.className("vacate-driver__driver-dropdown"));
    }

    WebElement driverDropdownOption(String email) {
        return webDriver.findElement(By.className("vacate-driver__driver-dropdown-option-" + email.replace('@', '_')));
    }

    WebElement submitButton() {
        return webDriver.findElement(By.className("vacate-driver__submit-button"));
    }

}
