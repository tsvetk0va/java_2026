package webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$;

public class WSDesktopsPage {

    private final ElementsCollection productLinks = $$("h2.product-title a");

    @Step("Выбрать из каталога первый товар")
    public WSProductPage selectProduct() {
        productLinks.get(0).click();
        return new WSProductPage();
    }
}
