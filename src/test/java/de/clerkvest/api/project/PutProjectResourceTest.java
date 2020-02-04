package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.project.ProjectDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureEmbeddedDatabase
public class PutProjectResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.PROJECT_UPDATE;

    @Test
    public void updateProjectAsSelf() {
        String name = "TEST DESC";
        ProjectDTO rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 0).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        rest.setDescription(name);
        ProjectDTO updated = given().header("Authorization", "Bearer exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        assertThat(rest).isEqualTo(updated);
        assertThat(name).isEqualTo(updated.getDescription());
    }

    @Test
    public void updateProjectAsAdmin() {
        ProjectDTO rest = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.PROJECT_SINGLE + 0).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void updateProjectAsForeignAdmin() {
        ProjectDTO rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 0).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken3").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }
}
