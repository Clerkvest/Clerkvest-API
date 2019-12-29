package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.employee.Employee;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class PostProjectResourceTest {
    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/project";

    @Test
    public void addProject_ToOwnCompany_Admin() {
        Employee employee1 = given().header("X-API-Key", "exampleToken1").get(REST_BASE_URL + "/employee/1").as(Employee.class);
        Project project = Project.builder().companyId(employee1.getCompany()).employeeId(employee1).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken1").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void addProject_ToOwnCompany_NonAdmin() {
        Employee employee0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").as(Employee.class);
        Project project = Project.builder().companyId(employee0.getCompany()).employeeId(employee0).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void addProject_ToCompany_ForeignAdmin() {
        Employee employee3 = given().header("X-API-Key", "exampleToken3").get(REST_BASE_URL + "/employee/3").as(Employee.class);
        Company company = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/company/0").as(Company.class);
        Project project = Project.builder().companyId(company).employeeId(employee3).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken3").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void addProject_ToCompany_FakeEmployee() {
        Employee employee0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").as(Employee.class);
        Project project =Project.builder().companyId(employee0.getCompany()).employeeId(employee0).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken1").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void addProject_ToCompany_FakeCompany() {
        Company company = given().header("X-API-Key", "exampleToken3").get(REST_BASE_URL + "/company/1").as(Company.class);
        Employee employee0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").as(Employee.class);
        Project project = Project.builder().companyId(company).employeeId(employee0).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void addProject_ToCompany_NegativGoal() {
        Employee employee0 = given().header("X-API-Key", "exampleToken0").get(REST_BASE_URL + "/employee/0").as(Employee.class);
        Project project = Project.builder().companyId(employee0.getCompany()).employeeId(employee0).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(-1)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

}
