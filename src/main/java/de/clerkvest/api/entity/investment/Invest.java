package de.clerkvest.api.entity.investment;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "invest_in")
@Entity
public class Invest extends RepresentationModel {

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

}
