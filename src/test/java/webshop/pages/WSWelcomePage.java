package webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSWelcomePage {
    private final SelenideElement registerButton = $("a.ico-register");
    private final SelenideElement loginLink = $("a.ico-login");
    private final ElementsCollection headerLinks = $$("div.header-links ul li");
    private final ElementsCollection userEmailInHeader = $$("div.header-links ul li");
    private final ElementsCollection topMenuItems = $$("ul.top-menu li a");
    private final SelenideElement desktopsLink = $(byText("Desktops"));

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


    public WSWelcomePage hoverComputersMenu() {
        topMenuItems.get(1).hover();
        return this;
    }

    public WSDesktopsPage openDesktops() {
        desktopsLink.click();
        return new WSDesktopsPage();
    }




}
