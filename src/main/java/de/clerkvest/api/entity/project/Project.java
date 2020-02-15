package de.clerkvest.api.entity.project;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * api <p>
 * de.clerkvest.api.entity.project <p>
 * Project.java <p>
 *
 * @author Michael K.
 * @version 1.0
 * @since 21 Dec 2019 19:13
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "project")
@Entity
public class Project extends RepresentationModel<Project> implements IServiceEntity {

    @Id
    @SequenceGenerator(name = "project_gen", sequenceName = "project_project_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "project_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "project_id", updatable = false)
    private Long projectId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Employee.class)
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Company.class)
    @JoinColumn(name = "company_id", nullable = false, updatable = false)
    private Company company;

    @NotNull
    @Size(max = 2083)
    private String link;

    @NotNull
    @Column(name = "name")
    @Size(max = 255)
    private String title;

    private String description;

    @NotNull
    private BigDecimal goal;

    @NotNull
    private BigDecimal investedIn;

    private boolean reached;

    @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime fundedAt;

    @Override
    public Long getId() {
        return getProjectId();
    }

    @Override
    public void setId(Long id) {
        setProjectId(id);
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
