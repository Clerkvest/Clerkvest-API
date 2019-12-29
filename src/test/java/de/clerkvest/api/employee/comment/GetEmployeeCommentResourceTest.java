package de.clerkvest.api.employee.comment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.employee.comment.EmployeeComment;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class GetEmployeeCommentResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/employee";

    @Test
    public void getEmployeeCommentAsSelf() {
        List employee0Comment = given().header("X-API-Key", "exampleToken0").contentType(ContentType.JSON).get(REST_ENDPOINT_URL + "/0/comments").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", EmployeeComment.class);
        assertThat(employee0Comment.isEmpty()).isFalse();
    }

    @Test
    public void getEmployeeCommentAsForeign() {
        ValidatableResponse employee0Comment = given().header("X-API-Key", "exampleToken2").contentType(ContentType.JSON).get(REST_ENDPOINT_URL + "/0/comments").then().statusCode(BAD_REQUEST.value());
    }
}
