package webshop.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

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
    // Надбавка выбранного процессора
    private float processorSurcharge = 0f;
    private String selectedItemName;
    private int selectedQuantity;

    // Надбавки за процессоры по индексам: slow = 0, medium = +15, fast = +100
    private static final float[] PROCESSOR_SURCHARGE = {0f, 15f, 100f};

    public WSProductPage selectProcessor(int index) {
        processorOptions.get(index).click();
        processorSurcharge = PROCESSOR_SURCHARGE[index];
        return this;
    }

    public WSProductPage enterQuantity(String quantity) {
        itemQuantity.setValue(quantity);
        this.selectedQuantity = Integer.parseInt(quantity);
        return this;
    }

    public WSProductPage addToCart() {
        this.selectedItemName = itemName.getText();
        addToCartButton.click();
        return this;
    }

    public WSProductPage checkItemAddedNotification() {
        successNotification.should(visible, Duration.ofSeconds(1));
        return this;
    }

    public WSProductPage checkCartQuantity(String quantity) {
        cartQty.shouldHave(text("(" + quantity + ")"));
        return this;
    }

    public float getUnitPrice() {
        return Float.parseFloat(itemPrice.getText()) + processorSurcharge;
    }

    public WSCartPage openCart() {
        float expectedSubtotal = getUnitPrice() * selectedQuantity;
        cartLink.click();
        return new WSCartPage(selectedItemName,selectedQuantity,expectedSubtotal);
    }

}
