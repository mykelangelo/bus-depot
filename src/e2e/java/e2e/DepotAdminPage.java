package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class DepotAdminPage {
    private final WebDriver webDriver;

    DepotAdminPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    static String getPageUrl() {
        return "http://localhost:8080/admin.jsp";
    }

    WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("admin__greetingMessage"));
    }
}
