package de.clerkvest.api.project.comment;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.project.ProjectDTO;
import de.clerkvest.api.entity.project.comment.ProjectCommentDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureEmbeddedDatabase
public class PostCommentResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.PROJECT_COMMENT_CREATE;

    @Test
    public void postCommentAsSelf() {
        ProjectDTO project0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + "0").then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        ProjectCommentDTO employee0Comment = ProjectCommentDTO.builder().text("Test Text").projectId(project0.getId()).employeeId(project0.getEmployeeId()).title("TEST").build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").contentType(ContentType.JSON).body(employee0Comment).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void postCommentAsEmployee() {
        EmployeeDTO employee1 = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.EMPLOYEE_SINGLE + "1").then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO project0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + "0").then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        ProjectCommentDTO employee0Comment = ProjectCommentDTO.builder().text("Test Text").projectId(project0.getId()).employeeId(employee1.getId()).title("Test").build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken1").contentType(ContentType.JSON).body(employee0Comment).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void postCommentAsForeign() {
        EmployeeDTO employee3 = given().header("Authorization", "Bearer exampleToken3").get(HateoasLink.EMPLOYEE_SINGLE + "3").then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO project0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + "0").then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        ProjectCommentDTO employee0Comment = ProjectCommentDTO.builder().text("Test Text").projectId(project0.getId()).employeeId(employee3.getId()).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken3").contentType(ContentType.JSON).body(employee0Comment).post(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }
}
