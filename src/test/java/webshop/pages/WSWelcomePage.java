package webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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

    @Step("Открыть страницу регистрации")
    public WSRegistrationPage openRegistration () {
        registerButton.click();
        return new WSRegistrationPage();
    }

    @Step("Открыть страницу авторизации")
    public WSLoginPage openLogin() {
        loginLink.click();
        return new WSLoginPage();
    }

    @Step("Проверить что пользователь залогинен с {email}")
    public WSWelcomePage checkUserLoggedIn(String email) {
        userEmailInHeader.get(0).shouldHave(exactText(email));
        return this;
    }


    @Step("Навести курсор на категорию 'Computers'")
    public WSWelcomePage hoverComputersMenu() {
        topMenuItems.get(1).hover();
        return this;
    }

    @Step("Выбрать категорию 'Desktops'")
    public WSDesktopsPage openDesktops() {
        desktopsLink.click();
        return new WSDesktopsPage();
    }




}
