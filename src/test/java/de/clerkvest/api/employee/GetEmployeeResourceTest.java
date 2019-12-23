package de.clerkvest.api.employee;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.Employee;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
public class GetEmployeeResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/employee/";

    @Autowired
    CompanyService service;

    @Test
    public void getEmployee_0() {
        Employee rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + 0).then().statusCode(OK.value()).extract().as(Employee.class);
        Employee employee0 = Employee.builder().employeeId(0L).company(service.getById(0).get()).email("user1@clerkvest.de").balance(BigDecimal.valueOf(5)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").is_admin(false).build();
        assertThat(employee0).isEqualTo(rest);
    }

    @Test
    public void getEmployee_1() {
        Employee rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + 1).then().statusCode(OK.value()).extract().as(Employee.class);
        Employee employee1 = Employee.builder().employeeId(1L).company(service.getById(0).get()).email("user2@clerkvest.de").balance(BigDecimal.valueOf(6)).token(null).firstname("Bike").lastname("User2").nickname("User2ClerkAdmin").is_admin(true).build();
        assertThat(employee1).isEqualTo(rest);
    }

    @Test
    public void getEmployee_2() {
        Employee rest = given().header("X-API-Key", "exampleToken2").get(REST_ENDPOINT_URL + 2).then().statusCode(OK.value()).extract().as(Employee.class);
        Employee employee2 = Employee.builder().employeeId(2L).company(service.getById(1).get()).email("user1@company.de").balance(BigDecimal.valueOf(7)).token(null).firstname("Mike").lastname("User").nickname("User1CompanyNonAdmin").is_admin(false).build();
        assertThat(employee2).isEqualTo(rest);
    }

    @Test
    public void getEmployee_3() {
        Employee rest = given().header("X-API-Key", "exampleToken3").get(REST_ENDPOINT_URL + 3).then().statusCode(OK.value()).extract().as(Employee.class);
        Employee employee3 = Employee.builder().employeeId(3L).company(service.getById(1).get()).email("user2@company.de").balance(BigDecimal.valueOf(13)).token(null).firstname("Bike").lastname("User2").nickname("User2CompanyAdmin").is_admin(true).build();
        assertThat(employee3).isEqualTo(rest);
    }

    @Test
    public void getEmployeeFromOtherCompany() {
        ValidatableResponse response = given().header("X-API-Key", "exampleToken3").get(REST_ENDPOINT_URL + 0).then().statusCode(BAD_REQUEST.value());
    }


}
