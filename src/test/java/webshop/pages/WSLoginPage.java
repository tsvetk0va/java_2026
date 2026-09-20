package webshop.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSLoginPage {

    private final SelenideElement pageTitle = $("div.page-title h1");
    private final SelenideElement emailInput = $("input#Email");
    private final SelenideElement passwordInput = $("input#Password");
    private final SelenideElement rememberMeInput = $("input#Password");
    private final SelenideElement loginButton = $("input.login-button");

    @Step("Проверить что открылась страница авторизации")
    public WSLoginPage checkLoginPageOpened() {
        pageTitle.shouldHave(exactText("Welcome, Please Sign In!"));
        return this;
    }


    @Step("Ввести электронную почту '{email}'")
    public WSLoginPage enterEmail(String email) {
        emailInput.setValue(email);
        return this;
    }


    @Step("Ввести пароль '{password}'")
    public WSLoginPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }


    @Step("Кликнуть на чекбокс 'Remember me?'")
    public WSLoginPage checkRememberMe() {
        rememberMeInput.click();
        return this;
    }


    @Step("Кликнуть на кнопку логина")
    public WSWelcomePage submitLogin() {
        loginButton.click();
        return new WSWelcomePage();
    }
}
