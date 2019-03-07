package e2e;

import e2e.page.admin.AdminPage;
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
    void shouldAssignDriverToBus_andGetCorrespondingMessage_andSeeDriverAwarenessSetToFalse() {
        assertEquals("✅", adminPage.driversView().assignmentAwareness("driver@company.com").getText());

        adminPage.driverToBusForm().driverDropdown().click();
        adminPage.driverToBusForm().driverDropdownOption("driver@company.com").click();
        adminPage.driverToBusForm().busDropdown().click();
        adminPage.driverToBusForm().busDropdownOption("OA0404OA").click();
        adminPage.driverToBusForm().submitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(AdminPage.getPageUrl());
        assertEquals("❌", adminPage.driversView().assignmentAwareness("driver@company.com").getText());
        assertEquals("OA0404OA", adminPage.driversView().busSerial("driver@company.com").getText());
        assertEquals("You assigned driver with email driver@company.com to bus with serial number OA0404OA",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: fail to assign second driver to a bus")
    void shouldFailToAssignSecondDriverToBus_andGetCorrespondingMessage_andSeeBothDriversAwarenessRemainTrueAndTheirBusesRemainSame() {
        assertEquals("✅", adminPage.driversView().assignmentAwareness("hell.o@company.com").getText());
        assertEquals("AA2552IA", adminPage.driversView().busSerial("hell.o@company.com").getText());
        assertEquals("✅", adminPage.driversView().assignmentAwareness("kalibob@company.com").getText());
        assertEquals("", adminPage.driversView().busSerial("kalibob@company.com").getText());

        adminPage.driverToBusForm().driverDropdown().click();
        adminPage.driverToBusForm().driverDropdownOption("kalibob@company.com").click();
        adminPage.driverToBusForm().busDropdown().click();
        adminPage.driverToBusForm().busDropdownOption("AA2552IA").click();
        adminPage.driverToBusForm().submitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(AdminPage.getPageUrl());
        assertEquals("✅", adminPage.driversView().assignmentAwareness("hell.o@company.com").getText());
        assertEquals("AA2552IA", adminPage.driversView().busSerial("hell.o@company.com").getText());
        assertEquals("✅", adminPage.driversView().assignmentAwareness("kalibob@company.com").getText());
        assertEquals("", adminPage.driversView().busSerial("kalibob@company.com").getText());
        assertEquals("You tried to assign driver with email kalibob@company.com to bus with serial number AA2552IA, but this bus is already used by driver with email hell.o@company.com",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: vacate a driver from a bus")
    void shouldVacateDriverFromBus_andGetCorrespondingMessage_andSeeDriverAwarenessSetToFalse() {
        assertEquals("✅", adminPage.driversView().assignmentAwareness("worst.driver@company.com").getText());

        adminPage.vacateDriverForm().driverDropdown().click();
        adminPage.vacateDriverForm().driverDropdownOption("worst.driver@company.com").click();
        adminPage.vacateDriverForm().submitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(AdminPage.getPageUrl());
        assertEquals("❌", adminPage.driversView().assignmentAwareness("worst.driver@company.com").getText());
        assertEquals("", adminPage.driversView().busSerial("worst.driver@company.com").getText());
        assertEquals("You vacated driver with email worst.driver@company.com",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Administrator flow: assign a bus to a route")
    void shouldAssignBusToRoute_andGetCorrespondingMessage_andSeeDriverAwarenessSetToFalse() {
        assertEquals("✅", adminPage.driversView().assignmentAwareness("some.driver@company.com").getText());

        adminPage.busToRouteForm().busDropdown().click();
        adminPage.busToRouteForm().busDropdownOption("YO7010LO").click();
        adminPage.busToRouteForm().routeDropdown().click();
        adminPage.busToRouteForm().routeDropdownOption("7L").click();
        adminPage.busToRouteForm().submitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(AdminPage.getPageUrl());
        assertEquals("❌", adminPage.driversView().assignmentAwareness("some.driver@company.com").getText());
        assertEquals("7L", adminPage.busesView().routeName("YO7010LO").getText());
        assertEquals("You assigned bus with serial number YO7010LO to route with name 7L",
                adminPage.findLastSubmitStatusMessage().getText());
    }
}
