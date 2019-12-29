package de.clerkvest.api.employee.comment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.entity.employee.comment.EmployeeComment;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PostEmployeeCommentResourceTest {
    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/employee/comment";

    @Autowired
    EmployeeService service;
    @Test
    public void postEmployeeCommentAsSelf() {
        Employee employee0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        EmployeeComment employee0Comment = EmployeeComment.builder().comment("Test Text").employeeId(service.getById(employee0.getEmployeeId()).get()).commenterId(service.getById(employee0.getEmployeeId()).get()).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").contentType(ContentType.JSON).body(employee0Comment).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void postEmployeeCommentAsEmployee() {
        Employee employee0 = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        Employee employee1 = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/employee/1").then().statusCode(OK.value()).extract().as(Employee.class);
        EmployeeComment employee0Comment = EmployeeComment.builder().comment("Test Text").employeeId(service.getById(employee0.getEmployeeId()).get()).commenterId(service.getById(employee1.getEmployeeId()).get()).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken1").contentType(ContentType.JSON).body(employee0Comment).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void postEmployeeCommentAsForeign() {
        Employee employee0 = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        Employee employee3 = given().header("X-API-Key", "exampleToken3").get(REST_BASE_URL + "/employee/3").then().statusCode(OK.value()).extract().as(Employee.class);
        EmployeeComment employee0Comment = EmployeeComment.builder().comment("Test Text").employeeId(service.getById(employee0.getEmployeeId()).get()).commenterId(service.getById(employee3.getEmployeeId()).get()).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken3").body(employee0Comment).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void postEmployeeCommentAsFakeSelf() {
        List<EmployeeComment> employeeComments = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/employee/0/comments").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", EmployeeComment.class);
        EmployeeComment employee0Comment = employeeComments.get(0);
        Employee employee0 = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        employee0Comment.setCommenterId(service.getById(employee0.getEmployeeId()).get());
        employee0Comment.setComment("Test Text");
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken1").body(employee0Comment).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }
}
