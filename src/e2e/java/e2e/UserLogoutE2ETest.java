package e2e;

import e2e.page.DriverPage;
import e2e.page.LoginPage;
import e2e.page.admin.AdminPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static e2e.constant.ApplicationEndpointsURLs.AdminPage.ADMIN_PAGE_URL;
import static e2e.constant.ApplicationEndpointsURLs.DRIVER_PAGE_URL;
import static e2e.constant.ApplicationEndpointsURLs.LOGIN_PAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserLogoutE2ETest implements ScreenShotGeneratingE2ETest {
    private WebDriver webDriver;
    private DriverPage driverPage;
    private AdminPage adminPage;
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
        adminPage = new AdminPage(webDriver);
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
    @DisplayName("User logout: driver visits login page and is automatically logged out")
    void shouldLogoutDriver_andNotLetDriverBackOnDriverPage_whenDriverVisitsLoginPage() {
        loginPage.findEmailField().sendKeys("kalibob@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(DRIVER_PAGE_URL, webDriver.getCurrentUrl());

        loginPage.goToPage();

        webDriver.get(DRIVER_PAGE_URL);
        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("User logout: admin visits login page and is automatically logged out")
    void shouldLogoutAdmin_andNotLetAdminBackOnAdminPage_whenAdminVisitsLoginPage() {
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(ADMIN_PAGE_URL, webDriver.getCurrentUrl());

        loginPage.goToPage();

        webDriver.get(ADMIN_PAGE_URL);
        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl());
    }

    @Test
    @DisplayName("User logout: admin clicks logout button")
    void shouldLogoutAdmin_andRedirectAdminToLoginPage_andNotLetAdminBackOnAdminPage_whenAdminClicksLogoutButton() {
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(ADMIN_PAGE_URL, webDriver.getCurrentUrl());

        adminPage.findLogoutButton().click();
        assertThat(webDriver.getCurrentUrl()).startsWith(LOGIN_PAGE_URL);

        webDriver.get(ADMIN_PAGE_URL);
        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl());
    }

    @ParameterizedTest
    @ValueSource(strings = {"an.un.en.n@company.com", "kalibob@company.com", "hell.o@company.com"})
    @DisplayName("User logout: unaware driver and aware free & busy drivers click logout button")
    void shouldLogoutDriver_andRedirectDriverToLoginPage_andNotLetDriverBackOnDriverPage_whenDifferentDriversClickLogoutButton(String driverEmail) {
        loginPage.findEmailField().sendKeys(driverEmail);
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(DRIVER_PAGE_URL, webDriver.getCurrentUrl());

        driverPage.findLogoutButton().click();
        assertThat(webDriver.getCurrentUrl()).startsWith(LOGIN_PAGE_URL);

        webDriver.get(DRIVER_PAGE_URL);
        assertEquals(LOGIN_PAGE_URL, webDriver.getCurrentUrl());
    }
}
