package de.clerkvest.api.employee;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import io.restassured.response.ValidatableResponse;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@AutoConfigureEmbeddedDatabase
public class GetEmployeeResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/employee/get/";

    @Autowired
    CompanyService service;


    @Test
    public void getEmployee_0() {
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken1").get(REST_ENDPOINT_URL + 0).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        EmployeeDTO employee0 = new EmployeeDTO().employeeId(0L).company(0).email("user1@clerkvest.de").balance(BigDecimal.valueOf(5)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").isAdmin(false);
        assertThat(rest).isEqualTo(employee0);
    }

    @Test
    public void getEmployee_1() {
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken1").get(REST_ENDPOINT_URL + 1).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        EmployeeDTO employee1 = new EmployeeDTO().employeeId(1L).company(0).email("user2@clerkvest.de").balance(BigDecimal.valueOf(6)).token(null).firstname("Bike").lastname("User2").nickname("User2ClerkAdmin").isAdmin(true);
        assertThat(rest).isEqualTo(employee1);
    }

    @Test
    public void getEmployee_2() {
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken2").get(REST_ENDPOINT_URL + 2).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        EmployeeDTO employee2 = new EmployeeDTO().employeeId(2L).company(1).email("user1@company.de").balance(BigDecimal.valueOf(7)).token(null).firstname("Mike").lastname("User").nickname("User1CompanyNonAdmin").isAdmin(false);
        assertThat(rest).isEqualTo(employee2);
    }

    @Test
    public void getEmployee_3() {
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken3").get(REST_ENDPOINT_URL + 3).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        EmployeeDTO employee3 = new EmployeeDTO().employeeId(3L).company(1).email("user2@company.de").balance(BigDecimal.valueOf(13)).token(null).firstname("Bike").lastname("User2").nickname("User2CompanyAdmin").isAdmin(true);
        assertThat(rest).isEqualTo(employee3);
    }

    @Test
    public void getEmployeeFromOtherCompany() {
        ValidatableResponse response = given().header("Authorization", "Bearer exampleToken3").get(REST_ENDPOINT_URL + 0).then().statusCode(FORBIDDEN.value());
    }


}
