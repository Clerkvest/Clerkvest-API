package de.clerkvest.api.employee;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import io.restassured.response.ValidatableResponse;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@AutoConfigureEmbeddedDatabase
public class DeleteEmployeeResourceTest {
    private final static String REST_ENDPOINT_URL = HateoasLink.EMPLOYEE_DELETE;

    @Test
    public void deleteEmployeeAsSelf() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken0").delete(REST_ENDPOINT_URL + 0).then().statusCode(OK.value());
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.EMPLOYEE_SINGLE + 0).then().statusCode(NOT_FOUND.value());
    }


    @Test
    public void deleteEmployeeAsAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken1").delete(REST_ENDPOINT_URL + 0).then().statusCode(OK.value());
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.EMPLOYEE_SINGLE + 0).then().statusCode(NOT_FOUND.value());
    }

    @Test
    public void deleteEmployeeAsNonAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken0").delete(REST_ENDPOINT_URL + 1).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void deleteEmployeeAsForeignAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken3").delete(REST_ENDPOINT_URL + 0).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void deleteEmployeeAsForeignEmployee() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken2").delete(REST_ENDPOINT_URL + 0).then().statusCode(FORBIDDEN.value());
    }
}
