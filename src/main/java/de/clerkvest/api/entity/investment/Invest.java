package de.clerkvest.api.entity.investment;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

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
@EntityListeners(InvestmentListener.class)
public class Invest extends RepresentationModel<Invest> implements IServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "invest_in_id", updatable = false)
    private Long investInId;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;

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
        Invest invest = (Invest) o;
        return investInId.equals(invest.investInId) &&
                project.equals(invest.project) &&
                employee.equals(invest.employee) &&
                investment.equals(invest.investment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), investInId, project, employee, investment);
    }
}
