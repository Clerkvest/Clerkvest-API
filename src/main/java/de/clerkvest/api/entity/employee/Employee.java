package de.clerkvest.api.entity.employee;

import de.clerkvest.api.entity.audit.AuditListener;
import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.employee.settings.EmployeeSettings;
import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
@EntityListeners(AuditListener.class)
public class Employee implements IServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id", updatable = false, nullable = false)
    private Long employeeId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Company.class)
    @JoinColumn(name = "company_id", updatable = false)
    private Company company;

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Invest> investments = new ArrayList<>();


    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Project> projects = new ArrayList<>();

    @OneToOne(mappedBy = "employee")
    private EmployeeSettings setting;

    @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

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

    public void addInvestment(Invest invest) {
        investments.add(invest);
        invest.setEmployee(this);
    }

    public void removeInvestment(Invest invest) {
        investments.remove(invest);
        invest.setEmployee(null);
    }


    public void addProject(Project project) {
        projects.add(project);
        project.setEmployee(this);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.setEmployee(null);
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
        return "Employee{" +
                "employeeId=" + employeeId +
                ", company=" + company +
                ", setting=" + setting +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", token='" + token + '\'' +
                ", loginToken='" + loginToken + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return isAdmin == employee.isAdmin &&
                employeeId.equals(employee.employeeId) &&
                company.equals(employee.company) &&
                email.equals(employee.email) &&
                balance.equals(employee.balance) &&
                Objects.equals(token, employee.token) &&
                Objects.equals(loginToken, employee.loginToken) &&
                firstname.equals(employee.firstname) &&
                lastname.equals(employee.lastname) &&
                nickname.equals(employee.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, company, email, balance, token, loginToken, firstname, lastname, nickname, isAdmin);
    }
}
