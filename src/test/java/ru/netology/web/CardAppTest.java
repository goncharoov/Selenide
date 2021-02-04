package ru.netology.web;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.*;


public class CardAppTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldReserveTest() {
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
