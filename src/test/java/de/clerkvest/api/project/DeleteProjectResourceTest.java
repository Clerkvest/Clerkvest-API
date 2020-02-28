package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeleteProjectResourceTest {


    private final static String REST_ENDPOINT_URL = HateoasLink.PROJECT_DELETE;

    @Test
    public void deleteProjectAsSelf() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken0").delete(REST_ENDPOINT_URL + 1).then().statusCode(OK.value());
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(NOT_FOUND.value());
    }


    @Test
    public void deleteProjectAsAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken1").delete(REST_ENDPOINT_URL + 1).then().statusCode(FORBIDDEN.value());
        //ValidatableResponse rest = given().header("Authorization","exampleToken0").get(REST_ENDPOINT_URL+"/0").then().statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void deleteProjectAsNonAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken0").delete(REST_ENDPOINT_URL + 2).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void deleteProjectAsForeignAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken3").delete(REST_ENDPOINT_URL + 1).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void deleteProjectForeignEmployee() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken2").delete(REST_ENDPOINT_URL + 1).then().statusCode(FORBIDDEN.value());
    }
}
