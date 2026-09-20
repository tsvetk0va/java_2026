package webshop.util;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import webshop.config.WebDriverConfig;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static webshop.config.Config.getWebDriverConfig;

public class AttachManager {
    private static final WebDriverConfig config = getWebDriverConfig();

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

    @Attachment(value = "Video", type = "video/mp4", fileExtension = ".mp4")
    public static byte[] addVideo(String sessionId) {
        String videoUrl = "https://selenoid.ya-telemost.site:8443/video/" + sessionId + ".mp4";

        String auth = config.selenoidUser() + ":" + config.selenoidPassword();
        String basic = "Basic " + Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(videoUrl))
                .header("Authorization", basic)
                .GET()
                .build();

        // видео появляется не сразу — несколько попыток с паузой
        int attempts = 10;
        for (int i = 1; i <= attempts; i++) {
            try {
                HttpResponse<byte[]> response =
                        client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                if (response.statusCode() == 200 && response.body().length > 0) {
                    return response.body();
                }
            } catch (Exception e) {
                // проглатываем и пробуем ещё раз
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Не удалось скачать видео: " + videoUrl);
        return new byte[0];
    }

//    private static URL getVideoUrl() {
//        String videoUrl = "https://selenoid.ya-telemost.site:8443/video/" + sessionId() + ".mp4";
//
//        try {
//            return new URL(videoUrl);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

}
