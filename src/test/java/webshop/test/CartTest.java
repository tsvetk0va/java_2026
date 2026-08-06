package webshop.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.WelcomePage;
import webshop.pages.WSWelcomePage;
import webshop.steps.AuthSteps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static webshop.config.Config.WEBSHOP_URL;

public class CartTest {
    private final AuthSteps authSteps = new AuthSteps();


    @BeforeEach
    void beforeEach() {
        authSteps.registerNewUser();
    }


    @Test
    void addItemToCartTest() {
        String quantity = "3";
        int processorIndex = 2;

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
