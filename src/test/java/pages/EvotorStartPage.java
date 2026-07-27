package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class EvotorStartPage {
    private final SelenideElement personalCabinetButton = $("[test-id='login-button']");

    public EvotorStartPage personalCabinetButtonShouldBeClickable() {
        personalCabinetButton.shouldBe(visible, enabled);
        return this;
    }

    public EvotorAuthPage clickPersonalCabinetButton() {
        personalCabinetButton.click();
        return new EvotorAuthPage();
    }
}
