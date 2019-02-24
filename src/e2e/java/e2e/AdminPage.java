package e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class AdminPage {
    private static final String ADMIN_PAGE_URL = "http://localhost:8080/admin";
    private final WebDriver webDriver;
    private final AssignDriverToBusForm assignDriverToBusForm;
    private final VacateDriverForm vacateDriverForm;
    private final AssignBusToRouteForm assignBusToRouteForm;

    AdminPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        assignDriverToBusForm = new AssignDriverToBusForm();
        assignBusToRouteForm = new AssignBusToRouteForm();
        vacateDriverForm = new VacateDriverForm();
    }

    static String getPageUrl() {
        return ADMIN_PAGE_URL;
    }

    void goToPage() {
        webDriver.get(ADMIN_PAGE_URL);
    }

    WebElement findGreetingMessage() {
        return webDriver.findElement(By.className("admin__greetingMessage"));
    }

    WebElement findLastSubmitStatusMessage() {
        return webDriver.findElement(By.className("admin__last-submit-status-message"));
    }

    AssignDriverToBusForm driverToBusForm() {
        return assignDriverToBusForm;
    }

    VacateDriverForm vacateDriverForm() {
        return vacateDriverForm;
    }

    AssignBusToRouteForm busToRouteForm() {
        return assignBusToRouteForm;
    }

    class AssignDriverToBusForm {

        WebElement busDropdown() {
            return webDriver.findElement(By.className("driver-to-bus__bus-dropdown"));
        }

        WebElement busDropdownOption(String busSerialNumber) {
            return webDriver.findElement(By.className("driver-to-bus__bus-dropdown-option-" + busSerialNumber));
        }

        WebElement driverDropdown() {
            return webDriver.findElement(By.className("driver-to-bus__driver-dropdown"));
        }

        WebElement driverDropdownOption(String email) {
            return webDriver.findElement(By.className("driver-to-bus__driver-dropdown-option-" + email.replace('@', '_')));
        }

        WebElement submitButton() {
            return webDriver.findElement(By.className("driver-to-bus__submit-button"));
        }
    }

    class VacateDriverForm {

        WebElement driverDropdown() {
            return webDriver.findElement(By.className("vacate-driver__driver-dropdown"));
        }

        WebElement driverDropdownOption(String email) {
            return webDriver.findElement(By.className("vacate-driver__driver-dropdown-option-" + email.replace('@', '_')));
        }

        WebElement submitButton() {
            return webDriver.findElement(By.className("vacate-driver__submit-button"));
        }

    }

    class AssignBusToRouteForm {

        WebElement routeDropdown() {
            return webDriver.findElement(By.className("bus-to-route__route-dropdown"));
        }

        WebElement routeDropdownOption(String routeName) {
            return webDriver.findElement(By.className("bus-to-route__route-dropdown-option-" + routeName));
        }

        WebElement busDropdown() {
            return webDriver.findElement(By.className("bus-to-route__bus-dropdown"));
        }

        WebElement busDropdownOption(String busSerialNumber) {
            return webDriver.findElement(By.className("bus-to-route__bus-dropdown-option-" + busSerialNumber));
        }

        WebElement submitButton() {
            return webDriver.findElement(By.className("bus-to-route__submit-button"));
        }
    }
}

