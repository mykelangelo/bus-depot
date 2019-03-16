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

import static e2e.constant.ApplicationEndpointsURLs.AdminPage.*;
import static e2e.constant.ApplicationEndpointsURLs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationE2ETest implements ScreenShotGeneratingE2ETest {
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
        this.makeScreenShot(testInfo);
        webDriver.quit();
    }

    @Override
    public TakesScreenshot getWebDriver() {
        return (TakesScreenshot) webDriver;
    }

    @ParameterizedTest
    @ValueSource(strings = {DRIVER_PAGE_URL, ADMIN_PAGE_URL, ASSIGN_DRIVER_TO_BUS_FORM_URL, VACATE_DRIVER_FORM_URL, ASSIGN_BUS_TO_ROUTE_FORM_URL})
    @DisplayName("Security check: unauthorized user can not perform actions that need authorization")
    void shouldNotLetUnauthorizedUserVisitDriverOrAdminPageOrSubmitAnyOfTheirForms(String driverOrAdminURL) {
        webDriver.get(driverOrAdminURL);

        assertEquals("YOU'VE COME TO THE WONG PLACE", webDriver.findElement(By.className("big-font")).getText());
    }

    @Test
    @DisplayName("Security check: unauthorized user can try to perform an unknown action")
    void shouldLetUnauthorizedUserTryToVisitUnknownPage() {
        webDriver.get(NO_SUCH_PAGE_EXISTS_URL);

        assertEquals("YOU'VE COME TO THE WONG PLACE", webDriver.findElement(By.className("big-font")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {LOGIN_PAGE_URL, LOGOUT_FORM_URL})
    @DisplayName("Security check: unauthorized user can perform actions that do not need authorization")
    void shouldLetUnauthorizedUserVisitLoginPageOrSubmitLoginFormOrTryToLogout(String loginOrLogoutURL) {
        webDriver.get(loginOrLogoutURL);

        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Security check: admin can not perform actions that need driver's authorization")
    void shouldNotLetAuthorizedAdminVisitDriverPageOrSubmitDriverForm() {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        webDriver.get(DRIVER_PAGE_URL);

        assertEquals("YOU'VE COME TO THE WONG PLACE", webDriver.findElement(By.className("big-font")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {ADMIN_PAGE_URL, ASSIGN_DRIVER_TO_BUS_FORM_URL, VACATE_DRIVER_FORM_URL, ASSIGN_BUS_TO_ROUTE_FORM_URL})
    @DisplayName("Security check: driver can not perform actions that need admin's authorization")
    void shouldNotLetAuthorizedDriverVisitAdminPageOrSubmitAnyOfAdminForms(String adminURL) {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("driver@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        webDriver.get(adminURL);

        assertEquals("YOU'VE COME TO THE WONG PLACE", webDriver.findElement(By.className("big-font")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"driver@company.com", "administrator@company.com"})
    @DisplayName("Security check: driver and admin can try to perform unknown action")
    void shouldLetAuthorizedDriverAndAdminTryToVisitUnknownPage(String driverOrAdminEmail) {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys(driverOrAdminEmail);
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        webDriver.get(NO_SUCH_PAGE_EXISTS_URL);

        assertEquals("YOU'VE COME TO THE WONG PLACE", webDriver.findElement(By.className("big-font")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"driver@company.com", "administrator@company.com"})
    @DisplayName("Security check: driver or admin can perform actions that do not need authorization")
    void shouldLetAuthorizedDriverOrAdminVisitLoginPage(String driverOrAdminEmail) {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys(driverOrAdminEmail);
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        webDriver.get(LOGIN_PAGE_URL);

        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl());
        assertEquals("Welcome to The Bus Depot!", webDriver.findElement(By.tagName("h1")).getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"driver@company.com", "administrator@company.com"})
    @DisplayName("Security check: driver or admin can perform actions that do not need authorization")
    void shouldLetAuthorizedDriverOrAdminPerformLogout(String driverOrAdminEmail) {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys(driverOrAdminEmail);
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        webDriver.get(LOGOUT_FORM_URL);

        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl());
        assertEquals("Welcome to The Bus Depot!", webDriver.findElement(By.tagName("h1")).getText());
    }
}
