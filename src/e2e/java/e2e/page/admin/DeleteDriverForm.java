package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DeleteDriverForm {
    private WebDriver webDriver;

    DeleteDriverForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findDriverDropdown() {
        return webDriver.findElement(By.className("delete-driver__driver-dropdown"));
    }

    public WebElement findDriverDropdownOption(String driverEmail) {
        return webDriver.findElement(By.className("delete-driver__driver-dropdown-option-" + driverEmail.replace('@', '_')));
    }

    public WebElement findDeleteButton() {
        return webDriver.findElement(By.className("delete-driver__submit-button"));
    }
}
