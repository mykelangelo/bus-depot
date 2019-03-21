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

public class AdminCreatingE2ETest implements ScreenShotGeneratingE2ETest {
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
        loginPage.findSubmitButton().click();
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
    @DisplayName("Admin flow: create route")
    void shouldCreateNewRoute_andGetCorrespondingMessage_andSeeThisRouteInListOfRoutes() {
        assertThrows(NoSuchElementException.class, () -> adminPage.getRoutesView().findRouteName("G9"));

        adminPage.getAddRouteForm().findNameField().sendKeys("G9");
        adminPage.getAddRouteForm().findAddButton().click();

        assertEquals("G9", adminPage.getRoutesView().findRouteName("G9").getText());
        assertEquals("You added new route with name G9",
                adminPage.findLastSubmitStatusMessage().getText());
    }

    @Test
    @DisplayName("Admin flow: create bus")
    void shouldCreateNewBus_andGetCorrespondingMessage_andSeeThisBusInListOfBuses() {
        assertThrows(NoSuchElementException.class, () -> adminPage.getBusesView().findBusSerial("LLP89"));

        adminPage.getAddBusForm().findSerialNumberField().sendKeys("LLP89");
        adminPage.getAddBusForm().findAddButton().click();

        assertEquals("LLP89", adminPage.getBusesView().findBusSerial("LLP89").getText());
        assertEquals("You added new bus with serial number LLP89",
                adminPage.findLastSubmitStatusMessage().getText());
    }
}
