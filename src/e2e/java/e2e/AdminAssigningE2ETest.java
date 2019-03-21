package e2e;

import e2e.page.LoginPage;
import e2e.page.admin.AdminPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static e2e.constant.ApplicationEndpointsURLs.AdminPage.ADMIN_PAGE_URL;
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
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.goToPage();

        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findLogInButton().click();
    }

    @AfterEach
    void takeScreenShotAndShutDownBrowser(TestInfo testInfo) {
        this.makeScreenShot(testInfo);
        webDriver.quit();
    }

    @Override
    public TakesScreenshot getWebDriver() {
        return (TakesScreenshot) webDriver;
    }

    @Test
    @DisplayName("Admin flow: assign a driver to a bus")
    void shouldAssignDriverToBus_andGetCorrespondingMessage_andSeeDriverAwarenessSetToFalse() {
        assertEquals("✅", adminPage.getDriversView().findAssignmentAwareness("driver@company.com").getText());

        adminPage.getDriverToBusForm().findDriverDropdown().click();
        adminPage.getDriverToBusForm().findDriverDropdownOption("driver@company.com").click();
        adminPage.getDriverToBusForm().findBusDropdown().click();
        adminPage.getDriverToBusForm().findBusDropdownOption("OA0404OA").click();
        adminPage.getDriverToBusForm().findSubmitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(ADMIN_PAGE_URL);
        assertEquals("❌", adminPage.getDriversView().findAssignmentAwareness("driver@company.com").getText());
        assertEquals("OA0404OA", adminPage.getDriversView().findBusSerial("driver@company.com").getText());
        assertEquals("You assigned driver with email driver@company.com to bus with serial number OA0404OA",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: fail to assign second driver to a bus")
    void shouldFailToAssignSecondDriverToBus_andGetCorrespondingMessage_andSeeBothDriversAwarenessRemainTrueAndTheirBusesRemainSame() {
        assertEquals("✅", adminPage.getDriversView().findAssignmentAwareness("hell.o@company.com").getText());
        assertEquals("AA2552IA", adminPage.getDriversView().findBusSerial("hell.o@company.com").getText());
        assertEquals("✅", adminPage.getDriversView().findAssignmentAwareness("kalibob@company.com").getText());
        assertEquals("", adminPage.getDriversView().findBusSerial("kalibob@company.com").getText());

        adminPage.getDriverToBusForm().findDriverDropdown().click();
        adminPage.getDriverToBusForm().findDriverDropdownOption("kalibob@company.com").click();
        adminPage.getDriverToBusForm().findBusDropdown().click();
        adminPage.getDriverToBusForm().findBusDropdownOption("AA2552IA").click();
        adminPage.getDriverToBusForm().findSubmitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(ADMIN_PAGE_URL);
        assertEquals("✅", adminPage.getDriversView().findAssignmentAwareness("hell.o@company.com").getText());
        assertEquals("AA2552IA", adminPage.getDriversView().findBusSerial("hell.o@company.com").getText());
        assertEquals("✅", adminPage.getDriversView().findAssignmentAwareness("kalibob@company.com").getText());
        assertEquals("", adminPage.getDriversView().findBusSerial("kalibob@company.com").getText());
        assertEquals("You tried to assign driver with email kalibob@company.com to bus with serial number AA2552IA, but this bus is already used by driver with email hell.o@company.com",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: vacate a driver from a bus")
    void shouldVacateDriverFromBus_andGetCorrespondingMessage_andSeeDriverAwarenessSetToFalse() {
        assertEquals("✅", adminPage.getDriversView().findAssignmentAwareness("worst.driver@company.com").getText());

        adminPage.getVacateDriverForm().findDriverDropdown().click();
        adminPage.getVacateDriverForm().findDriverDropdownOption("worst.driver@company.com").click();
        adminPage.getVacateDriverForm().findSubmitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(ADMIN_PAGE_URL);
        assertEquals("❌", adminPage.getDriversView().findAssignmentAwareness("worst.driver@company.com").getText());
        assertEquals("", adminPage.getDriversView().findBusSerial("worst.driver@company.com").getText());
        assertEquals("You vacated driver with email worst.driver@company.com",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Administrator flow: assign a bus to a route")
    void shouldAssignBusToRoute_andGetCorrespondingMessage_andSeeDriverAwarenessSetToFalse() {
        assertEquals("✅", adminPage.getDriversView().findAssignmentAwareness("some.driver@company.com").getText());

        adminPage.getBusToRouteForm().findBusDropdown().click();
        adminPage.getBusToRouteForm().findBusDropdownOption("YO7010LO").click();
        adminPage.getBusToRouteForm().findRouteDropdown().click();
        adminPage.getBusToRouteForm().findRouteDropdownOption("7L").click();
        adminPage.getBusToRouteForm().findSubmitButton().click();

        assertThat(webDriver.getCurrentUrl()).startsWith(ADMIN_PAGE_URL);
        assertEquals("❌", adminPage.getDriversView().findAssignmentAwareness("some.driver@company.com").getText());
        assertEquals("7L", adminPage.getBusesView().findRouteName("YO7010LO").getText());
        assertEquals("You assigned bus with serial number YO7010LO to route with name 7L",
                adminPage.findLastSubmitStatusMessage().getText());
    }
}
