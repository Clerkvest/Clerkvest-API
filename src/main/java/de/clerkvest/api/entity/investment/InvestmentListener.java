package de.clerkvest.api.entity.investment;

import de.clerkvest.api.entity.SendNotificationsService;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.exception.ViolatedConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Component
public class InvestmentListener {
    /*final InvestService investService;
    final ProjectService projectService;
    final EmployeeService employeeService;

    public InvestmentListener(InvestService investService, ProjectService projectService, EmployeeService employeeService){
        this.investService = investService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }*/

    private SendNotificationsService sendNotificationsService;

    @Autowired
    public void setMyService(SendNotificationsService sendNotificationsService) {
        this.sendNotificationsService = sendNotificationsService;
    }

    @PrePersist
    void preCreate(Invest invest) {
        Project project = invest.getProject();
        if (project != null) {
            project.setInvestedIn(project.getInvestedIn().add(invest.getInvestment()));
            if (project.getInvestedIn().equals(project.getGoal())) {
                project.setReached(true);
                project.setFundedAt(LocalDateTime.now());
            }
            if (!project.isReached() && project.getInvestedIn().divide(project.getGoal(), RoundingMode.DOWN).intValue() >= 80) {
                //80% Funded send notification
                sendNotificationsService.projectNearlyFunded(project.getEmployee(), project);
            }
        }
        Employee employee = invest.getEmployee();
        if (employee != null) {
            employee.setBalance(employee.getBalance().subtract(invest.getInvestment()));
        }
    }

    @PreUpdate
    void preUpdate(Invest invest) {
        throw new ViolatedConstraintException("Investments can't be updated");
    }

    @PreRemove
    void preRemove(Invest invest) {
        Project project = invest.getProject();
        if (project != null) {
            if (project.isReached()) {
                throw new ViolatedConstraintException("Can't remove Investment from finished Project");
            }
            project.setInvestedIn(project.getInvestedIn().subtract(invest.getInvestment()));
        }
        Employee employee = invest.getEmployee();
        if (employee != null) {
            employee.setBalance(employee.getBalance().add(invest.getInvestment()));
        }
    }
}
