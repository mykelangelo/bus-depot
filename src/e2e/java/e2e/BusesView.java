package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class BusesView {
    private WebDriver webDriver;

    public BusesView(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement busSerial(String busSerial) {
        return webDriver.findElement(By.className("buses-view__bus-serial-for-" + busSerial));
    }

    public WebElement routeName(String busSerial) {
        return webDriver.findElement(By.className("buses-view__route-name-for-" + busSerial));
    }
}
