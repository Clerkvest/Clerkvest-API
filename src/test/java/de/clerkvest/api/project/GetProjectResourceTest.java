package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.project.ProjectDTO;
import io.restassured.response.ValidatableResponse;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureEmbeddedDatabase
public class GetProjectResourceTest {
    private final static String REST_ENDPOINT_URL = HateoasLink.PROJECT_SINGLE;


    @Test
    public void getProject_0() {
        ProjectDTO rest = given().header("Authorization", "Bearer exampleToken1").get(REST_ENDPOINT_URL + 0).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
    }

    @Test
    public void getProject_1() {
        ProjectDTO rest = given().header("Authorization", "Bearer exampleToken2").get(REST_ENDPOINT_URL + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
    }

    @Test
    public void getEmployeeFromOtherCompany() {
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken1").get(REST_ENDPOINT_URL + 1).then().statusCode(FORBIDDEN.value());
    }
}
