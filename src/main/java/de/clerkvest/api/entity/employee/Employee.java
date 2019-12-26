package de.clerkvest.api.entity.employee;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "employee")
@Entity
public class Employee extends RepresentationModel<Employee> implements IServiceEntity {
    @Id
    @SequenceGenerator(name = "employee_gen", sequenceName = "employee_employee_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "employee_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_id", updatable = false)
    private Long employeeId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Company.class)
    @JoinColumn(name = "company_id", updatable = false)
    private Company company;

    @NotNull
    @Email
    @Column(updatable = false)
    private String email;

    @NotNull
    @Builder.Default
    private BigDecimal balance = new BigDecimal(0);

    private String token;

    @NotNull
    @Builder.Default
    private String firstname = "FirstName";

    @NotNull
    @Builder.Default
    private String lastname = "LastName";

    @NotNull
    @Builder.Default
    private String nickname = "NickName";

    private boolean is_admin;

    @Override
    public Long getId() {
        return getEmployeeId();
    }

    @Override
    public void setId(Long id) {
        setEmployeeId(id);
    }
}
