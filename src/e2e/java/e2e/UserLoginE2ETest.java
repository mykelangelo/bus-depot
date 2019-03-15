package e2e;

import e2e.page.DriverPage;
import e2e.page.LoginPage;
import e2e.page.admin.AdminPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static e2e.constant.ApplicationEndpointsURLs.AdminPage.ADMIN_PAGE_URL;
import static e2e.constant.ApplicationEndpointsURLs.DRIVER_PAGE_URL;
import static e2e.constant.ApplicationEndpointsURLs.LOGIN_PAGE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserLoginE2ETest implements ScreenShotGeneratingE2ETest {
    private WebDriver webDriver;
    private LoginPage loginPage;
    private AdminPage adminPage;
    private DriverPage driverPage;

    @BeforeAll
    static void doClearScreenShotsDirectory(TestInfo testInfo) {
        ScreenShotGeneratingE2ETest.clearScreenShotsDirectory(testInfo);
    }

    @BeforeEach
    void setupBrowser() {
        ChromeDriverManager.getInstance(ChromeDriver.class).setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");

        webDriver = new ChromeDriver(options);
        loginPage = new LoginPage(webDriver);
        adminPage = new AdminPage(webDriver);
        driverPage = new DriverPage(webDriver);
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
    @DisplayName("User flow: wrong email entered")
    void shouldStayOnPage_andDisplayErrorMessage_whenEmailIsNotRecognised() {
        loginPage.findEmailField().sendKeys("hacker@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl(), "Didn't stay on login page");
        assertEquals("Invalid email or password", loginPage.findLoginErrorMessage().getText());
    }

    @Test
    @DisplayName("User flow: wrong password entered")
    void shouldStayOnPage_andDisplayErrorMessage_whenPasswordIsWrong() {
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("someWrongCredentials");
        loginPage.findSubmitButton().click();

        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl(), "Didn't stay on login page");
        assertEquals("Invalid email or password", loginPage.findLoginErrorMessage().getText());
    }

    @Test
    @DisplayName("Administrator flow: successful login")
    void shouldLetDepotAdministratorLogIn_andDisplayGreetingMessage_whenCredentialsAreCorrect() {
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        LOGGER.debug(webDriver.getCurrentUrl());
        assertEquals(ADMIN_PAGE_URL, webDriver.getCurrentUrl(), "Didn't redirected to admin's page");
        assertEquals("Hi, administrator with email administrator@company.com!",
                adminPage.findGreetingMessage().getText(),
                "Greeting is missing email (or is malformed)");
    }

    @Test
    @DisplayName("Bus Driver flow: successful login")
    void shouldLetBusDriverLogIn_andDisplayGreetingMessage_whenCredentialsAreCorrect() {
        loginPage.findEmailField().sendKeys("best.driver@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        LOGGER.debug(webDriver.getCurrentUrl());
        assertEquals(DRIVER_PAGE_URL, webDriver.getCurrentUrl(), "Didn't redirected to driver's page");
        assertEquals("Hi, bus driver with email best.driver@company.com!",
                driverPage.findGreetingMessage().getText(),
                "Greeting is missing email (or is malformed)");
    }
}
