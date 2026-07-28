package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MentoringCostPage {
    private final SelenideElement wantToJoinQAButton = $x("//div[@data-elem-id='1741455941258']//a[@class='tn-atom']");
    private final SelenideElement proccedToCheckoutButton = $(byText("Бегу оплачивать"));

    public MentoringCostPage clickJoinQAButton() {
        wantToJoinQAButton.click();
        return this;
    }
    public PaymentPage clickProccedToCheckoutButton() {
        proccedToCheckoutButton.click();
        switchTo().window(2);

        return new PaymentPage();
    }
}
