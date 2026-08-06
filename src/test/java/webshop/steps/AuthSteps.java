package webshop.steps;

import net.datafaker.Faker;
import webshop.pages.WSRegistrationPage;

import static com.codeborne.selenide.Selenide.open;
import static webshop.config.Config.WEBSHOP_REGISTRATION_URL;

public class AuthSteps {
    private static final Faker faker = new Faker();

    public void registerNewUser() {
        open(WEBSHOP_REGISTRATION_URL, WSRegistrationPage.class)
                .register(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.credentials().password());
    }
}
