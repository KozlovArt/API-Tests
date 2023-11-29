package tests;


import api.ActionsWithBooks;
import api.Authorization;
import helpers.WithLogin;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ApiUiTests extends TestBase{
    @Test
    @WithLogin
    void deleteBookFromCollectionTest() {

        ActionsWithBooks actions = new ActionsWithBooks();

        actions.addBook(authResponse,"9781449365035");
        actions.deleteBook(authResponse,"9781449365035");
        actions.verifyDeleteBook("9781449365035");

    }
}
