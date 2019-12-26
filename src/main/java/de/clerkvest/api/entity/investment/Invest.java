package de.clerkvest.api.entity.investment;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * api <p>
 * de.clerkvest.api.entity.investment <p>
 * Invest.java <p>
 *
 * @author Michael K.
 * @version 1.0
 * @since 21 Dec 2019 19:12
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "invest_in")
@Entity
public class Invest extends RepresentationModel<Invest> implements IServiceEntity {

    @Id
    @SequenceGenerator(name = "invest_gen", sequenceName = "invest_invest_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "invest_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "invest_in_id", updatable = false)
    private Long investInId;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    private Project projectId;

    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    @NotNull
    private BigDecimal investment;

    @Override
    public Long getId() {
        return getInvestInId();
    }

    @Override
    public void setId(Long id) {
        setInvestInId(id);
    }
}
