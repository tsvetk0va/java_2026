package webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSProductPage {
    private final SelenideElement itemName = $("[itemprop=name]");
    private final SelenideElement itemPrice = $("[itemprop=price]");
    private final SelenideElement itemQuantity = $("input.qty-input");
    private final SelenideElement addToCartButton = $(".add-to-cart-button");
    private final SelenideElement cartQty = $(".cart-qty");
    private final SelenideElement cartLink = $("a.ico-cart");
    private final SelenideElement successNotification = $("div.bar-notification.success");
    private final ElementsCollection processorOptions = $$("dl dd").first().$$("li input");


    public String getProductName() {
        return itemName.getText();
    }

    public String getProductPrice() {
        return itemPrice.getText();
    }


    @Step("Выбрать процессор: {processor}")
    public WSProductPage selectProcessor(Processor processor) {
        processorOptions.get(processor.getIndex()).click();
        return this;
    }

    @Step("Ввести количество товара: {quantity}")
    public WSProductPage enterQuantity(String quantity) {
        itemQuantity.setValue(quantity);
        return this;
    }

    @Step("Кликнуть на кнопку добавления товара в корзину")
    public WSProductPage addToCart() {
        addToCartButton.click();
        return this;
    }

    @Step("Проверить что появилось уведомление об успешном добавлении товара в корзину")
    public WSProductPage checkItemAddedNotification() {
        successNotification.should(visible);
        return this;
    }

    @Step("Проверить что в хедере количество товара в корзине равно: {quantity} ")
    public WSProductPage checkCartQuantity(String quantity) {
        cartQty.shouldHave(text("(" + quantity + ")"));
        return this;
    }

    @Step("Открыть корзину")
    public WSCartPage goToCart() {
        cartLink.click();
        return new WSCartPage();
    }
}
