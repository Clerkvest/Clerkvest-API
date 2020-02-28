package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.project.ProjectDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetProjectsResourceTest {


    private final static String REST_ENDPOINT_URL = HateoasLink.PROJECT_ALL;


    @Test
    public void getProject_0() {
        List<ProjectDTO> rest = given().header("Authorization", "Bearer exampleToken1").get(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().body().jsonPath().getList(".", ProjectDTO.class);
        assertThat(rest.isEmpty()).isFalse();
    }

    @Test
    public void getProject_1() {
        List<ProjectDTO> rest = given().header("Authorization", "Bearer exampleToken2").get(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().body().jsonPath().getList(".", ProjectDTO.class);
        assertThat(rest.isEmpty()).isFalse();
    }

}
