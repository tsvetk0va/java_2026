package webshop.util;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AttachManager {

    @Attachment(value = "last-screenshot", type = "image/png")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "page-source", type = "text/html")
    public static byte[] getPageSource() {
        return getWebDriver()
                .getPageSource()
                .getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "browser-console-logs", type = "text/plain")
    public static String getBrowserConsoleLogs() {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            return "Браузер ещё не запущен";
        }
        try {
            LogEntries logs = getWebDriver()
                    .manage().logs().get(LogType.BROWSER);
            if (logs.getAll().isEmpty()) {
                return "Логи пустые";
            }
            return logs.getAll().stream()
                    .map(LogEntry::toString)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "Не удалось получить логи: " + e.getMessage();
        }
    }
}
