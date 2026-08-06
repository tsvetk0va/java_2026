package webshop.test;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import webshop.pages.WSWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static webshop.config.Config.WEBSHOP_URL;

public class RegistrationTest {
    private static final Faker faker = new Faker();

    @Test
    void registrationTest() {
        String password = faker.credentials().password();
        String email = faker.internet().emailAddress();

        open(WEBSHOP_URL, WSWelcomePage.class)
                .openRegistration()
                .verifyRegistrationOpened()
                .selectFemaleGender()
                .enterFirstName(faker.name().firstName())
                .enterLasttName(faker.name().lastName())
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .submitRegistration()
                .checkRegistrationCompleted()
                .checkUserLoggedIn(email);
    }
}
