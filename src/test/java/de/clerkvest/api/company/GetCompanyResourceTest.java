package de.clerkvest.api.company;


import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.Company;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insertData.sql")})
@Transactional
public class GetCompanyResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/company/get/";

    @Test
    public void getCompany_0() {
        Company rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + 0).then().statusCode(OK.value()).extract().as(Company.class);
        Company company0 = Company.builder().companyId(0L).name("Clerk GmbH").domain("clerkvest.de").payAmount(BigDecimal.valueOf(25)).payInterval(1).inviteOnly(true).imageId(rest.getImageId()).build();
        assertThat(rest).isEqualTo(company0);
        //Assert.assertEquals(rest, company0);
    }

    @Test
    public void getCompany_1() {
        Company rest = given().header("X-API-Key", "exampleToken2").get(REST_ENDPOINT_URL + 1).then().statusCode(OK.value()).extract().as(Company.class);
        Company company1 = Company.builder().companyId(1L).name("Company GmbH").domain("company.de").payAmount(BigDecimal.valueOf(15)).payInterval(30).inviteOnly(false).imageId(rest.getImageId()).build();
        assertThat(rest).isEqualTo(company1);
    }

    @Test
    public void getForeignCompany() {
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + 1).then().statusCode(FORBIDDEN.value());

    }
}
