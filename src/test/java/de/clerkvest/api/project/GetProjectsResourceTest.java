package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.project.Project;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insertData.sql")})
@Transactional
public class GetProjectsResourceTest {


    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/projects";


    @Test
    public void getProject_0() {
        List rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().body().jsonPath().getList(".", Project.class);
        assertThat(rest.isEmpty()).isFalse();
    }

    @Test
    public void getProject_1() {
        List rest = given().header("X-API-Key", "exampleToken2").get(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().body().jsonPath().getList(".", Project.class);
        assertThat(rest.isEmpty()).isFalse();
    }

}
