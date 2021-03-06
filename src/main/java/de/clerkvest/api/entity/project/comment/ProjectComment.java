package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.entity.audit.AuditListener;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * api <p>
 * de.clerkvest.api.entity.project.comment <p>
 * ProjectComment.java <p>
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
@Table(name = "project_comment")
@Entity
@EntityListeners(AuditListener.class)
public class ProjectComment extends RepresentationModel<ProjectComment> implements IServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_comment_id", nullable = false, updatable = false)
    private Long projectCommentId;

    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 2083)
    private String text;

    @CreatedDate
    @NotNull
    private Timestamp date;

    @Override
    public Long getId() {
        return projectCommentId;
    }

    @Override
    public void setId(Long id) {
        projectCommentId = id;
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
        ProjectComment that = (ProjectComment) o;
        return projectCommentId.equals(that.projectCommentId) &&
                employee.equals(that.employee) &&
                project.equals(that.project) &&
                title.equals(that.title) &&
                text.equals(that.text) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), projectCommentId, employee, project, title, text, date);
    }
}
