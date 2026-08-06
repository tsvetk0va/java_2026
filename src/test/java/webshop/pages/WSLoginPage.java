package webshop.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSLoginPage {

    private final SelenideElement pageTitle = $("div.page-title h1");
    private final SelenideElement emailInput = $("input#Email");
    private final SelenideElement passwordInput = $("input#Password");
    private final SelenideElement rememberMeInput = $("input#Password");
    private final SelenideElement loginButton = $("input.login-button");

    public WSLoginPage checkLoginPageOpened() {
        pageTitle.shouldHave(exactText("Welcome, Please Sign In!"));
        return this;
    }


    public WSLoginPage enterEmail(String email) {
        emailInput.setValue(email);
        return this;
    }


    public WSLoginPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }


    public WSLoginPage checkRememberMe() {
        rememberMeInput.click();
        return this;
    }


    public WSWelcomePage submitLogin() {
        loginButton.click();
        return new WSWelcomePage();
    }




}
