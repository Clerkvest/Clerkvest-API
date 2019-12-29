package de.clerkvest.api.employee;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.Employee;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class PostEmployeeResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/employee";

    @Autowired
    CompanyService service;

    @Test
    public void addEmployee_ToOwnCompany_Admin() {
        Employee employee0 = Employee.builder().employeeId(0L).company(service.getById(0).get()).email("user3@clerkvest.de").balance(BigDecimal.valueOf(10)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").is_admin(false).build();
        Employee rest = given().header("X-API-Key", "exampleToken1").contentType(ContentType.JSON).body(employee0).post(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(Employee.class);
        assertThat(employee0).isEqualTo(rest);
    }

    @Test
    public void addEmployee_ToOwnCompany_NonAdmin() {
        Employee employee0 = Employee.builder().employeeId(0L).company(service.getById(0).get()).email("user3@clerkvest.de").balance(BigDecimal.valueOf(10)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").is_admin(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").contentType(ContentType.JSON).body(employee0).post(REST_ENDPOINT_URL).then().statusCode(UNAUTHORIZED.value());
    }

    @Test
    public void addEmployee_ToCompany_ForeignAdmin() {
        Employee employee0 = Employee.builder().employeeId(0L).company(service.getById(0).get()).email("user3@clerkvest.de").balance(BigDecimal.valueOf(10)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").is_admin(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken3").contentType(ContentType.JSON).body(employee0).post(REST_ENDPOINT_URL).then().statusCode(UNAUTHORIZED.value());
    }

    @Disabled("Disabled")
    @Test
    public void addEmployee_ToOwnCompany_DuplicateMail() {
        //TODO Implement Mail Check
        Employee employee0 = Employee.builder().employeeId(0L).company(service.getById(0).get()).email("user1@clerkvest.de").balance(BigDecimal.valueOf(10)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").is_admin(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken3").contentType(ContentType.JSON).body(employee0).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }
}
