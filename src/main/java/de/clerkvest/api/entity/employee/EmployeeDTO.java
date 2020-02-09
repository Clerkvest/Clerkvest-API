package de.clerkvest.api.entity.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@AllArgsConstructor
@JsonIgnoreProperties(value = {"token", "employeeId"}, ignoreUnknown = true)
public class EmployeeDTO extends RepresentationModel<EmployeeDTO> implements IServiceEntity {
    private Long employeeId = null;
    private Long companyId;

    private Boolean admin = false;
    private String email = null;
    private BigDecimal balance = null;
    private String token = null;
    private String firstname = null;
    private String lastname = null;
    private String nickname = null;

    public EmployeeDTO(Employee employee) {
        employeeId = employee.getId();
        companyId = employee.getCompany().getId();
        email = employee.getEmail();
        balance = employee.getBalance();
        //token = employee.getToken();
        firstname = employee.getFirstname();
        lastname = employee.getLastname();
        nickname = employee.getNickname();
        admin = employee.isAdmin();
    }

    public EmployeeDTO() {

    }

    @Override
    public Long getId() {
        return employeeId;
    }

    @Override
    public void setId(Long id) {
        this.employeeId = id;
    }

    public EmployeeDTO employeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    /**
     * Get employee_id
     *
     * @return employee_id
     **/
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }


    public EmployeeDTO company(long companyRest) {
        this.companyId = companyRest;
        return this;
    }

    /**
     * Get company
     *
     * @return company
     **/
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public EmployeeDTO email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     *
     * @return email
     **/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeDTO balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Get balance
     *
     * @return balance
     **/
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public EmployeeDTO token(String token) {
        this.token = token;
        return this;
    }

    /**
     * Get token
     *
     * @return token
     **/
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EmployeeDTO firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    /**
     * Get firstname
     *
     * @return firstname
     **/
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public EmployeeDTO lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    /**
     * Get lastname
     *
     * @return lastname
     **/
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public EmployeeDTO nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    /**
     * Get nickname
     *
     * @return nickname
     **/
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public EmployeeDTO isAdmin(Boolean isAdmin) {
        this.admin = isAdmin;
        return this;
    }

    /**
     * Get is_admin
     *
     * @return is_admin
     **/
    public Boolean isAdmin() {
        return admin;
    }

    public void setIsAdmin(Boolean is_admin) {
        this.admin = is_admin;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeDTO employeeRest = (EmployeeDTO) o;
        return Objects.equals(this.employeeId, employeeRest.employeeId) &&
                Objects.equals(this.companyId, employeeRest.companyId) &&
                Objects.equals(this.email, employeeRest.email) &&
                Objects.equals(this.balance, employeeRest.balance) &&
                Objects.equals(this.token, employeeRest.token) &&
                Objects.equals(this.firstname, employeeRest.firstname) &&
                Objects.equals(this.lastname, employeeRest.lastname) &&
                Objects.equals(this.nickname, employeeRest.nickname) &&
                Objects.equals(this.admin, employeeRest.admin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, companyId, email, balance, token, firstname, lastname, nickname, admin);
    }


    @Override
    public String toString() {

        return "class EmployeeRest {\n" +
                "    employee_id: " + toIndentedString(employeeId) + "\n" +
                "    company: " + toIndentedString(companyId) + "\n" +
                "    email: " + toIndentedString(email) + "\n" +
                "    balance: " + toIndentedString(balance) + "\n" +
                "    token: " + toIndentedString(token) + "\n" +
                "    firstname: " + toIndentedString(firstname) + "\n" +
                "    lastname: " + toIndentedString(lastname) + "\n" +
                "    nickname: " + toIndentedString(nickname) + "\n" +
                "    is_admin: " + toIndentedString(admin) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


}
