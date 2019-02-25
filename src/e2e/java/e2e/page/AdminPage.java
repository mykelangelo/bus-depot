package e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminPage {
    private static final String ADMIN_PAGE_URL = "http://localhost:8080/admin";
    private final WebDriver webDriver;
    private final AssignDriverToBusForm assignDriverToBusForm;
    private final VacateDriverForm vacateDriverForm;
    private final AssignBusToRouteForm assignBusToRouteForm;
    private final BusesView busesView;
    private final DriversView driversView;

    public AdminPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        assignDriverToBusForm = new AssignDriverToBusForm(webDriver);
        assignBusToRouteForm = new AssignBusToRouteForm(webDriver);
        vacateDriverForm = new VacateDriverForm(webDriver);
        busesView = new BusesView(webDriver);
        driversView = new DriversView(webDriver);
    }

    public static String getPageUrl() {
        return ADMIN_PAGE_URL;
    }

    public void goToPage() {
        webDriver.get(ADMIN_PAGE_URL);
    }

    public WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("admin__greetingMessage"));
    }

    public WebElement findLastSubmitStatusMessage() {
        return webDriver.findElement(By.className("admin__last-submit-status-message"));
    }

    public AssignDriverToBusForm driverToBusForm() {
        return assignDriverToBusForm;
    }

    public VacateDriverForm vacateDriverForm() {
        return vacateDriverForm;
    }

    public AssignBusToRouteForm busToRouteForm() {
        return assignBusToRouteForm;
    }

    public BusesView busesView() {
        return busesView;
    }

    public DriversView driversView() {
        return driversView;
    }
}

