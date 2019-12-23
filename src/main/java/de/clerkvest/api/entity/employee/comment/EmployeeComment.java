package de.clerkvest.api.entity.employee.comment;

import lombok.*;
import de.clerkvest.api.entity.employee.Employee;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "employee_comment")
@Entity
public class EmployeeComment extends RepresentationModel {

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

}
