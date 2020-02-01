package de.clerkvest.api.company;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.company.CompanyDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class PutCompanyResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.COMPANY_UPDATE;

    @Test
    public void updateCompanyAsNoAdmin() {
        CompanyDTO rest = given().header("X-API-Key", "exampleToken0").get(HateoasLink.COMPANY_SINGLE + 0).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        rest.setName("TEST NAME");
        given().header("X-API-Key", "exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void updateCompanyAsAdmin() {
        String name = "TEST NAME";
        CompanyDTO rest = given().header("X-API-Key", "exampleToken0").get(HateoasLink.COMPANY_SINGLE + 0).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        rest.setName(name);
        CompanyDTO updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        assertThat(rest.getCompanyId()).isEqualTo(updated.getCompanyId());
        assertThat(name).isEqualTo(updated.getName());
    }

    @Test
    public void updateCompanyAsForeign() {
        CompanyDTO rest = given().header("X-API-Key", "exampleToken0").get(HateoasLink.COMPANY_SINGLE + 0).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        rest.setName("TEST NAME");
        given().header("X-API-Key", "exampleToken2").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void updateCompanyAsForeignAdmin() {
        CompanyDTO rest = given().header("X-API-Key", "exampleToken0").get(HateoasLink.COMPANY_SINGLE + 0).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        rest.setName("TEST NAME");
        given().header("X-API-Key", "exampleToken3").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }

    @Test
    public void updateInvalidField_DOMAIN() {
        String name = "TEST NAME";
        String domain = "HELLO.com";
        CompanyDTO rest = given().header("X-API-Key", "exampleToken1").get(HateoasLink.COMPANY_SINGLE + 0).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        rest.setName(name);
        rest.setDomain(domain);
        CompanyDTO updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        assertThat(rest.getCompanyId()).isEqualTo(updated.getCompanyId());
        assertThat(name).isEqualTo(updated.getName());
        assertThat(domain).isNotEqualTo(updated.getDomain());
    }

    @Test
    public void updateInvalidField_ID() {
        String name = "TEST NAME";
        Long id = 10L;
        CompanyDTO rest = given().header("X-API-Key", "exampleToken0").get(HateoasLink.COMPANY_SINGLE + 0).then().statusCode(OK.value()).extract().as(CompanyDTO.class);
        rest.setName(name);
        rest.setCompanyId(id);
        ValidatableResponse updated = given().header("X-API-Key", "exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());

    }


}
