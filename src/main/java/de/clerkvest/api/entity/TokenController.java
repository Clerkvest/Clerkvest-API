package de.clerkvest.api.entity;

import de.clerkvest.api.common.mail.MailUtil;
import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.exception.CompanyNotMatchException;
import de.clerkvest.api.exception.EntityExistsException;
import de.clerkvest.api.exception.NotEnoughPermissionsException;
import de.clerkvest.api.security.EmployeeUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
public class TokenController {

    private final Logger log = LoggerFactory.getLogger(TokenController.class);
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final SendGridEmailService sendGridEmailService;

    @Autowired
    public TokenController(EmployeeService employeeService, SendGridEmailService sendGridEmailService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.sendGridEmailService = sendGridEmailService;
        this.companyService = companyService;
    }

    @GetMapping("/token/{loginToken}")
    public ResponseEntity<StringResponse> getToken(@PathVariable String loginToken) {
        String token = employeeService.login(loginToken);
        if (StringUtils.isEmpty(token)) {
            return new ResponseEntity<>(new StringResponse("No Token Found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new StringResponse(token));
    }

    @Validated
    @PostMapping("/login")
    public ResponseEntity<StringResponse> login(@Email @RequestParam("mail") final String mail) {
        Optional<Employee> employeeOptional = employeeService.getByMail(mail);
        employeeOptional.ifPresentOrElse(this::login, () -> {
            Employee employee = MailUtil.createEmployeeFromMail(mail, companyService);
            String domain = MailUtil.getDomain(mail);
            Optional<Company> company = companyService.getByDomain(domain);

            if (company.isPresent() && company.get().isInviteOnly()) {
                throw new NotEnoughPermissionsException("Company is invite only");
            }

            if (company.isEmpty()) {
                Company newCompany = Company.builder().companyId(-1L).domain(domain).inviteOnly(true).image(null).name(domain).payAmount(BigDecimal.TEN).payInterval(30).build();
                employee.setCompany(companyService.save(newCompany));
                employee.setAdmin(true);
            }
            login(employeeService.save(employee));
        });
        return ResponseEntity.ok(new StringResponse("E-Mail Sent Successfully"));
    }

    @Validated
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/invite")
    public ResponseEntity<StringResponse> invite(@Email @RequestParam("mail") final String mail, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Employee> employeeOptional = employeeService.getByMail(mail);
        employeeOptional.ifPresentOrElse(employee -> {
            throw new EntityExistsException("Employee already exists");
        }, () -> {
            Employee employee = MailUtil.createEmployeeFromMail(mail, companyService);
            String domain = MailUtil.getDomain(mail);
            Optional<Company> companyOptional = companyService.getByDomain(domain);

            companyOptional.ifPresentOrElse(company -> {
                if (company.getId() == auth.getCompanyId()) {
                    invite(employeeService.save(employee));
                } else {
                    throw new CompanyNotMatchException("Company does not match domain");
                }
            }, () -> {
                throw new CompanyNotMatchException("Unknown company");
            });
        });

        return ResponseEntity.ok(new StringResponse("E-Mail Sent Successfully"));
    }

    public void login(Employee employee) {
        String token = UUID.randomUUID().toString();
        log.debug("Generated new Token: " + token);
        employee.setLoginToken(token);
        employee.setToken(null);
        sendGridEmailService.sendLoginMail(employeeService.update(employee));
    }

    public void invite(Employee employee) {
        String token = UUID.randomUUID().toString();
        log.debug("Generated new Token: " + token);
        employee.setLoginToken(token);
        employee.setToken(null);
        sendGridEmailService.sendInviteMail(employeeService.update(employee));
    }
}
