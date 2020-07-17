package de.clerkvest.api.entity.company;

import de.clerkvest.api.entity.audit.AuditListener;
import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * api <p>
 * de.clerkvest.api.entity.employee.company <p>
 * Company.java <p>
 *
 * @author Michael K.
 * @version 1.0
 * @since 21 Dec 2019 17:18
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "company")
@Entity
@EntityListeners(AuditListener.class)
public class Company extends RepresentationModel<Company> implements IServiceEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id", updatable = false)
    private Long companyId;

    @NotEmpty
    @Size(max = 255)
    private String name;

    @Column(updatable = false)
    @NotBlank
    @Size(max = 255)
    private String domain;

    @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @NotNull
    @Min(value = 0)
    private BigDecimal payAmount;

    @Min(value = 1)
    private int payInterval;

    private boolean inviteOnly;

    @Override
    public Long getId() {
        return getCompanyId();
    }

    @Override
    public void setId(Long id) {
        setCompanyId(id);
    }

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Company company = (Company) o;
        return payInterval == company.payInterval &&
                inviteOnly == company.inviteOnly &&
                companyId.equals(company.companyId) &&
                name.equals(company.name) &&
                domain.equals(company.domain) &&
                Objects.equals(image, company.image) &&
                payAmount.equals(company.payAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyId, name, domain, image, payAmount, payInterval, inviteOnly);
    }
}
