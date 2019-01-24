package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class LandingPage {
    private final WebDriver driver;

    LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    String getPageUrl() {
        return "http://localhost:8080/landing.jsp";
    }

    WebElement findGreetingMessage() {
        return driver.findElement(By.className("landing__greetingMessage"));
    }
}
