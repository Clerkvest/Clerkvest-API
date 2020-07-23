package de.clerkvest.api.entity;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.settings.EmployeeSettings;
import de.clerkvest.api.entity.employee.settings.EmployeeSettingsService;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SendNotificationsService {
    private final Logger log = LoggerFactory.getLogger(SendNotificationsService.class);
    private final EmployeeSettingsService employeeSettingsService;
    private final SendGridEmailService sendGridEmailService;

    public SendNotificationsService(EmployeeSettingsService employeeSettingsService, SendGridEmailService sendGridEmailService) {
        this.employeeSettingsService = employeeSettingsService;
        this.sendGridEmailService = sendGridEmailService;
    }

    public void freshProjectComment(Employee employee, ProjectComment projectComment) {
        EmployeeSettings settings = employeeSettingsService.getByUser(employee);
        if (settings.isNotificationProjectComment()) {
            sendGridEmailService.sendText("admin@clerkvest.com", employee.getEmail(), "New Comment", "New Comment in Project: " + projectComment.getTitle());
        }
    }

    public void projectFunded(Employee employee, Project project) {
        EmployeeSettings settings = employeeSettingsService.getByUser(employee);
        if (settings.isNotificationProjectFunded()) {
            sendGridEmailService.sendText("admin@clerkvest.com", employee.getEmail(), "Project funded", "Your Project: " + project.getTitle() + " got funded.");
        }
    }

    public void projectNearlyFunded(Employee employee, Project project) {
        EmployeeSettings settings = employeeSettingsService.getByUser(employee);
        if (settings.isNotificationProjectNearlyFunded()) {
            sendGridEmailService.sendText("admin@clerkvest.com", employee.getEmail(), "Project nearly funded", "Your Project: " + project.getTitle() + " is near completion.");
        }
    }

    public void freshProjectAvailable(Project project) {
        List<EmployeeSettings> settings = employeeSettingsService.getAll();
        //Todo: the repository should filter it in the first place
        List<EmployeeSettings> filteredSettings = settings.stream().filter(employeeSettings -> employeeSettings.getEmployee().getCompany().equals(project.getCompany())).collect(Collectors.toList());
        settings.forEach(employeeSettings -> {
            if (employeeSettings.isNotificationProjectAvailable()) {
                sendGridEmailService.sendText("admin@clerkvest.com", employeeSettings.getEmployee().getEmail(), "New Project", "There is a new Project available: " + project.getTitle());
            }
        });
    }
}
