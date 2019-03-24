package e2e;

import e2e.page.LoginPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationE2ETest implements ScreenShotGeneratingE2ETest {
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
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");

        webDriver = new ChromeDriver(options);
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
    @DisplayName("User flow: select english")
    void shouldSeeWelcomeMessageInEnglish_whenEnglishIsSelected() {
        loginPage.findLanguagesDropdown().click();
        loginPage.findLanguagesDropdownOption("english").click();

        assertEquals("Welcome to The Bus Depot!", loginPage.findGreetingMessage().getText());
    }

    @Test
    @DisplayName("User flow: select ukrainian")
    void shouldSeeWelcomeMessageInUkrainian_whenUkrainianIsSelected() {
        loginPage.findLanguagesDropdown().click();
        loginPage.findLanguagesDropdownOption("ukrainian").click();

        assertEquals("Вітаємо у Автобусному Депо!", loginPage.findGreetingMessage().getText());
    }

    @Test
    @DisplayName("User flow: select russian")
    void shouldSeeWelcomeMessageInRussian_whenRussianIsSelected() {
        loginPage.findLanguagesDropdown().click();
        loginPage.findLanguagesDropdownOption("russian").click();

        assertEquals("Добро пожаловать в Автобусное Депо!", loginPage.findGreetingMessage().getText());
    }
}
