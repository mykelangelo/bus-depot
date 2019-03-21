package e2e.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminPage {
    private final WebDriver webDriver;
    private final AssignDriverToBusForm assignDriverToBusForm;
    private final VacateDriverForm vacateDriverForm;
    private final AssignBusToRouteForm assignBusToRouteForm;
    private final BusesView busesView;
    private final DriversView driversView;
    private final RoutesView routesView;
    private final AddRouteForm addRouteForm;
    private final DeleteRouteForm deleteRouteForm;
    private final AddBusForm addBusForm;

    public AdminPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        assignDriverToBusForm = new AssignDriverToBusForm(webDriver);
        assignBusToRouteForm = new AssignBusToRouteForm(webDriver);
        vacateDriverForm = new VacateDriverForm(webDriver);
        busesView = new BusesView(webDriver);
        driversView = new DriversView(webDriver);
        routesView = new RoutesView(webDriver);
        addRouteForm = new AddRouteForm(webDriver);
        deleteRouteForm = new DeleteRouteForm(webDriver);
        addBusForm = new AddBusForm(webDriver);
    }


    public AssignDriverToBusForm getDriverToBusForm() {
        return assignDriverToBusForm;
    }

    public VacateDriverForm getVacateDriverForm() {
        return vacateDriverForm;
    }

    public AssignBusToRouteForm getBusToRouteForm() {
        return assignBusToRouteForm;
    }

    public BusesView getBusesView() {
        return busesView;
    }

    public DriversView getDriversView() {
        return driversView;
    }

    public RoutesView getRoutesView() {
        return routesView;
    }

    public AddRouteForm getAddRouteForm() {
        return addRouteForm;
    }

    public DeleteRouteForm getDeleteRouteForm() {
        return deleteRouteForm;
    }

    public AddBusForm getAddBusForm() {
        return addBusForm;
    }

    public WebElement findLogoutButton() {
        return webDriver.findElement(By.className("admin__logout-button"));
    }

    public WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("admin__greeting-message"));
    }

    public WebElement findLastSubmitStatusMessage() {
        return webDriver.findElement(By.className("admin__last-submit-status-message"));
    }
}
