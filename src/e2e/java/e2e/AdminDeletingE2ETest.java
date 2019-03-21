package e2e;

import e2e.page.LoginPage;
import e2e.page.admin.AdminPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminDeletingE2ETest implements ScreenShotGeneratingE2ETest {
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
    @DisplayName("Admin flow: delete unused route")
    void shouldDeleteRoute_andGetCorrespondingMessage_andNotSeeThisRouteInListsOfRoutes_whenRouteIsNotUsedByAnyBus() {
        assertEquals("K9", adminPage.getRoutesView().findRouteName("K9").getText());

        adminPage.getDeleteRouteForm().findRouteDropdown().click();
        adminPage.getDeleteRouteForm().findRouteDropdownOption("K9").click();
        adminPage.getDeleteRouteForm().findDeleteButton().click();

        adminPage.getDeleteRouteForm().findRouteDropdown().click();
        assertThrows(NoSuchElementException.class, () -> adminPage.getDeleteRouteForm().findRouteDropdownOption("K9"));
        assertThrows(NoSuchElementException.class, () -> adminPage.getRoutesView().findRouteName("K9"));
        assertEquals("You deleted route with name K9", adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: fail to delete used route")
    void shouldNotDeleteRoute_andGetCorrespondingMessage_andStillSeeThisRouteInListOfRoutes_whenRouteIsUsedByAnyBuses() {
        assertEquals("72", adminPage.getRoutesView().findRouteName("72").getText());

        adminPage.getDeleteRouteForm().findRouteDropdown().click();
        adminPage.getDeleteRouteForm().findRouteDropdownOption("72").click();
        adminPage.getDeleteRouteForm().findDeleteButton().click();

        assertEquals("72", adminPage.getRoutesView().findRouteName("72").getText());
        assertEquals("You tried to delete route with name 72 but it's used - please assign bus(es) AI7007AA to other route(s) before deleting this route",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: delete unused bus")
    void shouldDeleteBus_andGetCorrespondingMessage_andNotSeeThisBusInListsOfBuses_whenBusIsNotUsedByAnyDriver() {
        assertEquals("DE1373LT", adminPage.getBusesView().findBusSerial("DE1373LT").getText());

        adminPage.getDeleteBusForm().findBusDropdown().click();
        adminPage.getDeleteBusForm().findBusDropdownOption("DE1373LT").click();
        adminPage.getDeleteBusForm().findDeleteButton().click();

        adminPage.getDeleteBusForm().findBusDropdown().click();
        assertThrows(NoSuchElementException.class, () -> adminPage.getDeleteBusForm().findBusDropdownOption("DE1373LT"));
        assertThrows(NoSuchElementException.class, () -> adminPage.getBusesView().findBusSerial("DE1373LT"));
        assertEquals("You deleted bus with serial number DE1373LT", adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: fail to delete used bus")
    void shouldNotDeleteBus_andGetCorrespondingMessage_andStillSeeThisBusInListOfBuses_whenBusIsUsedBySomeDriver() {
        assertEquals("UC4444NT", adminPage.getBusesView().findBusSerial("UC4444NT").getText());

        adminPage.getDeleteBusForm().findBusDropdown().click();
        adminPage.getDeleteBusForm().findBusDropdownOption("UC4444NT").click();
        adminPage.getDeleteBusForm().findDeleteButton().click();

        assertEquals("UC4444NT", adminPage.getBusesView().findBusSerial("UC4444NT").getText());
        assertEquals("You tried to delete bus with serial number UC4444NT but it's used - please assign driver zoidberg@company.com to other bus before deleting this bus",
                adminPage.findLastSubmitStatusMessage().getText());
    }


    @Test
    @DisplayName("Admin flow: delete driver")
    void shouldDeleteDriver_andGetCorrespondingMessage_andNotSeeThisDriverInListsOfDrivers_andMakeThisDriverUnableToLogin() {
        assertEquals("borax.kid@company.com", adminPage.getDriversView().findUserEmail("borax.kid@company.com").getText());

        adminPage.getDeleteDriverForm().findDriverDropdown().click();
        adminPage.getDeleteDriverForm().findDriverDropdownOption("borax.kid@company.com").click();
        adminPage.getDeleteDriverForm().findDeleteButton().click();

        adminPage.getDeleteDriverForm().findDriverDropdown().click();
        assertThrows(NoSuchElementException.class, () -> adminPage.getDeleteDriverForm().findDriverDropdownOption("borax.kid@company.com"));
        assertThrows(NoSuchElementException.class, () -> adminPage.getDriversView().findUserEmail("borax.kid@company.com"));
        assertEquals("You deleted driver with email borax.kid@company.com", adminPage.findLastSubmitStatusMessage().getText());


        var loginPage = new LoginPage(webDriver);
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("borax.kid@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findLogInButton().click();

        assertEquals("Invalid email or password", loginPage.findLoginErrorMessage().getText());
    }
}
