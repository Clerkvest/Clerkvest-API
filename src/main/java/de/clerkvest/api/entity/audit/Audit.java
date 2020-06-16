package de.clerkvest.api.entity.audit;

import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "audit")
@Entity
public class Audit extends RepresentationModel<Audit> implements IServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long auditId;

    @NotNull
    @Size(max = 255)
    private String audit;

    @NotNull
    @Size(max = 255)
    private String clazz;

    @NotNull
    private Long entityId;

    private Long employeeId;

    private Long companyId;


    @Override
    public Long getId() {
        return getAuditId();
    }

    @Override
    public void setId(Long id) {
        setAuditId(id);
    }
}
