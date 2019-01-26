package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class BusDriverPage {
    private final WebDriver webDriver;

    BusDriverPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    static String getPageUrl() {
        return "http://localhost:8080/driver.jsp";
    }

    WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("driver__greetingMessage"));
    }
}
