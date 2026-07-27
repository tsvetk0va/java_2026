package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class EvotorAuthPage {
    private final SelenideElement loginWithPhoneButton = $(".evo-link-button");
    private final SelenideElement phoneInputField = $(".evo-input__field[type='tel']");
    private final SelenideElement passwordInputField = $(".evo-input__field[type='password']");
    private final SelenideElement submitButton = $("button[type='submit']");

    public EvotorAuthPage phoneInputFieldShouldBeVisible() {
        phoneInputField.shouldBe(visible);
        return this;
    }

    public EvotorAuthPage passwordInputFieldShouldBeVisible() {
        passwordInputField.shouldBe(visible);
        return this;
    }

    public EvotorAuthPage submitButtonShouldBeClickable() {
        submitButton.shouldBe(visible, enabled);
        return this;
    }

    public EvotorAuthPage selectPhonePasswordLoginMethod() {
        loginWithPhoneButton.click();
        return this;
    }

    public EvotorAuthPage enterPhone(String phoneNumber) {
        phoneInputField.setValue(phoneNumber);
        return this;
    }

    public EvotorAuthPage enterPassword(String password) {
        passwordInputField.setValue(password).pressTab();
        return this;
    }

    public EvotorDashboardPage clickSubmitButton() {
        submitButton.click();
        return new EvotorDashboardPage();
    }
}
