package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

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
public class ProjectComment extends RepresentationModel<ProjectComment> implements IServiceEntity {
    @Id
    @SequenceGenerator(name = "project_comment_gen", sequenceName = "project_comment_project_comment_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "project_comment_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "project_comment_id", updatable = false)
    private Long projectCommentId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Employee.class)
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    private Project projectId;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private Timestamp date;

    @Override
    public Long getId () {
        return projectCommentId;
    }

    @Override
    public void setId (Long id) {
        projectCommentId = id;
    }
}
