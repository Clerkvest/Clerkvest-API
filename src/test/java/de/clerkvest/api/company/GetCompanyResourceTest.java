package de.clerkvest.api.company;


import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.CompanyDTO;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.EmployeeService;
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
public class GetCompanyResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/company/get/";

    @Autowired
    private CompanyService service;
    @Autowired
    private EmployeeService employeeService;

    public GetCompanyResourceTest() {
    }

    @Test
    public void getCompany_0() {
        CompanyDTO rest = given().header("Authorization", "Bearer exampleToken0").get(REST_ENDPOINT_URL + 0).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        CompanyDTO company0 = CompanyDTO.builder().companyId(0L).name("Clerk GmbH").domain("clerkvest.de").payAmount(BigDecimal.valueOf(25)).payInterval(1).inviteOnly(true).imageId(rest.getImage()).build();
        assertThat(rest).isEqualTo(company0);
        //Assert.assertEquals(rest, company0);
    }

    @Test
    public void getCompany_1() {
        CompanyDTO rest = given().header("Authorization", "Bearer exampleToken2").get(REST_ENDPOINT_URL + 1).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        CompanyDTO company1 = CompanyDTO.builder().companyId(1L).name("Company GmbH").domain("company.de").payAmount(BigDecimal.valueOf(15)).payInterval(30).inviteOnly(false).imageId(rest.getImage()).build();
        assertThat(rest).isEqualTo(company1);
    }

    @Test
    public void getForeignCompany() {
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").get(REST_ENDPOINT_URL + 1).then().statusCode(FORBIDDEN.value());

    }
}
