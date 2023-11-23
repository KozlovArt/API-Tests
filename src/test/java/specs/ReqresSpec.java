package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class ReqresSpec {

    public static RequestSpecification requestWithBodySpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification responseWithBodySpec = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .build();

    public static RequestSpecification requestWithoutBodySpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method();

    public static ResponseSpecification responseWithoutBodySpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .build();


}
