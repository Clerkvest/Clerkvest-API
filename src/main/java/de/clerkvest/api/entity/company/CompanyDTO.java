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


package de.clerkvest.api.entity.company;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.implement.service.IServiceEntity;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * CompanyRest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-26T13:56:04.987Z")
@JsonIgnoreProperties({"company_id"})
public class CompanyDTO extends RepresentationModel<CompanyDTO> implements IServiceEntity {
    private Long company_id = null;

    private String name = null;

    private String domain = null;

    private Long image_id = null;

    private BigDecimal pay_amount = null;

    private Integer pay_interval = null;

    private Boolean invite_only = false;

    public CompanyDTO() {

    }
    public CompanyDTO(Company createdCompany) {
        company_id = createdCompany.getId();
        name = createdCompany.getName();
        domain = createdCompany.getDomain();
        Image companyImage = createdCompany.getImageId();
        if (companyImage != null) {
            image_id = companyImage.getImageId();
        }
        pay_amount = createdCompany.getPayAmount();
        pay_interval = createdCompany.getPayInterval();
        invite_only = createdCompany.isInviteOnly();
    }

    public CompanyDTO companyId(Long companyId) {
        this.company_id = companyId;
        return this;
    }

    /**
     * Get company_id
     *
     * @return company_id
     **/
    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public CompanyDTO name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     *
     * @return name
     **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyDTO domain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * Get domain
     *
     * @return domain
     **/
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public CompanyDTO image_id(Long image) {
        this.image_id = image;
        return this;
    }

    /**
     * Get image
     *
     * @return image
     **/
    public Long getImage() {
        //return new BinaryNode(image).getValueAsText();
        return image_id;
    }

    public void setImage(Long image) {
        this.image_id = image;
    }

    public CompanyDTO payAmount(BigDecimal payAmount) {
        this.pay_amount = payAmount;
        return this;
    }

    /**
     * Amount to Invest
     *
     * @return pay_amount
     **/
    public BigDecimal getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(BigDecimal pay_amount) {
        this.pay_amount = pay_amount;
    }
    public CompanyDTO payInterval(Integer payInterval) {
        this.pay_interval = payInterval;
        return this;
    }

    /**
     * Interval to pay the Set Amount in Days
     *
     * @return pay_interval
     **/
    public Integer getPay_interval() {
        return pay_interval;
    }

    public void setPay_interval(Integer pay_interval) {
        this.pay_interval = pay_interval;
    }

    public CompanyDTO inviteOnly(Boolean inviteOnly) {
        this.invite_only = inviteOnly;
        return this;
    }

    /**
     * Get invite_only
     *
     * @return invite_only
     **/
    public Boolean getInvite_only() {
        return invite_only;
    }


    public void setInvite_only(Boolean invite_only) {
        this.invite_only = invite_only;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CompanyRest {\n");

        sb.append("    company_id: ").append(toIndentedString(company_id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
        sb.append("    image: ").append(toIndentedString(image_id)).append("\n");
        sb.append("    pay_amount: ").append(toIndentedString(pay_amount)).append("\n");
        sb.append("    pay_interval: ").append(toIndentedString(pay_interval)).append("\n");
        sb.append("    invite_only: ").append(toIndentedString(invite_only)).append("\n");
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDTO rest = (CompanyDTO) o;
        return company_id.equals(rest.company_id) &&
                Objects.equals(name, rest.name) &&
                domain.equals(rest.domain) &&
                Objects.equals(pay_amount, rest.pay_amount) &&
                Objects.equals(pay_interval, rest.pay_interval) &&
                Objects.equals(invite_only, rest.invite_only);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(company_id, name, domain, pay_amount, pay_interval, invite_only);
        result = 31 * result + Objects.hashCode(image_id);
        return result;
    }

    @Override
    public Long getId() {
        return getCompany_id();
    }

    @Override
    public void setId(Long id) {
        company_id = id;
    }
}


