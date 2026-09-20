package webshop.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class WSCartPage {
    private final SelenideElement productName = $("a.product-name");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement productSubtotal = $("span.product-subtotal");

    public String getItemName() {
        return productName.getText();
    }

    public String getQuantity() {
        return quantityInput.getValue();
    }

    public String getSubtotal() {
        return productSubtotal.getText();
    }
}
