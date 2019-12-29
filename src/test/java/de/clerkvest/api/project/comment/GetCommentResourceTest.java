package de.clerkvest.api.project.comment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class GetCommentResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/comments";

    @Test
    public void getProjectCommentsAsSelf() {
        List employee0Comment = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", ProjectComment.class);
    }

    @Test
    public void getProjectCommentsAsEmployee() {
        List employee0Comment = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", ProjectComment.class);
    }

    @Test
    public void getProjectCommentsAsForeign() {
        ValidatableResponse employee0Comment = given().header("X-API-Key", "exampleToken2").get(REST_ENDPOINT_URL + "/0").then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void postProjectCommentsAsForeignAdmin() {
        ValidatableResponse employee0Comment = given().header("X-API-Key", "exampleToken3").get(REST_ENDPOINT_URL + "/0").then().statusCode(BAD_REQUEST.value());
    }
}
