package webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSRegistrationPage {
    private final SelenideElement pageTitle = $("div.page-title");
    private final SelenideElement femaleGenderRadio = $("input#gender-female");
    private final SelenideElement firstNameInput = $("input#FirstName");
    private final SelenideElement lastNameInput = $("input#LastName");
    private final SelenideElement emailInput = $("input#Email");
    private final SelenideElement passwordInput = $("input#Password");
    private final SelenideElement passwordConfirmInput = $("input#ConfirmPassword");
    private final SelenideElement submitRegistrationButton = $("input#register-button");
    private final SelenideElement resultText = $("div.result");
    private final ElementsCollection userEmailInHeader = $$("div.header-links ul li");

    public WSRegistrationPage register(String firstName, String lastName, String email, String password) {
        selectFemaleGender()
                .enterFirstName(firstName)
                .enterLasttName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .submitRegistration()
                .checkRegistrationCompleted();
        return this;
    }

    public WSRegistrationPage verifyRegistrationOpened() {
        pageTitle.shouldHave(exactText("Register"));
        return this;
    }

    public WSRegistrationPage selectFemaleGender() {
        femaleGenderRadio.click();
        return this;
    }

    public WSRegistrationPage enterFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    public WSRegistrationPage enterLasttName(String lastName) {
        lastNameInput.setValue(lastName);
        return this;
    }

    public WSRegistrationPage enterEmail(String email) {
        emailInput.setValue(email);
        return this;
    }

    public WSRegistrationPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public WSRegistrationPage enterConfirmPassword(String password) {
        passwordConfirmInput.setValue(password);
        return this;
    }

    public WSRegistrationPage submitRegistration() {
        submitRegistrationButton.click();
        return this;
    }

    public WSRegistrationPage checkRegistrationCompleted() {
        resultText.shouldHave(exactText("Your registration completed"));
        return this;
    }

    public WSRegistrationPage checkUserLoggedIn(String email) {
        userEmailInHeader.get(0).shouldHave(exactText(email));
        return this;
    }









}
