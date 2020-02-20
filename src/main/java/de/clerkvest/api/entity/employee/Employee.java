package de.clerkvest.api.entity.employee;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;

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
    @GeneratedValue
    @Column(name = "employee_id", updatable = false, nullable = false)
    private Long employeeId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Company.class)
    @JoinColumn(name = "company_id", updatable = false)
    private Company company;

    @NotNull
    @Email
    @Column(updatable = false, unique = true)
    @Size(max = 255)
    private String email;

    @NotNull
    @Builder.Default
    private BigDecimal balance = new BigDecimal(0);

    @Size(max = 255)
    private String token;

    @Column(name = "login_token")
    @Size(max = 255)
    private String loginToken;

    @NotNull
    @Builder.Default
    @Size(max = 255)
    private String firstname = "FirstName";

    @NotNull
    @Builder.Default
    @Size(max = 255)
    private String lastname = "LastName";

    @NotNull
    @Builder.Default
    @Size(max = 255)
    private String nickname = "NickName";

    private boolean isAdmin;

    @Override
    public Long getId() {
        return getEmployeeId();
    }

    @Override
    public void setId(Long id) {
        setEmployeeId(id);
    }

    public HashSet<SimpleGrantedAuthority> getAuthorities() {
        HashSet<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
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
