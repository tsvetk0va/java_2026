package webshop.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import webshop.util.TestBase;
import webshop.pages.WSRegistrationPage;
import webshop.pages.WSWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static webshop.config.Config.WEBSHOP_REGISTRATION_URL;
import static webshop.config.Config.WEBSHOP_URL;

public class LoginTest extends TestBase {
    private static final Faker faker = new Faker();
    private String password;
    private String email;

    @BeforeEach
    void beforeEach() {
        password = faker.credentials().password();
        email = faker.internet().emailAddress();

        open(WEBSHOP_REGISTRATION_URL, WSRegistrationPage.class)
                .register(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        email,
                        password)
                .checkUserLoggedIn(email);

        cookies().clear();
        localStorage().clear();
    }

    @Test
    @Owner("k.tsvetkova")
    @Epic("Авторизация")
    @Link("TASK-2333")
    @DisplayName("Успешная авторизация")
    @Severity(CRITICAL)
//    @Tag("positive")
    void successLoginTest() {
        open(WEBSHOP_URL, WSWelcomePage.class)
                .openLogin()
                .checkLoginPageOpened()
                .enterEmail(email)
                .enterPassword(password)
                .checkRememberMe()
                .submitLogin()
                .checkUserLoggedIn(email);
    }
}


