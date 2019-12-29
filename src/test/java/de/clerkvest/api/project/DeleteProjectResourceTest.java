package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class DeleteProjectResourceTest {


    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/project";

    @Test
    public void deleteProjectAsSelf() {
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken0").delete(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value());
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(NOT_FOUND.value());
    }


    @Test
    public void deleteProjectAsAdmin() {
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken1").delete(REST_ENDPOINT_URL + "/0").then().statusCode(BAD_REQUEST.value());
        //ValidatableResponse rest = given().header("X-API-Key","exampleToken0").get(REST_ENDPOINT_URL+"/0").then().statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void deleteProjectAsNonAdmin() {
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken0").delete(REST_ENDPOINT_URL + "/1").then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void deleteProjectAsForeignAdmin() {
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken3").delete(REST_ENDPOINT_URL + "/0").then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void deleteProjectForeignEmployee() {
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken2").delete(REST_ENDPOINT_URL + "/0").then().statusCode(BAD_REQUEST.value());
    }
}
