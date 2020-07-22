/*
 * Team Investment Tool
 * Team Investment Tool
 *
 * OpenAPI spec version: 1.0.0
 * Contact: admin@example.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package de.clerkvest.api.entity.investment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * InvestInRest
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-26T13:56:04.987Z")
@JsonIgnoreProperties(value = {"investId"}, ignoreUnknown = true)
public class InvestDTO extends RepresentationModel<InvestDTO> implements IServiceEntity {
    private Long investId = null;

    private Long projectId;

    private Long employeeId;

    private BigDecimal investment = null;

    public InvestDTO investInId(Long investInId) {
        this.investId = investInId;
        return this;
    }

    /**
     * Get invest_in_id
     *
     * @return invest_in_id
     **/
    public Long getInvest_in_id() {
        return investId;
    }

    public void setInvest_in_id(Long invest_in_id) {
        this.investId = invest_in_id;
    }

    public InvestDTO projectId(long projectRestId) {
        this.projectId = projectRestId;
        return this;
    }

    /**
     * Get project_id
     *
     * @return project_id
     **/
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public InvestDTO employeeId(Long employeeRestId) {
        this.employeeId = employeeRestId;
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

    public InvestDTO investment(BigDecimal investment) {
        this.investment = investment;
        return this;
    }

    /**
     * Get investment
     *
     * @return investment
     **/
    public BigDecimal getInvestment() {
        return investment;
    }

    public void setInvestment(BigDecimal investment) {
        this.investment = investment;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvestDTO investDTO = (InvestDTO) o;
        return Objects.equals(this.investId, investDTO.investId) &&
                Objects.equals(this.projectId, investDTO.projectId) &&
                Objects.equals(this.employeeId, investDTO.employeeId) &&
                Objects.equals(this.investment, investDTO.investment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(investId, projectId, employeeId, investment);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InvestInRest {\n");

        sb.append("    invest_in_id: ").append(toIndentedString(investId)).append("\n");
        sb.append("    project_id: ").append(toIndentedString(projectId)).append("\n");
        sb.append("    employee_id: ").append(toIndentedString(employeeId)).append("\n");
        sb.append("    investment: ").append(toIndentedString(investment)).append("\n");
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

    @Override
    public Long getId() {
        return investId;
    }

    @Override
    public void setId(Long id) {
        investId = id;
    }
}

