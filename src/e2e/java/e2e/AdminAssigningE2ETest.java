package e2e;

import e2e.page.AdminPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminAssigningE2ETest implements ScreenShotGeneratingE2ETest {
    private WebDriver webDriver;
    private AdminPage adminPage;

    @BeforeAll
    static void doClearScreenShotsDirectory(TestInfo testInfo) {
        ScreenShotGeneratingE2ETest.clearScreenShotsDirectory(testInfo);
    }

    @BeforeEach
    void setupBrowser() {
        ChromeDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-headless");
        options.addArguments("-disable-gpu");

        webDriver = new ChromeDriver(options);
        adminPage = new AdminPage(webDriver);
        adminPage.goToPage();
    }

    @AfterEach
    void takeScreenShotAndShutDownBrowser(TestInfo testInfo) {
        makeScreenShot(testInfo);
        webDriver.quit();
    }

    @Override
    public TakesScreenshot getWebDriver() {
        return (TakesScreenshot) webDriver;
    }

    @Test
    @DisplayName("Admin flow: assign a driver to a bus")
    void shouldAssignDriverToBus_andVacateDriverFromBus_andGetCorrespondingMessages() {
        adminPage.driverToBusForm().driverDropdown().click();
        adminPage.driverToBusForm().driverDropdownOption("driver@company.com").click();
        adminPage.driverToBusForm().busDropdown().click();
        adminPage.driverToBusForm().busDropdownOption("OA0404OA").click();
        adminPage.driverToBusForm().submitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(AdminPage.getPageUrl());
        assertEquals("OA0404OA", adminPage.driversView().busSerial("driver@company.com").getText());
        assertEquals("You assigned driver with email driver@company.com to bus with serial number OA0404OA",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: vacate a driver from a bus")
    void shouldVacateDriverFromBus_andGetCorrespondingMessage() {
        adminPage.vacateDriverForm().driverDropdown().click();
        adminPage.vacateDriverForm().driverDropdownOption("driver@company.com").click();
        adminPage.vacateDriverForm().submitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(AdminPage.getPageUrl());
        assertEquals("", adminPage.driversView().busSerial("driver@company.com").getText());
        assertEquals("You vacated driver with email driver@company.com",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Administrator flow: assign a bus to a route")
    void shouldAssignBusToRoute_andGetCorrespondingMessage() {
        adminPage.busToRouteForm().busDropdown().click();
        adminPage.busToRouteForm().busDropdownOption("AI7007AA").click();
        adminPage.busToRouteForm().routeDropdown().click();
        adminPage.busToRouteForm().routeDropdownOption("7L").click();
        adminPage.busToRouteForm().submitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(AdminPage.getPageUrl());
        assertEquals("7L", adminPage.busesView().routeName("AI7007AA").getText());
        assertEquals("You assigned bus with serial number AI7007AA to route with name 7L",
                adminPage.findLastSubmitStatusMessage().getText());
    }
}
