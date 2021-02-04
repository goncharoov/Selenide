package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.*;

public class CardAppTest {

    @Test
    void shouldReserve() {
        open("http://localhost:9999");
        $("[placeholder = 'Город']").setValue("Москва");
        $("[placeholder = 'Дата встречи']").sendKeys(Keys.DELETE);
        LocalDate date = LocalDate.now().plusDays(3);
        String inputDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder = 'Дата встречи']").setValue(inputDate);
        $("[name = 'name']").setValue("Гордон Фриман");
        $("[name = 'phone']").setValue("+79123456789");
        $("[data-test-id = 'agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id = 'notification'] .notification__content").shouldBe(visible, ofMillis(15000)).shouldHave(exactText("Встреча успешно забронирована на " + inputDate));
    }
}
