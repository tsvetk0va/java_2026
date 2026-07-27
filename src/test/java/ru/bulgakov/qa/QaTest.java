package ru.bulgakov.qa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.EvotorStartPage;
import pages.YandexSearchPage;

import static com.codeborne.selenide.Selenide.open;

public class QaTest {

    private static final String YANDEX_URL = "https://ya.ru/";
    private static final String EVOTOR_URL = "https://market.evotor.ru/";

    private static final String EVOTOR_PHONE = "71000001315";
    private static final String EVOTOR_PASSWORD = "123456aA";

    private static final String CHOOSE_TARIFF_BUTTON_TEXT = "Выбрать тариф";
    private static final String BASE_SUB_PAGE_TITLE = "Тарифы";

    @Test
    @DisplayName("Проверить что цена обучения 47000 рублей")
    @Tag("POSITIVE")
    void MentoringPriceShouldBe47000Test() {
        /*
         * Тест-кейс: проверить, что предоплата по обучению - 47 000 рублей
         * 1. открыть поисковик
         * 2. ввести данные сайта (bulgakov qa)
         * 3. нажать кнопку поиск
         * 4. в поисковой выдаче найти нужный сайт, кликнуть на него
         * 5. нажать на кнопку "Стоимость"
         * 6. нажать на кнопку "Хочу вкатиться в QA"
         * 7. нажать на "Бегу оплачивать"
         * 8. проверить что к оплате 47 000 руб.
         */
        //Configuration.holdBrowserOpen = true; //чтобы браузер не закрывался после выполнения теста

        open(YANDEX_URL, YandexSearchPage.class)
                .search("bulgakov qa")
                .submit()
                .openLink("ivanbulgakovqa.ru")
                .openPricePage()
                .clickJoinQAButton()
                .clickProccedToCheckoutButton()
                .priceShouldBe("₽ 47 000.00");
    }

    @Test
    @DisplayName("Проверка что кнопка 'Выбрать тариф'открывает страницу базовой подписки")
    @Tag("POSITIVE")
    void clickTariffButtonOpensBaseSubPageTest() {

        open(EVOTOR_URL, EvotorStartPage.class)
                .personalCabinetButtonShouldBeClickable()
                .clickPersonalCabinetButton()
                .selectPhonePasswordLoginMethod()
                .phoneInputFieldShouldBeVisible()
                .enterPhone(EVOTOR_PHONE)
                .passwordInputFieldShouldBeVisible()
                .enterPassword(EVOTOR_PASSWORD)
                .submitButtonShouldBeClickable()
                .clickSubmitButton()
                .chooseTariffButtonShouldBeClickable()
                .chooseTariffButtonShouldHaveText(CHOOSE_TARIFF_BUTTON_TEXT)
                .clickChooseTariffButton()
                .urlShouldContainBaseSub()
                .pageTitleShouldHaveText(BASE_SUB_PAGE_TITLE);
    }
}
