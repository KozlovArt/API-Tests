package api;

import io.restassured.response.Response;
import models.LoginModel;

import static io.restassured.RestAssured.given;
import static specs.ReqresSpec.requestSpec;
import static specs.ReqresSpec.responseSpec;

public class Authorization {

     public Response getAuthResponse() {

        LoginModel loginModel = new LoginModel();
        loginModel.setUserName("Test");
        loginModel.setPassword("Test(111)^");

       return given(requestSpec)
                .body(loginModel)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().response();
    }
}
