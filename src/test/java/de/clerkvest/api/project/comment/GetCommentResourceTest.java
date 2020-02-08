package de.clerkvest.api.project.comment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.project.comment.ProjectCommentDTO;
import io.restassured.response.ValidatableResponse;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@AutoConfigureEmbeddedDatabase
public class GetCommentResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/comment/";

    @Test
    public void getProjectCommentsAsSelf() {
        List employee0Comment = given().header("Authorization", "Bearer exampleToken0").get(REST_ENDPOINT_URL + 0 + "/comments").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", ProjectCommentDTO.class);
    }

    @Test
    public void getProjectCommentsAsEmployee() {
        List employee0Comment = given().header("Authorization", "Bearer exampleToken1").get(REST_ENDPOINT_URL + 0 + "/comments").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", ProjectCommentDTO.class);
    }

    @Test
    public void getProjectCommentsAsForeign() {
        ValidatableResponse employee0Comment = given().header("Authorization", "Bearer exampleToken2").get(REST_ENDPOINT_URL + 0 + "/comments").then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void postProjectCommentsAsForeignAdmin() {
        ValidatableResponse employee0Comment = given().header("Authorization", "Bearer exampleToken3").get(REST_ENDPOINT_URL + 0 + "/comments").then().statusCode(FORBIDDEN.value());
    }
}
