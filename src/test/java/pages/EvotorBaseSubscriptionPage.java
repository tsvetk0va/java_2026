package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class EvotorBaseSubscriptionPage {
    private static final String BASE_SUB_URL_PART = "/store/base-sub";

    private final SelenideElement pageTitle = $("h1"); // написал для примера

    public EvotorBaseSubscriptionPage urlShouldContainBaseSub() {
        webdriver().shouldHave(urlContaining(BASE_SUB_URL_PART));
        return this;
    }

    public EvotorBaseSubscriptionPage pageTitleShouldHaveText(String expectedTitle) {
        pageTitle.shouldHave(text(expectedTitle));
        return this;
    }
}
