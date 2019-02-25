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
        return webDriver.findElement(By.className("driver__greetingMessage"));
    }
}
