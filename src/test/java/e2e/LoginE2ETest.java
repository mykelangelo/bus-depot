package e2e;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginE2ETest implements ScreenShotGeneratingE2ETest {
    private WebDriver driver;
    private LoginPage loginPage;
    private LandingPage landingPage;

    @BeforeAll
    static void doClearScreenShotsDirectory() {
        ScreenShotGeneratingE2ETest.clearScreenShotsDirectory();
    }

    @BeforeEach
    void setupBrowser() {
        ChromeDriverManager.getInstance(ChromeDriver.class).setup();
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        loginPage.goToPage();
    }

    @AfterEach
    void takeScreenShotAndShutDownBrowser(TestInfo testInfo) {
        makeScreenShot(testInfo);
        driver.quit();
    }

    @Override
    public TakesScreenshot getDriver() {
        return (TakesScreenshot) driver;
    }

    @Test
    @DisplayName("Administrator flow: wrong email entered")
    void shouldStayOnPage_andDisplayErrorMessage_whenEmailIsNotRecognised() {
        loginPage.findEmailField().sendKeys("hacker@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(loginPage.getPageUrl(), driver.getCurrentUrl(), "Didn't stay on login page");
        assertEquals("Invalid email or password", loginPage.findLoginErrorMessage().getText());
    }

    @Test
    @DisplayName("Administrator flow: wrong password entered")
    void shouldStayOnPage_andDisplayErrorMessage_whenPasswordIsWrong() {
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("someWrongCredentials");
        loginPage.findSubmitButton().click();

        assertEquals(loginPage.getPageUrl(), driver.getCurrentUrl(), "Didn't stay on login page");
        assertEquals("Invalid email or password", loginPage.findLoginErrorMessage().getText());
    }

    @Test
    @DisplayName("Administrator flow: successful log-in")
    void shouldLetAdministratorLogIn_andDisplayGreetingMessage_whenCredentialsAreCorrect() {
        loginPage.findEmailField().sendKeys("administrator@company.com");
        loginPage.findPasswordField().sendKeys("correctPasswordWhyNotItsAGreatOne");
        loginPage.findSubmitButton().click();

        assertEquals(landingPage.getPageUrl(), driver.getCurrentUrl(), "Didn't redirected to landing page");
        assertEquals("Hi, administrator@company.com", landingPage.findGreetingMessage().getText(),
                "Greeting is missing email");
    }
}
