package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.AddBookModel;
import models.DeleteBookModel;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static specs.ReqresSpec.requestSpec;
import static specs.ReqresSpec.responseSpec;

public class ActionsWithBooks {
    @Step("Add book")
    public void addBook(Response authResponse, String isbn){

        AddBookModel addBookModel = new AddBookModel();
        AddBookModel.CollectionInfo CollectionInfo = new AddBookModel.CollectionInfo();
        CollectionInfo.setIsbn(isbn);
        addBookModel.getCollectionOfIsbns().add(CollectionInfo);
        addBookModel.setUserId(authResponse.path("userId"));

        given(requestSpec)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .body(addBookModel)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(201);
    }

    @Step("Delete book")
    public void deleteBook(Response authResponse, String isbn){

        DeleteBookModel deleteBookModel = new DeleteBookModel();
        deleteBookModel.setIsbn(isbn);
        deleteBookModel.setUserId(authResponse.path("userId"));

        given(requestSpec)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .body(deleteBookModel)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(responseSpec)
                .statusCode(204);
    }

}
