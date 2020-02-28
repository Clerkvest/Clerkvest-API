package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.entity.investment.InvestDTO;
import de.clerkvest.api.entity.project.ProjectDTO;
import de.clerkvest.api.entity.project.ProjectService;
import io.restassured.response.ValidatableResponse;
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

public class GetInvestmentResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/invest/";
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProjectService projectService;

    @Test
    public void getInvestment_0() {
        InvestDTO rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.INVEST_SINGLE + 1).then().statusCode(OK.value()).extract().as(InvestDTO.class);
        EmployeeDTO restEmployee = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 1).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO restProject = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        InvestDTO investMent0 = InvestDTO.builder().employeeId(restEmployee.getId()).investment(new BigDecimal("5.00")).projectId(restProject.getId()).investId(1L).build();
        assertThat(investMent0).isEqualTo(rest);
    }

    @Test
    public void getInvestment_1() {
        InvestDTO rest = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.INVEST_SINGLE + 2).then().statusCode(OK.value()).extract().as(InvestDTO.class);
        EmployeeDTO restEmployee = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.EMPLOYEE_SINGLE + 2).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO restProject = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        InvestDTO investMent0 = InvestDTO.builder().employeeId(restEmployee.getId()).investment(new BigDecimal("5.00")).projectId(restProject.getId()).investId(2L).build();
        assertThat(investMent0).isEqualTo(rest);
    }

    @Test
    public void getInvestment_2() {
        InvestDTO rest = given().header("Authorization", "Bearer exampleToken2").get(HateoasLink.INVEST_SINGLE + 3).then().statusCode(OK.value()).extract().as(InvestDTO.class);
        EmployeeDTO restEmployee = given().header("Authorization", "Bearer exampleToken2").get(HateoasLink.EMPLOYEE_SINGLE + 3).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO restProject = given().header("Authorization", "Bearer exampleToken2").get(HateoasLink.PROJECT_SINGLE + 2).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        InvestDTO investMent0 = InvestDTO.builder().employeeId(restEmployee.getId()).investment(new BigDecimal("5.00")).projectId(restProject.getId()).investId(3L).build();
        assertThat(investMent0).isEqualTo(rest);
    }

    @Test
    public void getInvestmentFromOtherCompany() {
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.INVEST_SINGLE + 3).then().statusCode(FORBIDDEN.value());

    }
}
