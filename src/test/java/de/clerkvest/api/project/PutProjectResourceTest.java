package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.project.Project;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insertData.sql")})
@Transactional
public class PutProjectResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/project";

    @Test
    public void updateProjectAsSelf() {
        String name = "TEST DESC";
        Project rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Project.class);
        rest.setDescription(name);
        Project updated = given().header("X-API-Key", "exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(Project.class);
        assertThat(rest).isEqualTo(updated);
        assertThat(name).isEqualTo(updated.getDescription());
    }

    @Test
    public void updateProjectAsAdmin() {
        Project rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Project.class);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void updateProjectAsForeignAdmin() {
        Project rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Project.class);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken3").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }
}
