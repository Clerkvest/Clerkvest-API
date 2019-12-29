package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.ProjectService;
import io.restassured.response.ValidatableResponse;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class GetInvestmentResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/invest/";
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProjectService projectService;

    @Test
    public void getInvestment_0() {
        Invest rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + 0).then().statusCode(OK.value()).extract().as(Invest.class);
        Employee restEmployee = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        Project restProject = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        Invest investMent0 = Invest.builder().employeeId(restEmployee).investment(BigDecimal.valueOf(5)).projectId(restProject).investInId(0L).build();
        assertThat(investMent0).isEqualTo(rest);
    }

    @Test
    public void getInvestment_1() {
        Invest rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + 1).then().statusCode(OK.value()).extract().as(Invest.class);
        Employee restEmployee = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/employee/1").then().statusCode(OK.value()).extract().as(Employee.class);
        Project restProject = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        Invest investMent0 = Invest.builder().employeeId(restEmployee).investment(BigDecimal.valueOf(5)).projectId(restProject).investInId(1L).build();
        assertThat(investMent0).isEqualTo(rest);
    }

    @Test
    public void getInvestment_2() {
        Invest rest = given().header("X-API-Key", "exampleToken2").get(REST_ENDPOINT_URL + 2).then().statusCode(OK.value()).extract().as(Invest.class);
        Employee restEmployee = given().header("X-API-Key", "exampleToken2").get(REST_BASE_URL + "/employee/2").then().statusCode(OK.value()).extract().as(Employee.class);
        Project restProject = given().header("X-API-Key", "exampleToken2").get(REST_BASE_URL + "/project/1").then().statusCode(OK.value()).extract().as(Project.class);
        Invest investMent0 = Invest.builder().employeeId(restEmployee).investment(BigDecimal.valueOf(5)).projectId(restProject).investInId(2L).build();
        assertThat(investMent0).isEqualTo(rest);
    }

    @Test
    public void getInvestmentFromOtherCompany() {
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + 2).then().statusCode(BAD_REQUEST.value());

    }
}
