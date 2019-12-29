package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.project.Project;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class GetProjectResourceTest {


    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/project/";


    @Test
    public void getProject_0() {
        Project rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + 0).then().statusCode(OK.value()).extract().as(Project.class);
    }

    @Test
    public void getProject_1() {
        Project rest = given().header("X-API-Key", "exampleToken2").get(REST_ENDPOINT_URL + 1).then().statusCode(OK.value()).extract().as(Project.class);
    }

    @Test
    public void getEmployeeFromOtherCompany() {
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + 1).then().statusCode(BAD_REQUEST.value());
    }
}
