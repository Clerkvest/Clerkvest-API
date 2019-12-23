package de.clerkvest.api.project.comment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insertData.sql")})
@Transactional
public class PostCommentResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/comment";

    @Test
    public void postEmployeeCommentAsSelf() {
        Project project0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        ProjectComment employee0Comment = ProjectComment.builder().text("Test Text").projectId(project0).employeeId(project0.getEmployeeId()).title("TEST").build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").contentType(ContentType.JSON).body(employee0Comment).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void postEmployeeCommentAsEmployee() {
        Employee employee1 = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/employee/1").then().statusCode(OK.value()).extract().as(Employee.class);
        Project project0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        ProjectComment employee0Comment = ProjectComment.builder().text("Test Text").projectId(project0).employeeId(employee1).title("Test").build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken1").contentType(ContentType.JSON).body(employee0Comment).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void postEmployeeCommentAsForeign() {
        Employee employee3 = given().header("X-API-Key", "exampleToken3").get(REST_BASE_URL + "/employee/3").then().statusCode(OK.value()).extract().as(Employee.class);
        Project project0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        ProjectComment employee0Comment = ProjectComment.builder().text("Test Text").projectId(project0).employeeId(employee3).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken3").contentType(ContentType.JSON).body(employee0Comment).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

}
