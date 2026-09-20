package webshop.test;

import io.qameta.allure.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import webshop.util.TestBase;
import webshop.pages.WSWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static webshop.config.Config.WEBSHOP_URL;

public class RegistrationTest extends TestBase {
    private static final Faker faker = new Faker();

    @Test
    @Owner("k.tsvetkova")
    @DisplayName("Успешная регистрация нового пользователя")
    @Tags({@Tag("UI"), @Tag("positive")})
    @Severity(CRITICAL)
    @Epic("Регистрация")
    @Feature("Регистрация нового пользователя")
    @Story("Позитивные")
    @Link("Task-120")
    @Issue("BUG-19")
    @Description("Создаем нового пользователя со случайными данными через интерфейс")
    void registrationTest() {
        String password = faker.credentials().password();
        String email = faker.internet().emailAddress();

        open(WEBSHOP_URL, WSWelcomePage.class)
                .openRegistration()
                .verifyRegistrationOpened()
                .selectFemaleGender()
                .enterFirstName(faker.name().firstName())
                .enterLastName(faker.name().lastName())
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .submitRegistration()
                .checkRegistrationCompleted()
                .checkUserLoggedIn(email);
    }

    @ParameterizedTest(name = "Регистрация с невалидным email: {0}")
    @Owner("k.tsvetkova")
    @Tags({@Tag("UI"), @Tag("negative")})
    @Epic("Регистрация")
    @Feature("Регистрация нового пользователя")
    @Story("Негативные")
    @CsvFileSource(resources = "/email.csv")
    void registrationWithInvalidEmailTest(String email) {
        String password = faker.credentials().password();

        open(WEBSHOP_URL, WSWelcomePage.class)
                .openRegistration()
                .verifyRegistrationOpened()
                .selectFemaleGender()
                .enterFirstName(faker.name().firstName())
                .enterLastName(faker.name().lastName())
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .submitRegistration()
                .checkEmailValidationError();
    }

    @ParameterizedTest
    @Owner("k.tsvetkova")
    @DisplayName("Регистрация с пустым email")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @Tags({@Tag("UI"), @Tag("negative")})
    @Epic("Регистрация")
    @Feature("Регистрация нового пользователя")
    @Story("Негативные")
    void registrationWithEmptyEmailTest(String email) {
        String password = faker.credentials().password();

        open(WEBSHOP_URL, WSWelcomePage.class)
                .openRegistration()
                .verifyRegistrationOpened()
                .selectFemaleGender()
                .enterFirstName(faker.name().firstName())
                .enterLastName(faker.name().lastName())
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .submitRegistration()
                .checkEmailRequiredError();
    }

}
