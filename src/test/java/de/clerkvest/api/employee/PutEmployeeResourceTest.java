package de.clerkvest.api.employee;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.employee.Employee;
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
public class PutEmployeeResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/employee";

    @Test
    public void updateEmployeeAsSelf() {
        String name = "TEST NAME";
        Employee rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Employee.class);
        rest.setNickname(name);
        Employee updated = given().header("X-API-Key", "exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(Employee.class);
        assertThat(rest.getEmployeeId()).isEqualTo(updated.getEmployeeId());
        assertThat(name).isEqualTo(updated.getNickname());
    }

    @Test
    public void updateEmployeeAsAdmin() {
        String name = "TEST NAME";
        Employee rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Employee.class);
        rest.setNickname(name);
        Employee updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(Employee.class);
        assertThat(rest.getEmployeeId()).isEqualTo(updated.getEmployeeId());
        assertThat(name).isEqualTo(updated.getNickname());
    }

    @Test
    public void updateEmployeeAsNonAdmin() {
        String name = "TEST NAME";
        Employee rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + "/1").then().statusCode(OK.value()).extract().as(Employee.class);
        rest.setNickname(name);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void updateEmployeeAsForeignAdmin() {
        String name = "TEST NAME";
        Employee rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Employee.class);
        rest.setNickname(name);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken3").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());

    }

    @Test
    public void updateEmployeeAsForeignEmployee() {
        String name = "TEST NAME";
        Employee rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Employee.class);
        rest.setNickname(name);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken2").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

}
