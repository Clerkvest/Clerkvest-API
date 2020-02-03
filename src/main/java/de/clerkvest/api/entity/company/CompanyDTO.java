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
import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * CompanyRest
 */
@Builder
@AllArgsConstructor
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-26T13:56:04.987Z")
@JsonIgnoreProperties(value = {"companyId"}, ignoreUnknown = true)
public class CompanyDTO extends RepresentationModel<CompanyDTO> implements IServiceEntity {
    private Long companyId = null;

    private String name = null;

    private String domain = null;

    private Long imageId = null;

    private BigDecimal payAmount = null;

    private Integer payInterval = null;

    private Boolean inviteOnly = false;

    public CompanyDTO() {

    }
    public CompanyDTO(Company createdCompany) {
        companyId = createdCompany.getId();
        name = createdCompany.getName();
        domain = createdCompany.getDomain();
        Image companyImage = createdCompany.getImageId();
        if (companyImage != null) {
            imageId = companyImage.getImageId();
        }
        payAmount = createdCompany.getPayAmount();
        payInterval = createdCompany.getPayInterval();
        inviteOnly = createdCompany.isInviteOnly();
    }

    public CompanyDTO companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * Get company_id
     *
     * @return company_id
     **/
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
        this.imageId = image;
        return this;
    }

    /**
     * Get image
     *
     * @return image
     **/
    public Long getImage() {
        //return new BinaryNode(image).getValueAsText();
        return imageId;
    }

    public void setImage(Long image) {
        this.imageId = image;
    }

    public CompanyDTO payAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
        return this;
    }

    /**
     * Amount to Invest
     *
     * @return pay_amount
     **/
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public CompanyDTO payInterval(Integer payInterval) {
        this.payInterval = payInterval;
        return this;
    }

    /**
     * Interval to pay the Set Amount in Days
     *
     * @return pay_interval
     **/
    public Integer getPayInterval() {
        return payInterval;
    }

    public void setPayInterval(Integer payInterval) {
        this.payInterval = payInterval;
    }

    public CompanyDTO inviteOnly(Boolean inviteOnly) {
        this.inviteOnly = inviteOnly;
        return this;
    }

    /**
     * Get invite_only
     *
     * @return invite_only
     **/
    public Boolean getInviteOnly() {
        return inviteOnly;
    }


    public void setInviteOnly(Boolean inviteOnly) {
        this.inviteOnly = inviteOnly;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CompanyRest {\n");

        sb.append("    company_id: ").append(toIndentedString(companyId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
        sb.append("    image: ").append(toIndentedString(imageId)).append("\n");
        sb.append("    pay_amount: ").append(toIndentedString(payAmount)).append("\n");
        sb.append("    pay_interval: ").append(toIndentedString(payInterval)).append("\n");
        sb.append("    invite_only: ").append(toIndentedString(inviteOnly)).append("\n");
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
        return companyId.equals(rest.companyId) &&
                Objects.equals(name, rest.name) &&
                domain.equals(rest.domain) &&
                Objects.equals(payAmount, rest.payAmount) &&
                Objects.equals(payInterval, rest.payInterval) &&
                Objects.equals(inviteOnly, rest.inviteOnly);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(companyId, name, domain, payAmount, payInterval, inviteOnly);
        result = 31 * result + Objects.hashCode(imageId);
        return result;
    }

    @Override
    public Long getId() {
        return getCompanyId();
    }

    @Override
    public void setId(Long id) {
        companyId = id;
    }
}


