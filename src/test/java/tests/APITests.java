package tests;

import models.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ReqresSpec.requestWithBodySpec;
import static specs.ReqresSpec.requestWithoutBodySpec;
import static specs.ReqresSpec.responseWithBodySpec;
import static specs.ReqresSpec.responseWithoutBodySpec;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITests extends TestBase {
    static String userId;
    @Test
    @Order(1)
    void checkCreateUserTest() {
        UserInfoRequestModel requestModel = new UserInfoRequestModel();
        requestModel.setName("Alex");
        requestModel.setJob("freeloader");
        UserInfoResponseModel responseModel = step("Create user request", () -> given(requestWithBodySpec)
                .body(requestModel)
                .when()
                .post("/users")
                .then()
                .spec(responseWithBodySpec)
                .statusCode(201)
                .extract().as(UserInfoResponseModel.class));
        step("Verify response", () -> {
        assertEquals("Alex", responseModel.getName());
        assertEquals("freeloader", responseModel.getJob());
            userId = responseModel.getId();
        });

    }

    @Test
    @Order(2)
    void checkUpdateUserTest() {
        UserInfoRequestModel requestModel = new UserInfoRequestModel();
        requestModel.setName("Alex");
        requestModel.setJob("hard worker");
        UserInfoResponseModel responseModel = step("Update user request", () -> given(requestWithBodySpec).log().uri()
                .body(requestModel)
                .when()
                .put("/users/" + userId)
                .then()
                .spec(responseWithBodySpec)
                .statusCode(200)
                .extract().as(UserInfoResponseModel.class));
        step("Verify response", () -> {
            assertEquals("Alex", responseModel.getName());
            assertEquals("hard worker", responseModel.getJob());
        });
    }

    @Test
    @Order(3)
    void checkDeleteUserTest() {
        step("Delete user request", () ->  given(requestWithoutBodySpec)
                .when()
                .delete("/users/" + userId)
                .then()
                .spec(responseWithoutBodySpec)
                .statusCode(204));
    }

    @Test
    @Order(4)
    void checkGetUserTest() {
        UserFullInfoModel userFullInfoModel = step("Get user request", () -> given(requestWithoutBodySpec)
                .when()
                .get("/users/7")
                .then()
                .spec(responseWithBodySpec)
                .statusCode(200)
                .extract().as(UserFullInfoModel.class));
        step("Verify response", () ->
        assertEquals("Michael", userFullInfoModel.getData().getFirst_name()));
    }

   @Test
    @Order(5)
    void checkRegisterUserTest() {
       RegisterRequestModel registerRequestModel = new RegisterRequestModel();
       registerRequestModel.setEmail("eve.holt@reqres.in");
       registerRequestModel.setPassword("pistol");
       RegisterResponseModel registerResponseModel = step("Register user request", () -> given(requestWithBodySpec)
                .body(registerRequestModel)
                .when()
                .post("/register")
                .then()
                .spec(responseWithBodySpec)
                .statusCode(200)
                .extract().as(RegisterResponseModel.class));
       step("Verify response", () ->
       assertEquals("QpwL5tke4Pnpja7X4", registerResponseModel.getToken()));

    }

    @Test
    @Order(6)
    void checkGetListUserTest() {
        UserListModel userListModel = step("Get list user request", () -> given(requestWithoutBodySpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseWithBodySpec)
                .statusCode(200)
                .extract().as(UserListModel.class));
        step("Verify response", () -> {
            assertEquals(8, userListModel.getData().get(1).getId());
            assertEquals("Lindsay", userListModel.getData().get(1).getFirst_name());
        });

    }
}
