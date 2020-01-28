package de.clerkvest.api.entity.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.clerkvest.api.implement.service.IServiceEntity;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties({"token","employee_id"})
public class EmployeeDTO extends RepresentationModel<EmployeeDTO> implements IServiceEntity {
    @Override
    public Long getId() {
        return employee_id;
    }

    @Override
    public void setId(Long id) {
        this.employee_id = id;
    }

    private Long employee_id = null;

    private long company;

    private String email = null;

    private BigDecimal balance = null;

    private String token = null;

    private String firstname = null;

    private String lastname = null;

    private String nickname = null;

    private Boolean isAdmin = false;

    public EmployeeDTO() {

    }

    public EmployeeDTO(Employee employee) {
        employee_id = employee.getId();
        company = employee.getCompany().getId();
        email = employee.getEmail();
        balance = employee.getBalance();
        //token = employee.getToken();
        firstname = employee.getFirstname();
        lastname = employee.getLastname();
        nickname = employee.getNickname();
        isAdmin = employee.is_admin();
    }

    public EmployeeDTO employeeId(Long employeeId) {
        this.employee_id = employeeId;
        return this;
    }

    /**
     * Get employee_id
     *
     * @return employee_id
     **/
    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }



    public EmployeeDTO company(long companyRest) {
        this.company = companyRest;
        return this;
    }

    /**
     * Get company
     *
     * @return company
     **/
    public long getCompany() {
        return company;
    }

    public void setCompany(long company) {
        this.company = company;
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
        this.isAdmin = isAdmin;
        return this;
    }

    /**
     * Get is_admin
     *
     * @return is_admin
     **/
    public Boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.isAdmin = is_admin;
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
        return Objects.equals(this.employee_id, employeeRest.employee_id) &&
                Objects.equals(this.company, employeeRest.company) &&
                Objects.equals(this.email, employeeRest.email) &&
                Objects.equals(this.balance, employeeRest.balance) &&
                Objects.equals(this.token, employeeRest.token) &&
                Objects.equals(this.firstname, employeeRest.firstname) &&
                Objects.equals(this.lastname, employeeRest.lastname) &&
                Objects.equals(this.nickname, employeeRest.nickname) &&
                Objects.equals(this.isAdmin, employeeRest.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee_id, company, email, balance, token, firstname, lastname, nickname, isAdmin);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EmployeeRest {\n");

        sb.append("    employee_id: ").append(toIndentedString(employee_id)).append("\n");
        sb.append("    company: ").append(toIndentedString(company)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
        sb.append("    token: ").append(toIndentedString(token)).append("\n");
        sb.append("    firstname: ").append(toIndentedString(firstname)).append("\n");
        sb.append("    lastname: ").append(toIndentedString(lastname)).append("\n");
        sb.append("    nickname: ").append(toIndentedString(nickname)).append("\n");
        sb.append("    is_admin: ").append(toIndentedString(isAdmin)).append("\n");
        sb.append("}");
        return sb.toString();
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
