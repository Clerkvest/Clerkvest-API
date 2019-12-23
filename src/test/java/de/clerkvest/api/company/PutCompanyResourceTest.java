package de.clerkvest.api.company;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.Company;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insertData.sql")})
@Transactional
public class PutCompanyResourceTest {

    private final static String REST_ENDPOINT_URL = REST_BASE_URL + "/company";

    @Test
    public void updateCompanyAsNoAdmin() {
        Company rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Company.class);
        rest.setName("TEST NAME");
        given().header("X-API-Key", "exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void updateCompanyAsAdmin() {
        String name = "TEST NAME";
        Company rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Company.class);
        rest.setName(name);
        Company updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(Company.class);
        assertThat(rest.getCompanyId()).isEqualTo(updated.getCompanyId());
        assertThat(name).isEqualTo(updated.getName());
    }

    @Test
    public void updateCompanyAsForeign() {
        Company rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Company.class);
        rest.setName("TEST NAME");
        given().header("X-API-Key", "exampleToken2").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void updateCompanyAsForeignAdmin() {
        Company rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Company.class);
        rest.setName("TEST NAME");
        given().header("X-API-Key", "exampleToken3").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void updateInvalidField_DOMAIN() {
        String name = "TEST NAME";
        String domain = "HELLO.com";
        Company rest = given().header("X-API-Key", "exampleToken1").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Company.class);
        rest.setName(name);
        rest.setDomain(domain);
        Company updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(Company.class);
        assertThat(rest.getCompanyId()).isEqualTo(updated.getCompanyId());
        assertThat(name).isEqualTo(updated.getName());
        assertThat(domain).isNotEqualTo(updated.getDomain());
    }

    @Test
    public void updateInvalidField_ID() {
        String name = "TEST NAME";
        Long id = 10L;
        Company rest = given().header("X-API-Key", "exampleToken0").get(REST_ENDPOINT_URL + "/0").then().statusCode(OK.value()).extract().as(Company.class);
        rest.setName(name);
        rest.setCompanyId(id);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());

    }


}
