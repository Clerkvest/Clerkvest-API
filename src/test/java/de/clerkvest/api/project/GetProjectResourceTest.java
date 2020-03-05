package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.project.ProjectDTO;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class GetProjectResourceTest {
    private final static String REST_ENDPOINT_URL = HateoasLink.PROJECT_SINGLE;


    @Test
    public void getProject_0() {
        ProjectDTO rest = given().header("Authorization", "Bearer exampleToken1").get(REST_ENDPOINT_URL + 7).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
    }

    @Test
    public void getProject_1() {
        ProjectDTO rest = given().header("Authorization", "Bearer exampleToken2").get(REST_ENDPOINT_URL + 8).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
    }

    @Test
    public void getEmployeeFromOtherCompany() {
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken1").get(REST_ENDPOINT_URL + 8).then().statusCode(FORBIDDEN.value());
    }
}
