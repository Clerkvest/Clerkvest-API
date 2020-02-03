package de.clerkvest.api.employee;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class PutEmployeeResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.EMPLOYEE_UPDATE;

    @Test
    public void updateEmployeeAsSelf() {
        String name = "TEST NAME";
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 0).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        rest.setNickname(name);
        EmployeeDTO updated = given().header("Authorization", "Bearer exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        assertThat(rest.getEmployeeId()).isEqualTo(updated.getEmployeeId());
        assertThat(name).isEqualTo(updated.getNickname());
    }

    @Test
    public void updateEmployeeAsAdmin() {
        String name = "TEST NAME";
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 0).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        rest.setNickname(name);
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken1").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());

    }

    @Test
    public void updateEmployeeAsNonAdmin() {
        String name = "TEST NAME";
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken1").get(HateoasLink.EMPLOYEE_SINGLE + 1).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        rest.setNickname(name);
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken0").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

    @Test
    public void updateEmployeeAsForeignAdmin() {
        String name = "TEST NAME";
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 0).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        rest.setNickname(name);
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken3").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());

    }

    @Test
    public void updateEmployeeAsForeignEmployee() {
        String name = "TEST NAME";
        EmployeeDTO rest = given().header("Authorization", "Bearer exampleToken0").get(HateoasLink.EMPLOYEE_SINGLE + 0).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        rest.setNickname(name);
        ValidatableResponse updated = given().header("Authorization", "Bearer exampleToken2").body(rest).contentType(ContentType.JSON).put(REST_ENDPOINT_URL).then().statusCode(FORBIDDEN.value());
    }

}
