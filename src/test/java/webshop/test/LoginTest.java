package webshop.test;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webshop.pages.WSRegistrationPage;
import webshop.pages.WSWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static webshop.config.Config.WEBSHOP_REGISTRATION_URL;
import static webshop.config.Config.WEBSHOP_URL;

public class LoginTest {
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
    void succesLoginTest() {
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


