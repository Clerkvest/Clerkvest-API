package de.clerkvest.api.entity.employee;

import de.clerkvest.api.entity.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Employee extends RepresentationModel {
    @Id
    @SequenceGenerator(name = "employee_gen", sequenceName = "employee_employee_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "employee_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_id", updatable = false)
    private Long employeeId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Company.class)
    @JoinColumn(name = "company_id")
    private Company company;

    @NotNull
    @Email
    private String email;

    @NotNull
    private BigDecimal balance = new BigDecimal(0);

    private String token;

    @NotNull
    private String firstname = "FirstName";

    @NotNull
    private String lastname = "LastName";

    @NotNull
    private String nickname = "NickName";

    private boolean is_admin;

}
