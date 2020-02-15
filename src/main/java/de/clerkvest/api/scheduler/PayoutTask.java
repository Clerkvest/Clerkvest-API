package de.clerkvest.api.scheduler;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Danny B.
 * @version 1.0
 * @since 1.0
 */
@Component
public class PayoutTask {

    private final Logger logger = LoggerFactory.getLogger(PayoutTask.class);

    private final CompanyService companyService;

    private final EmployeeService employeeService;

    @Autowired
    public PayoutTask(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @Scheduled(cron = "0 0 3 ? * *")
    public void payout() {
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();

        companyService.getAll().forEach(company -> {
            if (company.getPayInterval() == dayOfMonth) {
                payoutEmployees(company);
            }
        });
    }

    private void payoutEmployees(Company company) {
        employeeService.getAllForCompany(company.getId()).forEach(employee -> {
            employee.setBalance(employee.getBalance().add(company.getPayAmount()));
            employeeService.save(employee);
        });
    }
}
