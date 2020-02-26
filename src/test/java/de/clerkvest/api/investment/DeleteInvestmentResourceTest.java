package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.investment.InvestDTO;
import io.restassured.response.ValidatableResponse;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@AutoConfigureEmbeddedDatabase
public class DeleteInvestmentResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.INVEST_DELETE;

    @Test
    public void deleteInvestmentAsSelf() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken0").delete(REST_ENDPOINT_URL + 0).then().statusCode(OK.value());
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.INVEST_SINGLE + 0).then().statusCode(NOT_FOUND.value());
    }


    @Test
    public void deleteInvestmentAsAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken1").delete(REST_ENDPOINT_URL + 0).then().statusCode(FORBIDDEN.value());
        //ValidatableResponse rest = given().header("Authorization","exampleToken0").get(REST_ENDPOINT_URL+"/0").then().statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void deleteInvestmentAsNonAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken0").delete(REST_ENDPOINT_URL + 1).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void deleteInvestmentAsForeignAdmin() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken3").delete(REST_ENDPOINT_URL + 0).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void deleteInvestmentForeignEmployee() {
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken2").delete(REST_ENDPOINT_URL + 0).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void deleteInvestmentsByProjectAndEmployee() {
        List<InvestDTO> rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.BASE_ENDPOINT + "/invest/all/0").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", InvestDTO.class);
        assertThat(rest.size()).isEqualTo(2);
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken0").delete(HateoasLink.BASE_ENDPOINT + "/invest/delete/project/0/employee/0").then().statusCode(OK.value());
        List<InvestDTO> rest1 = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.BASE_ENDPOINT + "/invest/all/0").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", InvestDTO.class);
        assertThat(rest1.size()).isEqualTo(1);
    }
}
