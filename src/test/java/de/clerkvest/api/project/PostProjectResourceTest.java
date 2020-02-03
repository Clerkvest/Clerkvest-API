package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.company.CompanyDTO;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.project.ProjectDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class PostProjectResourceTest {
    private final static String REST_ENDPOINT_URL = HateoasLink.PROJECT_CREATE;

    @Test
    public void addProject_ToOwnCompany_Admin() {
        EmployeeDTO employee1 = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.EMPLOYEE_SINGLE + 1).as(EmployeeDTO.class);
        ProjectDTO project = ProjectDTO.builder().companyId(employee1.getCompany()).employeeId(employee1.getId()).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken1").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void addProject_ToOwnCompany_NonAdmin() {
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 0).as(EmployeeDTO.class);
        ProjectDTO project = ProjectDTO.builder().companyId(employee0.getCompany()).employeeId(employee0.getId()).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
    }

    @Test
    public void addProject_ToCompany_ForeignAdmin() {
        EmployeeDTO employee3 = given().header("Authorization", "Bearer exampleToken3").get(HateoasLink.EMPLOYEE_SINGLE + 3).as(EmployeeDTO.class);
        CompanyDTO company = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.COMPANY_SINGLE + 0).as(CompanyDTO.class);
        ProjectDTO project = ProjectDTO.builder().companyId(company.getId()).employeeId(employee3.getId()).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken3").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void addProject_ToCompany_FakeEmployee() {
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 0).as(EmployeeDTO.class);
        ProjectDTO project = ProjectDTO.builder().companyId(employee0.getCompany()).employeeId(employee0.getId()).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken1").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void addProject_ToCompany_FakeCompany() {
        CompanyDTO company = given().header("Authorization", "Bearer exampleToken3").get(HateoasLink.COMPANY_SINGLE + 1).as(CompanyDTO.class);
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 0).as(EmployeeDTO.class);
        ProjectDTO project = ProjectDTO.builder().companyId(company.getId()).employeeId(employee0.getId()).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(1337)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void addProject_ToCompany_NegativGoal() {
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 0).as(EmployeeDTO.class);
        ProjectDTO project = ProjectDTO.builder().companyId(employee0.getCompany()).employeeId(employee0.getId()).link("ExampleLink").title("Title").description("Description").goal(BigDecimal.valueOf(-1)).investedIn(BigDecimal.valueOf(0)).reached(false).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").contentType(ContentType.JSON).body(project).post(REST_ENDPOINT_URL).then().statusCode(CONFLICT.value());
    }

}
