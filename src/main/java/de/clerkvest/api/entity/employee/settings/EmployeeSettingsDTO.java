package de.clerkvest.api.entity.employee.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Builder
@AllArgsConstructor
@JsonIgnoreProperties(value = {"employeeSettingId"}, ignoreUnknown = true)
public class EmployeeSettingsDTO extends RepresentationModel<EmployeeSettingsDTO> implements IServiceEntity {
    private Long employeeSettingId;
    private Long employeeId;

    private boolean notificationProjectFunded;

    private boolean notificationProjectComment;

    private boolean notificationProjectAvailable;

    private boolean notificationProjectNearlyFunded;

    public EmployeeSettingsDTO(EmployeeSettings employeeSettings) {
        employeeSettingId = employeeSettings.getId();
        employeeId = employeeSettings.getEmployee().getId();
        notificationProjectFunded = employeeSettings.isNotificationProjectFunded();
        notificationProjectComment = employeeSettings.isNotificationProjectComment();
        notificationProjectAvailable = employeeSettings.isNotificationProjectAvailable();
        notificationProjectNearlyFunded = employeeSettings.isNotificationProjectNearlyFunded();
    }

    public EmployeeSettingsDTO() {

    }

    @Override
    public Long getId() {
        return employeeSettingId;
    }

    @Override
    public void setId(Long id) {
        employeeSettingId = id;
    }

    public Long getEmployeeSettingId() {
        return employeeSettingId;
    }

    public void setEmployeeSettingId(Long employeeSettingId) {
        this.employeeSettingId = employeeSettingId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isNotificationProjectFunded() {
        return notificationProjectFunded;
    }

    public void setNotificationProjectFunded(boolean notificationProjectFunded) {
        this.notificationProjectFunded = notificationProjectFunded;
    }

    public boolean isNotificationProjectComment() {
        return notificationProjectComment;
    }

    public void setNotificationProjectComment(boolean notificationProjectComment) {
        this.notificationProjectComment = notificationProjectComment;
    }

    public boolean isNotificationProjectAvailable() {
        return notificationProjectAvailable;
    }

    public void setNotificationProjectAvailable(boolean notificationProjectAvailable) {
        this.notificationProjectAvailable = notificationProjectAvailable;
    }

    public boolean isNotificationProjectNearlyFunded() {
        return notificationProjectNearlyFunded;
    }

    public void setNotificationProjectNearlyFunded(boolean notificationProjectNearlyFunded) {
        this.notificationProjectNearlyFunded = notificationProjectNearlyFunded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmployeeSettingsDTO that = (EmployeeSettingsDTO) o;
        return notificationProjectFunded == that.notificationProjectFunded &&
                notificationProjectComment == that.notificationProjectComment &&
                notificationProjectAvailable == that.notificationProjectAvailable &&
                notificationProjectNearlyFunded == that.notificationProjectNearlyFunded &&
                employeeSettingId.equals(that.employeeSettingId) &&
                employeeId.equals(that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), employeeSettingId, employeeId, notificationProjectFunded, notificationProjectComment, notificationProjectAvailable, notificationProjectNearlyFunded);
    }

    @Override
    public String toString() {
        return "EmployeeSettingDTO{" +
                "employeeSettingId=" + employeeSettingId +
                ", employeeId=" + employeeId +
                ", notificationProjectFunded=" + notificationProjectFunded +
                ", notificationProjectComment=" + notificationProjectComment +
                ", notificationProjectAvailable=" + notificationProjectAvailable +
                ", notificationProjectNearlyFunded=" + notificationProjectNearlyFunded +
                '}';
    }
}
