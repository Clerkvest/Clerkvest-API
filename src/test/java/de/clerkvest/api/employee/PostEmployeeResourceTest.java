package de.clerkvest.api.employee;

import de.clerkvest.api.Application;
import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

@Transactional
public class PostEmployeeResourceTest {

    private final static String REST_ENDPOINT_URL = HateoasLink.EMPLOYEE_CREATE;


    @Test
    public void addEmployee_ToOwnCompany_Admin() {
        EmployeeDTO employee0 = EmployeeDTO.builder().employeeId(0L).company(0L).email("user3@clerkvest.de").balance(BigDecimal.valueOf(10)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").admin(false).build();
        EmployeeDTO rest = given().header("X-API-Key", "exampleToken1").contentType(ContentType.JSON).body(employee0).post(REST_ENDPOINT_URL).then().statusCode(OK.value()).extract().as(EmployeeDTO.class);
        assertThat(employee0).isEqualTo(rest);
    }

    @Test
    public void addEmployee_ToOwnCompany_NonAdmin() {
        EmployeeDTO employee0 = EmployeeDTO.builder().employeeId(0L).company(0L).email("user3@clerkvest.de").balance(BigDecimal.valueOf(10)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").admin(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken0").contentType(ContentType.JSON).body(employee0).post(REST_ENDPOINT_URL).then().statusCode(UNAUTHORIZED.value());
    }

    @Test
    public void addEmployee_ToCompany_ForeignAdmin() {
        EmployeeDTO employee0 = EmployeeDTO.builder().employeeId(0L).company(0L).email("user3@clerkvest.de").balance(BigDecimal.valueOf(10)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").admin(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken3").contentType(ContentType.JSON).body(employee0).post(REST_ENDPOINT_URL).then().statusCode(UNAUTHORIZED.value());
    }

    @Disabled("Disabled")
    @Test
    public void addEmployee_ToOwnCompany_DuplicateMail() {
        //TODO Implement Mail Check
        EmployeeDTO employee0 = EmployeeDTO.builder().employeeId(0L).company(0L).email("user1@clerkvest.de").balance(BigDecimal.valueOf(10)).token(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").admin(false).build();
        ValidatableResponse rest = given().header("X-API-Key", "exampleToken3").contentType(ContentType.JSON).body(employee0).post(REST_ENDPOINT_URL).then().statusCode(BAD_REQUEST.value());
    }
}
