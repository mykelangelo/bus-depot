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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RootUriRedirectionE2ETest implements ScreenShotGeneratingE2ETest {
    private static final String ROOT_URL = "http://localhost:8080/";
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
    @DisplayName("Support root URI: redirect unauthorized user to login page")
    void shouldRedirectUnauthorizedUserToLoginPage() {
        webDriver.get(ROOT_URL);

        assertEquals(LoginPage.getPageUrl(), webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Support root URI: redirect admin to admin page")
    void shouldRedirectAdminToAdminPage() {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        webDriver.get(ROOT_URL);

        assertEquals(AdminPage.getPageUrl(), webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("Support root URI: redirect driver to driver page")
    void shouldRedirectDriverToDriverPage() {
        loginPage.goToPage();
        loginPage.findEmailField().sendKeys("driver@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        webDriver.get(ROOT_URL);

        assertEquals(DriverPage.getPageUrl(), webDriver.getCurrentUrl());
    }
}
