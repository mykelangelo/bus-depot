package e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DriversView {
    private final WebDriver webDriver;

    DriversView(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement userEmail(String driverEmail) {
        return webDriver.findElement(By.className("drivers-view__user-email-for-" + driverEmail.replace('@', '_')));
    }

    public WebElement busSerial(String driverEmail) {
        return webDriver.findElement(By.className("drivers-view__bus-serial-for-" + driverEmail.replace('@', '_')));
    }
}
