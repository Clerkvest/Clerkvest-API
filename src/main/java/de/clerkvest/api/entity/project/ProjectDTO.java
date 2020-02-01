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


package de.clerkvest.api.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ProjectRest
 */
@Builder
@AllArgsConstructor
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-26T13:56:04.987Z")
@JsonIgnoreProperties({"project_id", "_links"})
public class ProjectDTO extends RepresentationModel<ProjectDTO> implements IServiceEntity {
    private Long projectId = null;

    private Long employeeId;

    private Long companyId;

    private String link = null;

    private String title = null;

    private String description = null;

    private BigDecimal goal = null;

    private BigDecimal investedIn = null;

    private Boolean reached = null;

    private Long imageId = null;

    private LocalDateTime created_at = null;

    private LocalDateTime funded_at = null;

    public ProjectDTO() {

    }

    public ProjectDTO(Project projectId) {
        this.projectId = projectId.getId();
        employeeId = projectId.getEmployeeId().getId();
        companyId = projectId.getCompanyId().getId();
        title = projectId.getTitle();
        link = projectId.getLink();
        description = projectId.getDescription();
        goal = projectId.getGoal();
        investedIn = projectId.getInvestedIn();
        reached = projectId.isReached();

        Image projectImage = projectId.getImageId();
        if (projectImage != null) {
            imageId = projectImage.getImageId();
        }
        created_at = projectId.getCreatedAt();
        funded_at = projectId.getFundedAt();
    }

    public ProjectDTO projectId(Long projectId) {
        this.projectId = projectId;
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

    /**
     * Get company_id
     *
     * @return company_id
     **/
    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public ProjectDTO companyId(long companyRestId) {
        this.companyId = companyRestId;
        return this;
    }

    /**
     * Get employee_id
     *
     * @return employee_id
     **/
    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public ProjectDTO employeeId(long employeeRestId) {
        this.employeeId = employeeRestId;
        return this;
    }

    public ProjectDTO link(String link) {
        this.link = link;
        return this;
    }

    /**
     * Get link
     *
     * @return link
     **/
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ProjectDTO title(String title) {
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

    public ProjectDTO description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectDTO goal(BigDecimal goal) {
        this.goal = goal;
        return this;
    }

    /**
     * Get goal
     *
     * @return goal
     **/
    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public ProjectDTO investedIn(BigDecimal investedIn) {
        this.investedIn = investedIn;
        return this;
    }

    /**
     * Get invested_in
     *
     * @return invested_in
     **/
    public BigDecimal getInvestedIn() {
        return investedIn;
    }

    public void setInvestedIn(BigDecimal investedIn) {
        this.investedIn = investedIn;
    }

    public ProjectDTO reached(Boolean reached) {
        this.reached = reached;
        return this;
    }

    /**
     * Get reached
     *
     * @return reached
     **/
    public Boolean isReached() {
        return reached;
    }

    public void setReached(Boolean reached) {
        this.reached = reached;
    }

    public ProjectDTO image(Long image) {
        this.imageId = image;
        return this;
    }

    /**
     * Get image
     *
     * @return image
     **/
    public Long getImage() {
        return imageId;
    }

    public void setImage(Long image) {
        this.imageId = image;
    }

    public ProjectDTO createdAt(LocalDateTime createdAt) {
        this.created_at = createdAt;
        return this;
    }

    /**
     * Get created_at
     *
     * @return created_at
     **/
    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public ProjectDTO fundedAt(LocalDateTime fundedAt) {
        this.funded_at = fundedAt;
        return this;
    }

    /**
     * Get funded_at
     *
     * @return funded_at
     **/
    public LocalDateTime getFunded_at() {
        return funded_at;
    }

    public void setFunded_at(LocalDateTime funded_at) {
        this.funded_at = funded_at;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectDTO projectDTO = (ProjectDTO) o;
        return Objects.equals(this.projectId, projectDTO.projectId) &&
                Objects.equals(this.employeeId, projectDTO.employeeId) &&
                Objects.equals(this.link, projectDTO.link) &&
                Objects.equals(this.title, projectDTO.title) &&
                Objects.equals(this.description, projectDTO.description) &&
                Objects.equals(this.goal, projectDTO.goal) &&
                Objects.equals(this.investedIn, projectDTO.investedIn) &&
                Objects.equals(this.reached, projectDTO.reached) &&
                Objects.equals(this.created_at, projectDTO.created_at) &&
                Objects.equals(this.funded_at, projectDTO.funded_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, employeeId, link, title, description, goal, investedIn, reached, imageId, created_at, funded_at);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProjectRest {\n");

        sb.append("    project_id: ").append(toIndentedString(projectId)).append("\n");
        sb.append("    employee_id: ").append(toIndentedString(employeeId)).append("\n");
        sb.append("    link: ").append(toIndentedString(link)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    goal: ").append(toIndentedString(goal)).append("\n");
        sb.append("    invested_in: ").append(toIndentedString(investedIn)).append("\n");
        sb.append("    reached: ").append(toIndentedString(reached)).append("\n");
        sb.append("    image: ").append(toIndentedString(imageId)).append("\n");
        sb.append("    created_at: ").append(toIndentedString(created_at)).append("\n");
        sb.append("    funded_at: ").append(toIndentedString(funded_at)).append("\n");
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
        return projectId;
    }

    @Override
    public void setId(Long id) {
        projectId = id;
    }
}

