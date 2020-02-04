package de.clerkvest.api.company;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.company.CompanyDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureEmbeddedDatabase
public class PostCompanyResourceTest {
    private CompanyDTO company;

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyEmptyDomain() {
        company = CompanyDTO.builder().name("titCompany").domain("").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyInvalidDomain() {
        company = CompanyDTO.builder().name("titCompany").domain("titcom").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyInvalidDomain_1() {
        company = CompanyDTO.builder().name("titCompany").domain("-tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyInvalidDomain_2() {
        company = CompanyDTO.builder().name("titCompany").domain("tit-.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Disabled("Disabled")
    @Test
    public void createInvalidCompanyInvalidDomain_3() {
        company = CompanyDTO.builder().name("titCompany").domain("ti--t.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Test
    public void createInvalidCompanyPayAmountBelow_1() {
        company = CompanyDTO.builder().name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(-1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Test
    public void createInvalidCompanyPayIntervalBelow_1() {
        company = CompanyDTO.builder().name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(Long.MIN_VALUE)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(BAD_REQUEST.value());
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

    @Test
    public void createCompanyDuplicate() {
        company = CompanyDTO.builder().name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(OK.value());
        company = CompanyDTO.builder().name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(CONFLICT.value());

    }

    @Test
    public void createCompanyPost_1() {
        company = CompanyDTO.builder().companyId(-1L).name("titCompany").domain("tit.com").inviteOnly(false).payAmount(BigDecimal.valueOf(1)).payInterval(1).build();
        ValidatableResponse response = given().body(company).contentType(ContentType.JSON).post(HateoasLink.COMPANY_CREATE + "?mail=test@tit.com").then().statusCode(OK.value());
        //CompanyRest rest = response.extract().as(CompanyRest.class);
        //EmployeeRest employeeRest = response.extract().as(EmployeeRest.class);
        //Assert.assertTrue(response. != null);
        //ResponseMessage.EMPLOYEE_OTHER_COMPANY
    }

}
