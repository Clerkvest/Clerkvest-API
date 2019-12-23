package de.clerkvest.api.entity.company;

import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    private String name;

    @NotNull
    private String domain;

    @OneToOne(targetEntity = Image.class)
    @JoinColumn(name = "image_id")
    private Image imageId;

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
}
