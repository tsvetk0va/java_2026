package webshop.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.WelcomePage;
import webshop.TestBase;
import webshop.pages.WSWelcomePage;
import webshop.steps.AuthSteps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static webshop.config.Config.WEBSHOP_URL;

public class CartTest extends TestBase {
    private final AuthSteps authSteps = new AuthSteps();


    @BeforeEach
    void beforeEach() {
        authSteps.registerNewUser();
    }



    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @DisplayName("Добавление товара в корзину")
    @Tag("positive")
    void addItemToCartTest(int processorIndex) {
        String quantity = "3";

        open(WEBSHOP_URL, WSWelcomePage.class)
                .hoverComputersMenu()
                .openDesktops()
                .selectProduct()
                .selectProcessor(processorIndex)
                .enterQuantity(quantity)
                .addToCart()
                .checkItemAddedNotification()
                .checkCartQuantity(quantity)
                .openCart()
                .checkProductName()
                .checkQuantity()
                .checkSubtotal();
    }
}
