package de.clerkvest.api.entity.project;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id", updatable = false)
    private Long projectId;

    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id", nullable = false, updatable = false)
    private Company company;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Invest> investments = new ArrayList<>();


    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProjectComment> comments = new ArrayList<>();

    @NotNull
    @Size(max = 2083)
    private String link;

    @NotNull
    @Column(name = "name")
    @Size(max = 255)
    private String title;

    @Column(length = 5000)
    private String description;

    @NotNull
    private BigDecimal goal;

    @NotNull
    private BigDecimal investedIn;

    private boolean reached;

    @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    @CreatedDate
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

    public void addInvestment(Invest invest) {
        investments.add(invest);
        invest.setProject(this);
    }

    public void removeInvestment(Invest invest) {
        investments.remove(invest);
        invest.setProject(null);
    }


    public void addProjectComment(ProjectComment comment) {
        comments.add(comment);
        comment.setProject(this);
    }

    public void removeProjectComment(ProjectComment comment) {
        comments.remove(comment);
        comment.setProject(null);
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
        Project project = (Project) o;
        return reached == project.reached &&
                projectId.equals(project.projectId) &&
                employee.equals(project.employee) &&
                company.equals(project.company) &&
                link.equals(project.link) &&
                title.equals(project.title) &&
                description.equals(project.description) &&
                goal.equals(project.goal) &&
                investedIn.equals(project.investedIn) &&
                Objects.equals(image, project.image) &&
                Objects.equals(createdAt, project.createdAt) &&
                Objects.equals(fundedAt, project.fundedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), projectId, employee, company, link, title, description, goal, investedIn, reached, image, createdAt, fundedAt);
    }
}
