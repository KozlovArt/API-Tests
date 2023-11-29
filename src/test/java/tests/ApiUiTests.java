package tests;


import api.ActionsWithBooks;
import api.Authorization;
import helpers.WithLogin;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class ApiUiTests extends TestBase{
    @Test
    @WithLogin
    void deleteBookFromCollectionTest() {

        ActionsWithBooks actions = new ActionsWithBooks();

        actions.addBook(authResponse,"9781449365035");
        actions.deleteBook(authResponse,"9781449365035");

        step("Check delete book", () -> {
        open("/profile");
        $("[href*='/profile?book=9781449365035']").shouldNotBe(exist);
        });

    }
}
