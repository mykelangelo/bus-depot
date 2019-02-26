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

    public WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("driver__greeting-message"));
    }

    public WebElement busSerial() {
        return webDriver.findElement(By.className("driver__bus-serial"));
    }

    public WebElement routeName() {
        return webDriver.findElement(By.className("driver__route-name"));
    }

    public WebElement unawareMessage() {
        return webDriver.findElement(By.className("driver__unaware-message"));
    }

    public WebElement confirmButton() {
        return webDriver.findElement(By.className("driver__confirm-button"));
    }
}
