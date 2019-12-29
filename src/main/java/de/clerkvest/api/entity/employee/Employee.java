package de.clerkvest.api.entity.employee;

import com.fasterxml.jackson.annotation.JsonValue;
import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * api <p>
 * de.clerkvest.api.entity.employee <p>
 * Employee.java <p>
 *
 * @author Michael K.
 * @version 1.0
 * @since 21 Dec 2019 19:10
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "employee")
@Entity
public class Employee implements IServiceEntity {
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