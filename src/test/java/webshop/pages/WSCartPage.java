package webshop.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WSCartPage {
    private final SelenideElement productName = $("a.product-name");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement productSubtotal = $("span.product-subtotal");

    private final String expectedName;
    private final int expectedQuantity;
    private final float expectedSubtotal;

    public WSCartPage(String expectedName, int expectedQuantity, float expectedSubtotal) {
        this.expectedName = expectedName;
        this.expectedQuantity = expectedQuantity;
        this.expectedSubtotal = expectedSubtotal;
    }

    @Step("Проверить название товара в корзине")
    public WSCartPage checkProductName(){
        productName.shouldHave(text(expectedName));
        return this;
    }

    @Step("Проверить количество товара в корзине")
    public WSCartPage checkQuantity(){
        quantityInput.shouldHave(exactValue(String.valueOf(expectedQuantity)));
        return this;
    }

    @Step("Проверить итоговую стоимость товара в корзине")
    public WSCartPage checkSubtotal(){
        productSubtotal.shouldHave(text(String.valueOf(expectedSubtotal)));
        return this;
    }
}
