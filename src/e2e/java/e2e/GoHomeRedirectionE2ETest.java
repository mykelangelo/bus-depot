package e2e;

import e2e.page.LoginPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static e2e.constant.ApplicationEndpointsURLs.AdminPage.ADMIN_PAGE_URL;
import static e2e.constant.ApplicationEndpointsURLs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoHomeRedirectionE2ETest implements ScreenShotGeneratingE2ETest {
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


    @Test
    @DisplayName("Support root ApplicationEndpointsURIs: redirect unauthorized user to login page")
    void shouldRedirectUnauthorizedUserToLoginPage() {
        webDriver.get(GO_HOME_URL);

        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Support root ApplicationEndpointsURIs: redirect admin to admin page")
    void shouldRedirectAdminToAdminPage() {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findLogInButton().click();

        webDriver.get(GO_HOME_URL);

        assertEquals(ADMIN_PAGE_URL, webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Support root ApplicationEndpointsURIs: redirect driver to driver page")
    void shouldRedirectDriverToDriverPage() {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("driver@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findLogInButton().click();

        webDriver.get(GO_HOME_URL);

        assertEquals(DRIVER_PAGE_URL, webDriver.getCurrentUrl());
    }
}
