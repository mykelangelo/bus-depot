package e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VacateDriverForm {

    private final WebDriver webDriver;

    VacateDriverForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement driverDropdown() {
        return webDriver.findElement(By.className("vacate-driver__driver-dropdown"));
    }

    public WebElement driverDropdownOption(String email) {
        return webDriver.findElement(By.className("vacate-driver__driver-dropdown-option-" + email.replace('@', '_')));
    }

    public WebElement submitButton() {
        return webDriver.findElement(By.className("vacate-driver__submit-button"));
    }
}
