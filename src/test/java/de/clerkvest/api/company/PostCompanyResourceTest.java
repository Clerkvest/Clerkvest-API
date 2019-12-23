package de.clerkvest.api.company;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.Company;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insertData.sql")})
@Transactional
public class PostCompanyResourceTest {
    private Company company;

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyEmptyDomain() {
        company = Company.builder().name("titCompany").domain("").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyInvalidDomain() {
        company = Company.builder().name("titCompany").domain("titcom").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyInvalidDomain_1() {
        company = Company.builder().name("titCompany").domain("-tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyInvalidDomain_2() {
        company =  Company.builder().name("titCompany").domain("tit-.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyInvalidDomain_3() {
        company =  Company.builder().name("titCompany").domain("ti--t.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Test
    public void createInvalidCompanyPayAmountBelow_1() {
        company =  Company.builder().name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(-1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Test
    public void createInvalidCompanyPayIntervalBelow_1() {
        company =  Company.builder().name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(Long.MIN_VALUE)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Test
    public void createCompanyDuplicate() {
        company =  Company.builder().name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(OK.value());
        company =  Company.builder().name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());

    }

    @Test
    public void createCompanyPost_1() {
        company = Company.builder().companyId(-1L).name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(REST_BASE_URL + "/company?mail=test@tit.com").then().statusCode(OK.value());
        //CompanyRest rest = response.extract().as(CompanyRest.class);
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

}
