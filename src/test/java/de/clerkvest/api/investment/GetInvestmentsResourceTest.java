package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.investment.Invest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insertData.sql")})
@Transactional
public class GetInvestmentsResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/investments/";

    @Disabled("Disabled")
    @Test
    public void getInvestments_0() {
        List rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + 0).then().statusCode(OK.value()).extract().body().jsonPath().getList(".", Invest.class);
        assertThat(rest.isEmpty()).isFalse();
    }


    @Test
    public void getInvestmentsFromOtherCompany() {
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + 2).then().statusCode(BAD_REQUEST.value());

    }
}
