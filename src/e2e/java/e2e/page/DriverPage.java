package e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DriverPage {
    private static final String DRIVER_PAGE_URL = "http://localhost:8080/driver";
    private final WebDriver webDriver;

    public DriverPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public static String getPageUrl() {
        return DRIVER_PAGE_URL;
    }

    public WebElement findLogoutButton() {
        return webDriver.findElement(By.className("driver__logout-button"));
    }

    public WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("driver__greeting-message"));
    }

    public WebElement findBusSerial() {
        return webDriver.findElement(By.className("driver__bus-serial"));
    }

    public WebElement findRouteName() {
        return webDriver.findElement(By.className("driver__route-name"));
    }

    public WebElement findUnawareMessage() {
        return webDriver.findElement(By.className("driver__unaware-message"));
    }

    public WebElement findConfirmButton() {
        return webDriver.findElement(By.className("driver__confirm-button"));
    }

    public WebElement findVacatedMessage() {
        return webDriver.findElement(By.className("driver__vacated-message"));
    }
}
