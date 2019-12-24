package de.clerkvest.api.entity.employee.comment;

import de.clerkvest.api.entity.employee.Employee;
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
 * de.clerkvest.api.entity.employee.comment <p>
 * EmployeeComment.java <p>
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
@Table(name = "employee_comment")
@Entity
public class EmployeeComment extends RepresentationModel<EmployeeComment> implements IServiceEntity {

    @Id
    @SequenceGenerator(name = "employee_comment_gen", sequenceName = "employee_comment_employee_comment_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "employee_comment_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_comment_id", updatable = false)
    private Long employeeCommentId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Employee.class)
    @JoinColumn(name = "employee_id")
    @NotNull
    private Employee employeeId;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Employee.class)
    @JoinColumn(name = "commenter_Id")
    @NotNull
    private Employee commenterId;

    @NotNull
    private String comment;

    @NotNull
    private Timestamp date;

    @Override
    public Long getId () {
        return employeeCommentId;
    }

    @Override
    public void setId (Long id) {
        employeeCommentId = id;
    }
}
