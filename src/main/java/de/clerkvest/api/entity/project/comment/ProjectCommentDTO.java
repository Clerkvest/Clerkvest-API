/*
 * Team Investment Tool
 * Team Investment Tool
 *
 * OpenAPI spec version: 1.0.0
 * Contact: admin@example.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package de.clerkvest.api.entity.project.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Objects;

/**
 * ProjectCommentRest
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-26T13:56:04.987Z")
@JsonIgnoreProperties({"project_comment_id", "_links"})
public class ProjectCommentDTO extends RepresentationModel<ProjectCommentDTO> implements IServiceEntity {
    private Long projectCommentId = null;

    private Long employeeId;

    private Long projectId;

    private String title = null;

    private String text = null;

    private Date date = null;

    public ProjectCommentDTO(ProjectComment item) {
        projectCommentId = item.getId();
        employeeId = item.getEmployeeId().getId();
        projectId = item.getProjectId().getId();
        title = item.getTitle();
        text = item.getText();
        date = item.getDate();
    }

    public ProjectCommentDTO projectCommentId(Long projectCommentId) {
        this.projectCommentId = projectCommentId;
        return this;
    }

    /**
     * Get project_comment_id
     *
     * @return project_comment_id
     **/
    public Long getProjectCommentId() {
        return projectCommentId;
    }

    public void setProjectCommentId(Long projectCommentId) {
        this.projectCommentId = projectCommentId;
    }

    public ProjectCommentDTO employeeId(long employeeRestId) {
        this.employeeId = employeeRestId;
        return this;
    }

    /**
     * Get employee_id
     *
     * @return employee_id
     **/
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public ProjectCommentDTO projectId(long projectRestId) {
        this.projectId = projectRestId;
        return this;
    }

    /**
     * Get project_id
     *
     * @return project_id
     **/
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public ProjectCommentDTO title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get title
     *
     * @return title
     **/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProjectCommentDTO text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Get text
     *
     * @return text
     **/
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ProjectCommentDTO date(Date date) {
        this.date = date;
        return this;
    }

    /**
     * Get date
     *
     * @return date
     **/
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectCommentDTO projectCommentDTO = (ProjectCommentDTO) o;
        return Objects.equals(this.projectCommentId, projectCommentDTO.projectCommentId) &&
                Objects.equals(this.employeeId, projectCommentDTO.employeeId) &&
                Objects.equals(this.projectId, projectCommentDTO.projectId) &&
                Objects.equals(this.title, projectCommentDTO.title) &&
                Objects.equals(this.text, projectCommentDTO.text) &&
                Objects.equals(this.date, projectCommentDTO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCommentId, employeeId, projectId, title, text, date);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProjectCommentRest {\n");

        sb.append("    project_comment_id: ").append(toIndentedString(projectCommentId)).append("\n");
        sb.append("    employee_id: ").append(toIndentedString(employeeId)).append("\n");
        sb.append("    project_id: ").append(toIndentedString(projectId)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    text: ").append(toIndentedString(text)).append("\n");
        sb.append("    date: ").append(toIndentedString(date)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    @Override
    public Long getId() {
        return projectCommentId;
    }

    @Override
    public void setId(Long id) {
        projectCommentId = id;
    }
}

