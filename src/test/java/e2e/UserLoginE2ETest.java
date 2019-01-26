package e2e;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserLoginE2ETest implements ScreenShotGeneratingE2ETest {
    private WebDriver webDriver;
    private LoginPage loginPage;
    private DepotAdminPage adminPage;
    private BusDriverPage busDriverPage;

    @BeforeAll
    static void doClearScreenShotsDirectory() {
        ScreenShotGeneratingE2ETest.clearScreenShotsDirectory();
    }

    @BeforeEach
    void setupBrowser() {
        ChromeDriverManager.getInstance(ChromeDriver.class).setup();
        webDriver = new ChromeDriver();
        loginPage = new LoginPage(webDriver);
        adminPage = new DepotAdminPage(webDriver);
        busDriverPage = new BusDriverPage(webDriver);
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
    @DisplayName("User flow: wrong email entered")
    void shouldStayOnPage_andDisplayErrorMessage_whenEmailIsNotRecognised() {
        loginPage.findEmailField().sendKeys("hacker@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(loginPage.getPageUrl(), webDriver.getCurrentUrl(), "Didn't stay on login page");
        assertEquals("Invalid email or password", loginPage.findLoginErrorMessage().getText());
    }

    @Test
    @DisplayName("User flow: wrong password entered")
    void shouldStayOnPage_andDisplayErrorMessage_whenPasswordIsWrong() {
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("someWrongCredentials");
        loginPage.findSubmitButton().click();

        assertEquals(loginPage.getPageUrl(), webDriver.getCurrentUrl(), "Didn't stay on login page");
        assertEquals("Invalid email or password", loginPage.findLoginErrorMessage().getText());
    }

    @Test
    @DisplayName("Administrator flow: successful log-in")
    void shouldLetDepotAdministratorLogIn_andDisplayGreetingMessage_whenCredentialsAreCorrect() {
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(DepotAdminPage.getPageUrl(), webDriver.getCurrentUrl(), "Didn't redirected to admin's landing page");
        assertEquals("Hi, administrator with email administrator@company.com!",
                adminPage.findGreetingMessage().getText(),
                "Greeting is missing email (or is malformed)");
    }

    @Test
    @DisplayName("Bus Driver flow: successful log-in")
    void shouldLetBusDriverLogIn_andDisplayGreetingMessage_whenCredentialsAreCorrect() {
        loginPage.findEmailField().sendKeys("driver@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(BusDriverPage.getPageUrl(), webDriver.getCurrentUrl(), "Didn't redirected to driver's landing page");
        assertEquals("Hi, bus driver with email driver@company.com!",
                busDriverPage.findGreetingMessage().getText(),
                "Greeting is missing email (or is malformed)");
    }
}
