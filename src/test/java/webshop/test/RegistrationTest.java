package webshop.test;

import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import webshop.TestBase;
import webshop.pages.WSWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static webshop.config.Config.WEBSHOP_URL;

public class RegistrationTest extends TestBase {
    private static final Faker faker = new Faker();

    @Test
    @DisplayName("Успешная регистрация")
    @Tag("positive")
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

    @ParameterizedTest
    @DisplayName("Регистрация с невалидным email")
    @Tag("negative")
    @CsvFileSource(resources = "/email.csv")
    void registrationWithInvalidEmailTest(String email) {
        String password = faker.credentials().password();

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
                .checkEmailValidationError();
    }

    @ParameterizedTest
    @DisplayName("Регистрация с пустым email")
    @NullAndEmptySource
    @ValueSource(strings={" ", "  "})
    @Tag("negative")
    void registrationWithEmptyEmailTest(String email) {
        String password = faker.credentials().password();

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
                .checkEmailRequiredError();
    }

}
