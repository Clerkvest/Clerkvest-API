package de.clerkvest.api.entity.employee.settings;

import de.clerkvest.api.entity.audit.AuditListener;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "employee_setting")
@Entity
@EntityListeners(AuditListener.class)
public class EmployeeSetting implements IServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_setting_id", updatable = false, nullable = false)
    private Long employeeSettingId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @NotNull
    @Builder.Default
    private boolean notificationProjectFunded = true;

    @NotNull
    @Builder.Default
    private boolean notificationProjectComment = true;

    @NotNull
    @Builder.Default
    private boolean notificationProjectAvailable = true;

    @NotNull
    @Builder.Default
    private boolean notificationProjectNearlyFunded = true;

    @Override
    public Long getId() {
        return getEmployeeSettingId();
    }

    @Override
    public void setId(Long id) {
        setEmployeeSettingId(id);
    }
}
