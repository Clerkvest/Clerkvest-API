package de.clerkvest.api.entity;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TokenController {

    private final Logger log = LoggerFactory.getLogger(TokenController.class);
    private EmployeeService employeeService;
    private CompanyService companyService;
    private SendGridEmailService sendGridEmailService;

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
            return new ResponseEntity<StringResponse>(new StringResponse("No Token Found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new StringResponse(token));
    }

    @Validated
    @PostMapping("/login")
    public ResponseEntity<StringResponse> login(@Email @RequestParam("mail") final String mail) {
        Optional<Employee> employeeOptional = employeeService.getByMail(mail);
        String token = UUID.randomUUID().toString();
        log.debug("Generated new Token: " + token);
        employeeOptional.ifPresentOrElse(employee -> {
            employee.setLoginToken(token);
            employee.setToken(null);
            employeeService.update(employee);
            sendGridEmailService.sendText("admin@clerkvest.de", employee.getEmail(), "Login Credentials", employee.getLoginToken());
        }, () -> {
            // GET Domain from mail, verify Mail & Domain; Parse First & Lastname
            String domain = mail.substring(mail.indexOf('@'));
            String firstname = mail.substring(0, mail.indexOf('.')), lastname = mail.substring(mail.indexOf('.'), mail.indexOf('@')), name = domain.substring(0, mail.indexOf('.'));
            Optional<Company> company = companyService.getByDomain(domain);
            Employee employee = Employee.builder().employeeId(-1L).balance(BigDecimal.ONE).company(null).email(mail).firstname(firstname).lastname(lastname).isAdmin(false).loginToken(token).nickname(firstname + " " + lastname).build();
            company.ifPresentOrElse(employee::setCompany, () -> {
                Company newCompany = Company.builder().companyId(-1L).domain(domain).inviteOnly(true).imageId(null).name(name).payAmount(BigDecimal.TEN).payInterval(30).build();
                companyService.save(newCompany);
                employee.setCompany(newCompany);
                employee.setAdmin(true);
            });
            employeeService.save(employee);
            sendGridEmailService.sendText("admin@clerkvest.de", employee.getEmail(), "Login Credentials", employee.getLoginToken());
        });
        return ResponseEntity.ok(new StringResponse("E-Mail Send Successfully"));
    }
}
