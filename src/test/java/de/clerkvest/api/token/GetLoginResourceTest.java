package de.clerkvest.api.token;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static de.clerkvest.api.config.TestConfig.REST_BASE_URL;
import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
@Disabled
public class GetLoginResourceTest {


    private final static String REST_ENDPOINT_URL = /*HateoasLink.EMPLOYEE_LOGIN*/REST_BASE_URL + "/login/";

    @Test
    public void getEmployee_0() {
        EmployeeDTO rest = get(REST_ENDPOINT_URL + "exampleToken0").then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        assertThat(rest.getToken().isEmpty()).isFalse();
    }

    @Test
    public void getEmployee_1() {
        EmployeeDTO rest = get(REST_ENDPOINT_URL + "exampleToken1").then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        assertThat(rest.getToken().isEmpty()).isFalse();
    }

    @Test
    public void getEmployee_2() {
        EmployeeDTO rest = get(REST_ENDPOINT_URL + "exampleToken2").then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        assertThat(rest.getToken().isEmpty()).isFalse();
    }

    @Test
    public void getEmployee_3() {
        EmployeeDTO rest = get(REST_ENDPOINT_URL + "exampleToken3").then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        assertThat(rest.getToken().isEmpty()).isFalse();
    }
}
