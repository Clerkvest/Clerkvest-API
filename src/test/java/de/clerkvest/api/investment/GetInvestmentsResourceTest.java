package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.investment.InvestDTO;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class GetInvestmentsResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.BASE_ENDPOINT;

    @Test
    public void getInvestmentsByEmployee() {
        List<InvestDTO> rest = given().header("Authorization", "Bearer exampleToken0").get(REST_ENDPOINT_URL + "/invest/all/1").then().statusCode(OK.value()).extract().body().jsonPath().getList(".", InvestDTO.class);
        assertThat(rest.isEmpty()).isFalse();
    }


    @Disabled("Disabled")
    @Test
    public void getInvestmentsFromOtherCompany() {
        ValidatableResponse rest = given().header("Authorization", "Bearer exampleToken0").get(REST_ENDPOINT_URL + 3).then().statusCode(BAD_REQUEST.value());

    }
}
