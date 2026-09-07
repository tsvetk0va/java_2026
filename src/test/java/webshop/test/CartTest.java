package webshop.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import webshop.pages.Processor;
import webshop.pages.WSCartPage;
import webshop.pages.WSProductPage;
import webshop.util.TestBase;
import webshop.pages.WSWelcomePage;
import webshop.steps.AuthSteps;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static webshop.config.Config.WEBSHOP_URL;

public class CartTest extends TestBase {
    private final AuthSteps authSteps = new AuthSteps();


    @BeforeEach
    void beforeEach() {
        authSteps.registerNewUser();
    }

    @ParameterizedTest(name = "Добавление товара в корзину с выбором процессора: {0}")
    @Epic("Корзина")
    @Owner("k.tsvetkova")
    @Tag("positive")
    @Severity(CRITICAL)
    @Link("TEST-1515")
    @EnumSource(Processor.class)
    @Tags({@Tag("UI"), @Tag("positive")})
    void addItemToCartTest(Processor processor) {
        String property = System.getProperty("run", "local");
        String quantity = "3";


        WSProductPage productPage = open(WEBSHOP_URL, WSWelcomePage.class)
                .hoverComputersMenu()
                .openDesktops()
                .selectProduct();
        String expectedName = productPage.getProductName();
        String expectedSubtotal = String.format(Locale.US, "%.2f",
                (Float.parseFloat(productPage.getProductPrice()) +
                        processor.getSurcharge()) * Integer.parseInt(quantity));

        WSCartPage cartPage = productPage
                .selectProcessor(processor)
                .enterQuantity(quantity)
                .addToCart()
                .checkItemAddedNotification()
                .checkCartQuantity(quantity)
                .goToCart();

        assertAll(
                () -> Allure.step("Проверить название товара в корзине",
                        () -> assertEquals(expectedName, cartPage.getItemName())),
                () -> Allure.step("Проверить количество товара в корзине",
                        () -> assertEquals(quantity, cartPage.getQuantity())),
                () -> Allure.step("Проверить итоговую стоимость товара в корзине",
                        () -> assertEquals(expectedSubtotal, cartPage.getSubtotal()))
        );
    }
}
