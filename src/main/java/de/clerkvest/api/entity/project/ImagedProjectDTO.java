package de.clerkvest.api.entity.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2019-04-26T13:56:04.987Z")
@JsonIgnoreProperties(value = {"projectId"}, ignoreUnknown = true)
public class ImagedProjectDTO extends RepresentationModel<ImagedProjectDTO> implements IServiceEntity {
    private Long projectId = null;

    private Long employeeId;

    private Long companyId;

    private String link = null;

    private String title = null;

    private String description = null;

    private BigDecimal goal = null;

    private BigDecimal investedIn = null;

    private Boolean reached = null;

    private String image = null;

    private LocalDateTime createdAt = null;

    private LocalDateTime fundedAt = null;

    public ImagedProjectDTO projectId(Long projectId) {
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
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public ImagedProjectDTO companyId(long companyRestId) {
        this.companyId = companyRestId;
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

    public ImagedProjectDTO employeeId(long employeeRestId) {
        this.employeeId = employeeRestId;
        return this;
    }

    public ImagedProjectDTO link(String link) {
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

    public ImagedProjectDTO title(String title) {
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

    public ImagedProjectDTO description(String description) {
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

    public ImagedProjectDTO goal(BigDecimal goal) {
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

    public ImagedProjectDTO investedIn(BigDecimal investedIn) {
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

    public ImagedProjectDTO reached(Boolean reached) {
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

    public ImagedProjectDTO image(String image) {
        this.image = image;
        return this;
    }

    /**
     * Get image
     *
     * @return image
     **/
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ImagedProjectDTO createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Get created_at
     *
     * @return created_at
     **/
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ImagedProjectDTO fundedAt(LocalDateTime fundedAt) {
        this.fundedAt = fundedAt;
        return this;
    }

    /**
     * Get funded_at
     *
     * @return funded_at
     **/
    public LocalDateTime getFundedAt() {
        return fundedAt;
    }

    public void setFundedAt(LocalDateTime fundedAt) {
        this.fundedAt = fundedAt;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImagedProjectDTO projectDTO = (ImagedProjectDTO) o;
        return Objects.equals(this.projectId, projectDTO.projectId) &&
                Objects.equals(this.employeeId, projectDTO.employeeId) &&
                Objects.equals(this.link, projectDTO.link) &&
                Objects.equals(this.title, projectDTO.title) &&
                Objects.equals(this.description, projectDTO.description) &&
                Objects.equals(this.goal, projectDTO.goal) &&
                Objects.equals(this.investedIn, projectDTO.investedIn) &&
                Objects.equals(this.reached, projectDTO.reached) &&
                Objects.equals(this.createdAt, projectDTO.createdAt) &&
                Objects.equals(this.fundedAt, projectDTO.fundedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, employeeId, link, title, description, goal, investedIn, reached, image, createdAt, fundedAt);
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
        sb.append("    image: ").append(toIndentedString(image)).append("\n");
        sb.append("    created_at: ").append(toIndentedString(createdAt)).append("\n");
        sb.append("    funded_at: ").append(toIndentedString(fundedAt)).append("\n");
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