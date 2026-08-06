package webshop.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class WSDesktopsPage {

    private final ElementsCollection products = $$("div.product-grid div");

    public WSProductPage selectProduct () {
        products.get(0).click();
        return new WSProductPage();
    }
}
