package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class EvotorDashboardPage {
    private final SelenideElement chooseTariffButton = $("evo-header-base-sub-button.show-for-attached-menu .go-to-base-sub");

    public EvotorDashboardPage chooseTariffButtonShouldBeClickable() {
        chooseTariffButton.shouldBe(visible, enabled);
        return this;
    }

    public EvotorDashboardPage chooseTariffButtonShouldHaveText(String expectedText) {
        chooseTariffButton.shouldHave(text(expectedText));
        return this;
    }

    public EvotorBaseSubscriptionPage clickChooseTariffButton() {
        chooseTariffButton.click();
        return new EvotorBaseSubscriptionPage();
    }
}
