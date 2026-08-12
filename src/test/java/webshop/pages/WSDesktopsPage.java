package webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$;

public class WSDesktopsPage {

    private final ElementsCollection products = $$("div.product-grid div");

    @Step("Выбрать из каталога первый товар")
    public WSProductPage selectProduct() {
        products.get(0).click();
        return new WSProductPage();
    }
}
