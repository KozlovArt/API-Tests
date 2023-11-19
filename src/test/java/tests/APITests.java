package tests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITests extends TestBase {
    static String userId;
    @Test
    @Order(1)
    void checkCreateUser() {
        userId = given().log().uri()
                .log().method()
                .log().body()
                .body("{\n" +
                        "    \"name\": \"Alex\",\n" +
                        "    \"job\": \"freeloader\"\n" +
                        "}")
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Alex"),
                        "job", is("freeloader"))
                .extract()
                .path("id");
    }

    @Test
    @Order(2)
    void checkUpdateUser() {
        given().log().uri()
                .log().method()
                .log().body()
                .body("{\n" +
                        "    \"name\": \"Alex\",\n" +
                        "    \"job\": \"hard worker\"\n" +
                        "}")
                .contentType(JSON)
                .when()
                .put("/users/" + userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("hard worker"));
    }

    @Test
    @Order(3)
    void checkDeleteUser() {
        given().log().uri()
                .log().method()
                .when()
                .delete("/users/" + userId)
                .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    @Order(4)
    void checkGetUser() {
        given().log().uri()
                .log().method()
                .when()
                .get("/users/7")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.first_name", is("Michael"));
    }

    @Test
    @Order(5)
    void checkRegisterUser() {
        given().log().uri()
                .log().method()
                .log().body()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));;
    }
}
