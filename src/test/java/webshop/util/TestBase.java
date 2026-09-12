package webshop.util;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import webshop.config.WebDriverConfig;

import static com.codeborne.selenide.Selenide.*;
import static webshop.config.Config.getSelenoidChromeOptions;
import static webshop.config.Config.getWebDriverConfig;


public class TestBase {

    private static final WebDriverConfig config = getWebDriverConfig();

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.browserSize = config.browserSize();
        Configuration.browser = config.browser();

        if ("remote".equals(System.getProperty("run"))) {
            Configuration.remote = config.selenoidUrl();
            Configuration.browserCapabilities = getSelenoidChromeOptions();
        }
    }

    @AfterEach
    void after() {
        cookies().clear();
        localStorage().clear();

        AttachManager.takeScreenshot();
        AttachManager.getPageSource();
        AttachManager.getBrowserConsoleLogs();

        if ("remote".equals(config.run())) {
            String id = sessionId().toString();   // запомнили id, пока драйвер жив
            closeWebDriver();                      // закрыли сессию — Selenoid допишет видео
            AttachManager.addVideo(id);            // теперь качаем
        }
    }

//    @BeforeEach
//    void closeDriver() {
//        closeWebDriver();
//    }
}
