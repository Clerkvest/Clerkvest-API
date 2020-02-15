package de.clerkvest.api.entity.company;

import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

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
public class Company extends RepresentationModel<Company> implements IServiceEntity {

    @Id
    @SequenceGenerator(name = "company_gen", sequenceName = "company_company_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "company_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "company_id", updatable = false)
    private Long companyId;

    @NotEmpty
    @Size(max = 255)
    private String name;

    @Column(updatable = false)
    @NotBlank
    @Size(max = 255)
    private String domain;

    @OneToOne(targetEntity = Image.class)
    @JoinColumn(name = "image_id")
    private Image image;

    @NotNull
    @Min(value = 0)
    private BigDecimal payAmount;

    @Min(value = 1)
    private int payInterval;

    private boolean inviteOnly;

    @Override
    public Long getId () {
        return getCompanyId();
    }

    @Override
    public void setId (Long id) {
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
}
