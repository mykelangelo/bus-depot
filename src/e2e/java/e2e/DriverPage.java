package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class DriverPage {
    private static final String DRIVER_PAGE_URL = "http://localhost:8080/driver";
    private final WebDriver webDriver;

    DriverPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    static String getPageUrl() {
        return DRIVER_PAGE_URL;
    }

    WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("driver__greetingMessage"));
    }
}
