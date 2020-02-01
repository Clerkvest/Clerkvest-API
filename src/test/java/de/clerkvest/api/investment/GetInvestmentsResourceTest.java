package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.investment.InvestDTO;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class GetInvestmentsResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.INVEST_ALL;

    @Test
    public void getInvestments_0() {
        List rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().body().jsonPath().getList(".", InvestDTO.class);
        assertThat(rest.isEmpty()).isFalse();
    }


    @Disabled("Disabled")
    @Test
    public void getInvestmentsFromOtherCompany() {
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + 2).then().statusCode(BAD_REQUEST.value());

    }
}
