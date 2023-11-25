package tests;

import models.*;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.ReqresSpec.*;


public class ApiTests extends TestBase {

    @Test
    void checkCreateUserTest() {
        UserInfoRequestModel requestModel = new UserInfoRequestModel();
        requestModel.setName("Alex");
        requestModel.setJob("freeloader");
        UserInfoResponseModel responseModel = step("Create user request", () -> given(requestSpec)
                .body(requestModel)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract().as(UserInfoResponseModel.class));
        step("Verify response", () -> {
            assertThat(responseModel.getName()).isEqualTo("Alex");
            assertThat(responseModel.getJob()).isEqualTo("freeloader");
        });

    }

    @Test
    void checkUpdateUserTest() {
        UserInfoRequestModel requestModel = new UserInfoRequestModel();
        requestModel.setName("Alex");
        requestModel.setJob("hard worker");
        UserInfoResponseModel responseModel = step("Update user request", () -> given(requestSpec).log().uri()
                .body(requestModel)
                .when()
                .put("/users/4")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(UserInfoResponseModel.class));
        step("Verify response", () -> {
            assertThat(responseModel.getName()).isEqualTo("Alex");
            assertThat(responseModel.getJob()).isEqualTo("hard worker");
        });
    }

    @Test
    void checkDeleteUserTest() {
        step("Delete user request", () ->  given(requestSpec)
                .when()
                .delete("/users/4")
                .then()
                .spec(responseSpec)
                .statusCode(204));
    }

    @Test
    void checkGetUserTest() {
        UserFullInfoModel userFullInfoModel = step("Get user request", () -> given(requestSpec)
                .when()
                .get("/users/7")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(UserFullInfoModel.class));
        step("Verify response", () ->
        assertThat(userFullInfoModel.getData().getFirstName()).isEqualTo("Michael"));
    }

   @Test
    void checkRegisterUserTest() {
       RegisterRequestModel registerRequestModel = new RegisterRequestModel();
       registerRequestModel.setEmail("eve.holt@reqres.in");
       registerRequestModel.setPassword("pistol");
       RegisterResponseModel registerResponseModel = step("Register user request", () -> given(requestSpec)
                .body(registerRequestModel)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(RegisterResponseModel.class));
       step("Verify response", () ->
       assertThat(registerResponseModel.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));

    }

    @Test
    void checkGetListUserTest() {
        UserListModel userListModel = step("Get list user request", () -> given(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(UserListModel.class));
        step("Verify response", () -> {
            assertThat(userListModel.getData().get(1).getId()).isEqualTo(8);
            assertThat(userListModel.getData().get(1).getFirstName()).isEqualTo("Lindsay");
        });

    }
}
