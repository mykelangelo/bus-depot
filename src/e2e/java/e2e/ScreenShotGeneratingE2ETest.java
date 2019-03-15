package e2e;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.openqa.selenium.OutputType.BYTES;

public interface ScreenShotGeneratingE2ETest {
    Logger LOGGER = LoggerFactory.getLogger(ScreenShotGeneratingE2ETest.class);
    Path SCREENSHOTS_DIRECTORY = get(
            defaultString(
                    System.getProperty("E2E_SCREENSHOTS_DIRECTORY"),
                    "test-reports/screenshots"
            )
    );

    static void clearScreenShotsDirectory(TestInfo testInfo) {
        try {
            LOGGER.trace("Deleting old screenshots' directory");
            Path testClassDir = SCREENSHOTS_DIRECTORY.resolve(testInfo.getTestClass().get().getSimpleName());
            FileUtils.deleteDirectory(testClassDir.toFile());
            LOGGER.trace("Creating screenshots' directory anew");
            Files.createDirectories(testClassDir);
        } catch (IOException e) {
            LOGGER.error("Couldn't clear screenshot directory", e);
        }
    }

    default void makeScreenShot(TestInfo testInfo) {
        try {
            String screenShotFileName = testInfo.getTestMethod().map(Method::getName).get() + ".png";
            Path screenShotPath = SCREENSHOTS_DIRECTORY.resolve(this.getClass().getSimpleName()).resolve(screenShotFileName);
            LOGGER.trace("Generating screen shot {}", screenShotPath.toAbsolutePath());
            Files.write(screenShotPath, getWebDriver().getScreenshotAs(BYTES));
        } catch (IOException e) {
            LOGGER.error("Couldn't make screen shot", e);
        }
    }

    TakesScreenshot getWebDriver();
}
