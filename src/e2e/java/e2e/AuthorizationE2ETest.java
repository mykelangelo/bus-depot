package e2e;

import e2e.page.LoginPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationE2ETest implements ScreenShotGeneratingE2ETest {
    private static final String ROOT_URL = "http://localhost:8080";
    private WebDriver webDriver;
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
        loginPage = new LoginPage(webDriver);
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

    @ParameterizedTest
    @ValueSource(strings = {"/driver", "/admin", "/driver-to-bus", "/vacate-driver", "/bus-to-route"})
    @DisplayName("Security check: unauthorized user can not perform actions that need authorization")
    void shouldNotLetUnauthorizedUserVisitDriverOrAdminPageAsWellAsSubmitAnyOfTheirForms(String driverOrAdminURI) {
        var authorizationNeededURL = ROOT_URL + driverOrAdminURI;
        webDriver.get(authorizationNeededURL);

        assertEquals("HTTP Status 401 – Unauthorized", webDriver.findElement(By.tagName("h1")).getText());
    }

    @Test
    @DisplayName("Security check: unauthorized user can try to perform an unknown action")
    void shouldLetUnauthorizedUserTryToVisitUnknownPage() {
        var noSuchURL = ROOT_URL + "/";
        webDriver.get(noSuchURL);

        assertEquals("HTTP Status 404 – Not Found", webDriver.findElement(By.tagName("h1")).getText());
    }

    @Test
    @DisplayName("Security check: unauthorized user can perform actions that do not need authorization")
    void shouldLetUnauthorizedUserVisitLoginPageAsWellAsSubmitLoginForm() {
        var authorizationNotNeededURL = ROOT_URL + "/login";
        webDriver.get(authorizationNotNeededURL);

        assertEquals("Welcome to The Bus Depot!", webDriver.findElement(By.tagName("h1")).getText());
    }

    @Test
    @DisplayName("Security check: admin can not perform actions that need driver's authorization")
    void shouldNotLetAuthorizedAdminVisitDriverPageAsWellAsSubmitDriverForm() {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        var forbiddenForAdminURL = ROOT_URL + "/driver";
        webDriver.get(forbiddenForAdminURL);

        assertEquals("HTTP Status 403 – Forbidden", webDriver.findElement(By.tagName("h1")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/admin", "/driver-to-bus", "/vacate-driver", "/bus-to-route"})
    @DisplayName("Security check: driver can not perform actions that need admin's authorization")
    void shouldNotLetAuthorizedDriverVisitAdminPageOrSubmitAnyOfAdminForms(String adminURI) {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("driver@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        var forbiddenForDriverURL = ROOT_URL + adminURI;
        webDriver.get(forbiddenForDriverURL);

        assertEquals("HTTP Status 403 – Forbidden", webDriver.findElement(By.tagName("h1")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"driver@company.com", "administrator@company.com"})
    @DisplayName("Security check: driver or admin  can try to perform an unknown action")
    void shouldLetUAuthorizedDriverOrAdminTryToVisitUnknownPage(String driverOrAdminEmail) {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys(driverOrAdminEmail);
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        var noSuchURL = ROOT_URL + "/";
        webDriver.get(noSuchURL);

        assertEquals("HTTP Status 404 – Not Found", webDriver.findElement(By.tagName("h1")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"driver@company.com", "administrator@company.com"})
    @DisplayName("Security check: driver or admin can perform actions that do not need authorization")
    void shouldLetAuthorizedDriverOrAdminVisitLoginPage(String driverOrAdminEmail) {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys(driverOrAdminEmail);
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        var noAuthorizationNeededURL = ROOT_URL + "/login";
        webDriver.get(noAuthorizationNeededURL);

        assertEquals(LoginPage.getPageUrl(), webDriver.getCurrentUrl());
    }
}
