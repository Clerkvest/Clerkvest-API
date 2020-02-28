package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.investment.InvestDTO;
import de.clerkvest.api.entity.project.ProjectDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class PostInvestmentResourceTest {
    private final static String REST_ENDPOINT_URL = HateoasLink.INVEST_CREATE;

    @Test
    public void postInvestment_Open_Project() {
        BigDecimal invest = BigDecimal.valueOf(5);
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 1).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO projectRest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        InvestDTO inRest = InvestDTO.builder().employeeId(employee0.getId()).investment(invest).projectId(projectRest.getId()).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
        EmployeeDTO employee0changed = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 1).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO projectRestchanged = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        assertThat(employee0.getBalance().subtract(invest)).isEqualTo(employee0changed.getBalance());
        assertThat(projectRest.getInvestedIn().add(invest)).isEqualTo(projectRestchanged.getInvestedIn());
    }

    @Test
    public void postInvestment_Open_Project_Invalid_Balance() {
        BigDecimal invest = BigDecimal.valueOf(6);
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 1).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO projectRest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        InvestDTO inRest = InvestDTO.builder().employeeId(employee0.getId()).investment(invest).projectId(projectRest.getId()).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(CONFLICT.value());
    }

    @Test
    public void postInvestment_Closed_Project() {
        BigDecimal invest = BigDecimal.valueOf(5);
        EmployeeDTO employee2 = given().header("Authorization", "Bearer exampleToken2").get(HateoasLink.EMPLOYEE_SINGLE + 3).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO projectRest = given().header("Authorization", "Bearer exampleToken2").get(HateoasLink.PROJECT_SINGLE + 2).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        InvestDTO inRest = InvestDTO.builder().employeeId(employee2.getId()).investment(invest).projectId(projectRest.getId()).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken2").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(CONFLICT.value());
    }

    @Test
    public void postInvestment_Foreign() {
        BigDecimal invest = BigDecimal.valueOf(5);
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken2").get(HateoasLink.EMPLOYEE_SINGLE + 3).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO projectRest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        InvestDTO inRest = InvestDTO.builder().employeeId(employee0.getId()).investment(invest).projectId(projectRest.getId()).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken2").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void postInvestment_Fake_Sender() {
        BigDecimal invest = BigDecimal.valueOf(5);
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 1).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO projectRest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        InvestDTO inRest = InvestDTO.builder().employeeId(employee0.getId()).investment(invest).projectId(projectRest.getId()).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken2").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void postInvestmentFinishProject() {
        EmployeeDTO employee0 = given().header("Authorization", "Bearer exampleToken4").get(HateoasLink.EMPLOYEE_SINGLE + 5).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO projectRest = given().header("Authorization", "Bearer exampleToken4").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        BigDecimal invest = projectRest.getGoal().subtract(projectRest.getInvestedIn());
        InvestDTO inRest = InvestDTO.builder().employeeId(employee0.getId()).investment(invest).projectId(projectRest.getId()).build();
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken4").body(inRest).contentType(ContentType.JSON).post(REST_ENDPOINT_URL).then().statusCode(OK.value());
        EmployeeDTO employee0changed = given().header("Authorization", "Bearer exampleToken4").get(HateoasLink.EMPLOYEE_SINGLE + 5).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        ProjectDTO projectRestchanged = given().header("Authorization", "Bearer exampleToken4").get(HateoasLink.PROJECT_SINGLE + 1).then().statusCode(OK.value()).extract().as(ProjectDTO.class);
        assertThat(employee0.getBalance().subtract(invest)).isEqualTo(employee0changed.getBalance());
        assertThat(projectRest.getInvestedIn().add(invest)).isEqualTo(projectRestchanged.getInvestedIn());
    }

}
