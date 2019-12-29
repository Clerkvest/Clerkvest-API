package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.project.Project;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
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
public class PostInvestmentResourceTest {


    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/invest";

    @Test
    public void postInvestment_Open_Project() {
        BigDecimal invest = BigDecimal.valueOf(5);
        Employee employee0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        Project projectRest = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        Invest inRest = Invest.builder().employeeId(employee0).investment(invest).projectId(projectRest).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
        Employee employee0changed = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        Project projectRestchanged = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        assertThat(employee0.getBalance().subtract(invest)).isEqualTo(employee0changed.getBalance());
        assertThat(projectRest.getInvestedIn().add(invest)).isEqualTo(projectRestchanged.getInvestedIn());
    }

    @Test
    public void postInvestment_Open_Project_Invalid_Balance() {
        BigDecimal invest = BigDecimal.valueOf(6);
        Employee employee0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        Project projectRest = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        Invest inRest = Invest.builder().employeeId(employee0).investment(invest).projectId(projectRest).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void postInvestment_Closed_Project() {
        BigDecimal invest = BigDecimal.valueOf(5);
        Employee employee2 = given().header("X-API-Key", "exampleToken2").get(REST_BASE_URL + "/employee/2").then().statusCode(OK.value()).extract().as(Employee.class);
        Project projectRest = given().header("X-API-Key", "exampleToken2").get(REST_BASE_URL + "/project/1").then().statusCode(OK.value()).extract().as(Project.class);
        Invest inRest = Invest.builder().employeeId(employee2).investment(invest).projectId(projectRest).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken2").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void postInvestment_Foreign() {
        BigDecimal invest = BigDecimal.valueOf(5);
        Employee employee0 = given().header("X-API-Key", "exampleToken2").get(REST_BASE_URL + "/employee/2").then().statusCode(OK.value()).extract().as(Employee.class);
        Project projectRest = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        Invest inRest = Invest.builder().employeeId(employee0).investment(invest).projectId(projectRest).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken2").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void postInvestment_Fake_Sender() {
        BigDecimal invest = BigDecimal.valueOf(5);
        Employee employee0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").then().statusCode(OK.value()).extract().as(Employee.class);
        Project projectRest = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/project/0").then().statusCode(OK.value()).extract().as(Project.class);
        Invest inRest = Invest.builder().employeeId(employee0).investment(invest).projectId(projectRest).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken2").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

}
