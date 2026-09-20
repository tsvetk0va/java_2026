package ru.bulgakov.qa.booking;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.bulgakov.qa.booking.dto.AuthRequest;
import ru.bulgakov.qa.booking.dto.AuthResponse;
import ru.bulgakov.qa.booking.dto.CreateBookingDTO;
import ru.bulgakov.qa.booking.dto.CreateBookingResponse;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingTest {
    private static final String BOOKING_URL = "https://restful-booker.herokuapp.com";

    @BeforeAll
    static void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); /*добавили логирование запроса и
         ответа в каждый тест*/
        RestAssured.filters(new AllureRestAssured()); /*для каждого запроса, отправленного через Rest Assured (given().when().get(...) и т.д.), в Allure-отчёт автоматически попадает отдельный шаг с:
    методом и URL запроса,
    заголовками запроса,
    телом запроса (если было),
    статус-кодом ответа,
    заголовками ответа,
    телом ответа.*/

    }

    @Test
    @Tag("API")
    void authTest() {
        String user = "admin";
        String password = "password123";

        Response resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))
                .post(BOOKING_URL + "/auth")
                .then()
                .extract().response();

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNotNull();
    }

    static Stream<Arguments> invalidPayloads() {
        return Stream.of(
                Arguments.of("Неверный пароль", new AuthRequest("admin", "password"), 200),
                Arguments.of("Неверный логин",  new AuthRequest("no_such_user", "password123"), 200),
                Arguments.of("Пустой пароль",    new AuthRequest("admin", ""), 200),
                Arguments.of("Пустой логин",     new AuthRequest("", "password123"), 200),
                Arguments.of("Пустое body",      "{}", 200)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidPayloads")
    @Tag("API")
    void authWithInvalidDataTest(String name, Object body, int expectedStatus) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(BOOKING_URL + "/auth")
                .then()
                .extract().response();
        assertThat(resp.statusCode()).isEqualTo(expectedStatus);
        assertThat(resp.as(AuthResponse.class).getToken()).isNull();
    }

    @Test
    @Tag("API")
    void authWithoutBodyTest() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .post(BOOKING_URL + "/auth")
                .then()
                .extract().response();
        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNull();
    }

    @Test
    @Tag("API")
    void createBookingTest() {
        CreateBookingResponse resp = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequest())
                .post(BOOKING_URL + "/booking")
                .then()
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);
        assertThat(resp.getBookingid()).isNotNull();
        assertThat(resp.getBooking().getTotalprice()).isEqualTo(10000);
        assertThat(resp.getBooking().getBookingdates().getCheckin()).isEqualTo("2026-09-20");
        assertThat(resp.getBooking().getDepositpaid()).isFalse();
    }


    static Stream<Arguments> invalidBookings() {
        return Stream.of(
                Arguments.of("Без обязательного поля firstname", buildBookingRequest().toBuilder().firstname(null).build(), 500),
                Arguments.of("Без обязательного поля lastname", buildBookingRequest().toBuilder().lastname(null).build(), 500),
                Arguments.of("Пустое body", "{}", 500)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidBookings")
    @Tag("API")
    void createBookingWithInvalidBodyTest(String scenario, Object body, int expectedStatus) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(BOOKING_URL + "/booking")
                .then()
                .extract().response();
        assertThat(resp.statusCode()).isEqualTo(expectedStatus);
    }

    @Test
    @Tag("API")
    void createBookingWithNegativePriceTest() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequest().toBuilder().totalprice(-500).build())
                .post(BOOKING_URL + "/booking")
                .then()
                .extract().response();

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(CreateBookingResponse.class).getBooking().getTotalprice()).isEqualTo(-500);
    }

    @Test
    @Tag("API")
    void createBookingWithInvalidDateFormatTest() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequest()
                        .toBuilder()
                        .bookingdates(buildBookingRequest()
                                .getBookingdates()
                                .toBuilder()
                                .checkin("не-дата")
                                .build())
                        .build())
                .post(BOOKING_URL + "/booking")
                .then()
                .extract().response();

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(CreateBookingResponse.class)
                .getBooking()
                .getBookingdates()
                .getCheckin())
                .isEqualTo("0NaN-aN-aN");
    }

    @Test
    @Tag("API")
    void createBookingWithCheckoutBeforeCheckinTest() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequest()
                        .toBuilder()
                        .bookingdates(buildBookingRequest()
                                .getBookingdates()
                                .toBuilder()
                                .checkin("2026-09-30")
                                .checkout("2026-09-20")
                                .build())
                        .build())
                .post(BOOKING_URL + "/booking")
                .then()
                .extract().response();

        assertThat(resp.statusCode()).isEqualTo(200);
        CreateBookingResponse booking = resp.as(CreateBookingResponse.class);
        assertThat(booking.getBooking().getBookingdates().getCheckin()).isEqualTo("2026-09-30");
        assertThat(booking.getBooking().getBookingdates().getCheckout()).isEqualTo("2026-09-20");
    }

/*1 способ*/
    private static CreateBookingDTO buildBookingRequest() {
        return CreateBookingDTO.builder()
                .firstname("John")
                .lastname("Smith")
                .totalprice(10000)
                .depositpaid(false)
                .bookingdates(CreateBookingDTO.BookingDates.builder()
                        .checkin("2026-09-20")
                        .checkout("2026-09-30")
                        .build())
                .additionalneeds("bottle of water")
                .build();
    }

/*2 способ*/
    private static CreateBookingDTO buildRequest() {
        CreateBookingDTO booking = new CreateBookingDTO();
        booking.setFirstname("John");
        booking.setLastname("Smith");
        booking.setTotalprice(10000);
        booking.setDepositpaid(false);
        booking.setBookingdates(new CreateBookingDTO.BookingDates("2026-09-20", "2026-09-30"));
        booking.setAdditionalneeds("bottle of water");

        return booking;
    }

}
