package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DeleteBusForm {
    private WebDriver webDriver;

    DeleteBusForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findBusDropDown() {
        return webDriver.findElement(By.className("delete-bus__bus-dropdown"));
    }

    public WebElement findBusDropDownOption(String busSerial) {
        return webDriver.findElement(By.className("delete-bus__bus-dropdown-option-" + busSerial));
    }

    public WebElement findDeleteButton() {
        return webDriver.findElement(By.className("delete-bus__submit-button"));
    }
}
