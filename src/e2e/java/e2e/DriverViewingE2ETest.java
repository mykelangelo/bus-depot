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
        makeScreenShot(testInfo);
        webDriver.quit();
    }

    @Override
    public TakesScreenshot getWebDriver() {
        return (TakesScreenshot) webDriver;
    }

    @Test
    @DisplayName("Driver flow: view their current bus")
    void shouldViewBusSerial_andItsRoute() {
        loginPage.findEmailField().sendKeys("hell.o@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals("AA2552IA", driverPage.busSerial().getText());
        assertEquals("7k", driverPage.routeName().getText());
    }
}
