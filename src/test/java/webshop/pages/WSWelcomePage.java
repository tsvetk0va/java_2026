package webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSWelcomePage {
    private final SelenideElement registerButton = $("a.ico-register");
    private final SelenideElement loginLink = $("a.ico-login");
    private final ElementsCollection headerLinks = $$("div.header-links ul li");
    private final ElementsCollection userEmailInHeader = $$("div.header-links ul li");

    public WSRegistrationPage openRegistration () {
        registerButton.click();
        return new WSRegistrationPage();
    }

    public WSLoginPage openLogin() {
        loginLink.click();
        return new WSLoginPage();
    }

    public WSWelcomePage checkUserLoggedIn(String email) {
        userEmailInHeader.get(0).shouldHave(exactText(email));
        return this;
    }
}
