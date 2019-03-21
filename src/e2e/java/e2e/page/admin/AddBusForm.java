package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddBusForm {
    private WebDriver webDriver;

    AddBusForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findSerialNumberField() {
        return webDriver.findElement(By.className("add-bus__serial-number"));
    }

    public WebElement findAddButton() {
        return webDriver.findElement(By.className("add-bus__submit-button"));
    }
}
