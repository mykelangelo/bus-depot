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

    default void makeScreenShot(TestInfo testInfo) {
        try {
            String screenShotFileName = this.getClass().getSimpleName() + "_" + testInfo.getTestMethod().map(Method::getName).get() + ".png";
            Path screenShotPath = SCREENSHOTS_DIRECTORY.resolve(screenShotFileName);
            LOGGER.info("Generating screen shot {}", screenShotPath.toAbsolutePath());
            Files.write(screenShotPath, getDriver().getScreenshotAs(BYTES));
        } catch (IOException e) {
            LOGGER.error("Couldn't make screen shot", e);
        }
    }

    static void clearScreenShotsDirectory() {
        try {
            LOGGER.info("Deleting old screenshots' directory");
            FileUtils.deleteDirectory(SCREENSHOTS_DIRECTORY.toFile());
            LOGGER.info("Creating screenshots' directory anew");
            Files.createDirectories(SCREENSHOTS_DIRECTORY);
        } catch (IOException e) {
            LOGGER.error("Couldn't clear screenshot directory", e);
        }
    }

    TakesScreenshot getDriver();
}
