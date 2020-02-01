package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.project.ProjectDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class PutProjectResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.PROJECT_UPDATE;

    @Test
    public void updateProjectAsSelf() {
        String name = "TEST DESC";
        ProjectDTO rest = given().header("X-API-Key", "exampleToken0").get(HateoasLink.PROJECT_SINGLE + 0).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        rest.setDescription(name);
        ProjectDTO updated = given().header("X-API-Key", "exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        assertThat(rest).isEqualTo(updated);
        assertThat(name).isEqualTo(updated.getDescription());
    }

    @Test
    public void updateProjectAsAdmin() {
        ProjectDTO rest = given().header("X-API-Key", "exampleToken1").get(HateoasLink.PROJECT_SINGLE + 0).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void updateProjectAsForeignAdmin() {
        ProjectDTO rest = given().header("X-API-Key", "exampleToken0").get(HateoasLink.PROJECT_SINGLE + 0).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken3").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }
}
