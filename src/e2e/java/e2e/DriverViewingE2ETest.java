package e2e;

import e2e.page.DriverPage;
import e2e.page.LoginPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverViewingE2ETest implements ScreenShotGeneratingE2ETest {
    private WebDriver webDriver;
    private DriverPage driverPage;
    private LoginPage loginPage;

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
        driverPage = new DriverPage(webDriver);
        loginPage = new LoginPage(webDriver);
        loginPage.goToPage();
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
    @DisplayName("Driver flow: view vacation message")
    void shouldViewVacationMessage() {
        loginPage.findEmailField().sendKeys("kalibob@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findLogInButton().click();

        assertEquals("You're free of any work currently. Have fun on your vacation!", driverPage.findVacatedMessage().getText());
    }

    @Test
    @DisplayName("Driver flow: view their current bus")
    void shouldViewBusSerial_andItsRoute_whenDriverIsAwareOfTheirAssignment() {
        loginPage.findEmailField().sendKeys("hell.o@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findLogInButton().click();

        assertEquals("AA2552IA", driverPage.findBusSerial().getText());
        assertEquals("7k", driverPage.findRouteName().getText());
    }

    @Test
    @DisplayName("Driver flow: accept new assignment and view their current bus and route")
    void shouldAcceptNewAssignment_thenViewBusSerial_andItsRoute_whenDriverIsUnawareOfTheirAssignment() {
        loginPage.findEmailField().sendKeys("an.un.en.n@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findLogInButton().click();

        assertEquals("You have been assigned to a new bus and/or route.",
                driverPage.findUnawareMessage().getText());
        driverPage.findConfirmButton().click();
        assertEquals("DO2019NT", driverPage.findBusSerial().getText());
        assertEquals("UN7", driverPage.findRouteName().getText());
    }

    @Test
    @DisplayName("Driver flow: accept new assignment and view their current bus")
    void shouldAcceptNewAssignment_thenViewVacationMessage_whenDriverIsUnawareOfTheirAssignment() {
        loginPage.findEmailField().sendKeys("newbie@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findLogInButton().click();

        assertEquals("You have been assigned to a new bus and/or route.",
                driverPage.findUnawareMessage().getText());
        driverPage.findConfirmButton().click();
        assertEquals("You're free of any work currently. Have fun on your vacation!", driverPage.findVacatedMessage().getText());
    }
}
