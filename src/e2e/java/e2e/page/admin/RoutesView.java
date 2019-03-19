package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RoutesView {
    private WebDriver webDriver;

    RoutesView(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findRouteName(String routeName) {
        return webDriver.findElement(By.className("routes-view__route-name-for-" + routeName));
    }
}
