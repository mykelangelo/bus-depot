package e2e;

import e2e.page.LoginPage;
import e2e.page.admin.AdminPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminViewingE2ETest implements ScreenShotGeneratingE2ETest {
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
        var loginPage = new LoginPage(webDriver);

        loginPage.goToPage();

        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();
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
    @DisplayName("Admin flow: view buses")
    void shouldViewBusSerial_andItsRoute() {
        assertEquals("AA2552IA",
                adminPage.busesView().busSerial("AA2552IA").getText());
        assertEquals("7k",
                adminPage.busesView().routeName("AA2552IA").getText());
    }

    @Test
    @DisplayName("Admin flow: view drivers")
    void shouldViewDriverEmail_andTheirBus_andTheirAwareness_whenTheyAreAwareOfTheirAssignment() {
        assertEquals("hell.o@company.com",
                adminPage.driversView().userEmail("hell.o@company.com").getText());
        assertEquals("AA2552IA",
                adminPage.driversView().busSerial("hell.o@company.com").getText());
        assertEquals("✅",
                adminPage.driversView().assignmentAwareness("hell.o@company.com").getText());
    }

    @Test
    @DisplayName("Admin flow: view drivers")
    void shouldViewDriverEmail_andTheirBus_andTheirAwareness_whenTheyAreUnawareOfTheirAssignment() {
        assertEquals("newbie@company.com",
                adminPage.driversView().userEmail("newbie@company.com").getText());
        assertEquals("",
                adminPage.driversView().busSerial("newbie@company.com").getText());
        assertEquals("❌",
                adminPage.driversView().assignmentAwareness("newbie@company.com").getText());
    }
}
