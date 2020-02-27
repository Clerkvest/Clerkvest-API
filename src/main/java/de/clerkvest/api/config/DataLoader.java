package de.clerkvest.api.config;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.investment.InvestService;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.ProjectService;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import de.clerkvest.api.entity.project.comment.ProjectCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Profile({"dev", "test"})
@Component
public class DataLoader {
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final InvestService investService;
    private final ProjectCommentService projectCommentService;
    private final ProjectService projectService;

    @Autowired
    public DataLoader(CompanyService companyService, EmployeeService employeeService, InvestService investService, ProjectCommentService projectCommentService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.investService = investService;
        this.projectCommentService = projectCommentService;
        this.projectService = projectService;
        initEntities();
    }

    void initEntities() {
        //Company clerkCompany = Company.builder().companyId(0L).name("Clerk GmbH").domain("clerkvest.com").image(null).payAmount(BigDecimal.valueOf(25)).payInterval(1).inviteOnly(true).build();
        //Company companyCompany = Company.builder().companyId(1L).name("Company GmbH").domain("company.de").image(null).payAmount(BigDecimal.valueOf(15)).payInterval(30).inviteOnly(false).build();

        Company clerkCompany = new Company();
        clerkCompany.setCompanyId(1L);
        clerkCompany.setName("Clerk GmbH");
        clerkCompany.setDomain("clerkvest.com");
        clerkCompany.setPayAmount(BigDecimal.TEN);
        clerkCompany.setPayInterval(1);
        clerkCompany.setInviteOnly(true);
        clerkCompany.setImage(null);

        Company companyCompany = new Company();
        companyCompany.setCompanyId(2L);
        companyCompany.setName("Company GmbH");
        companyCompany.setDomain("company.com");
        companyCompany.setPayAmount(BigDecimal.TEN);
        companyCompany.setPayInterval(1);
        companyCompany.setInviteOnly(true);
        companyCompany.setImage(null);

        clerkCompany = companyService.save(clerkCompany);
        companyCompany = companyService.save(companyCompany);

        Employee user1 = new Employee();
        user1.setEmployeeId(0L);
        user1.setCompany(clerkCompany);
        user1.setEmail("user1@clerkvest.de");
        user1.setBalance(BigDecimal.TEN);
        user1.setToken("exampleToken0");
        user1.setLoginToken(null);
        user1.setFirstname("Mike");
        user1.setLastname("User");
        user1.setNickname("User1ClerkNonAdmin");
        user1.setAdmin(false);

        Employee user2 = new Employee();
        user2.setEmployeeId(1L);
        user2.setCompany(clerkCompany);
        user2.setEmail("user2@clerkvest.de");
        user2.setBalance(BigDecimal.valueOf(11));
        user2.setToken("exampleToken1");
        user2.setLoginToken(null);
        user2.setFirstname("Bike");
        user2.setLastname("User2");
        user2.setNickname("User2ClerkAdmin");
        user2.setAdmin(true);

        Employee user3 = new Employee();
        user3.setEmployeeId(2L);
        user3.setCompany(companyCompany);
        user3.setEmail("user1@company.de");
        user3.setBalance(BigDecimal.valueOf(12));
        user3.setToken("exampleToken2");
        user3.setLoginToken(null);
        user3.setFirstname("Mike");
        user3.setLastname("User");
        user3.setNickname("User1CompanyNonAdmin");
        user3.setAdmin(false);

        Employee user4 = new Employee();
        user4.setEmployeeId(3L);
        user4.setCompany(companyCompany);
        user4.setEmail("user2@company.de");
        user4.setBalance(BigDecimal.valueOf(13));
        user4.setToken("exampleToken3");
        user4.setLoginToken(null);
        user4.setFirstname("Bike");
        user4.setLastname("User2");
        user4.setNickname("User2CompanyAdmin");
        user4.setAdmin(true);

        user1 = employeeService.save(user1);
        user2 = employeeService.save(user2);
        user3 = employeeService.save(user3);
        user4 = employeeService.save(user4);

        var google = Project.builder().projectId(0L).employee(user1).company(clerkCompany).link("google.de").title("Google").description("Lets buy google").goal(BigDecimal.valueOf(100000)).investedIn(BigDecimal.ZERO).reached(false).image(null).fundedAt(null).build();
        var amazon = Project.builder().projectId(1L).employee(user3).company(companyCompany).link("amazon.com").title("Amazon").description("Dis").goal(BigDecimal.valueOf(5)).investedIn(BigDecimal.ZERO).reached(false).image(null).fundedAt(null).build();
        google = projectService.saveAndFlush(google);
        amazon = projectService.saveAndFlush(amazon);

        var investment1 = Invest.builder().investInId(0L).project(google).employee(user1).investment(BigDecimal.valueOf(5)).build();
        var investment2 = Invest.builder().investInId(1L).project(google).employee(user2).investment(BigDecimal.valueOf(5)).build();
        var investment3 = Invest.builder().investInId(2L).project(amazon).employee(user3).investment(BigDecimal.valueOf(5)).build();
        investment1 = investService.saveAndFlush(investment1);
        investment2 = investService.saveAndFlush(investment2);
        investment3 = investService.saveAndFlush(investment3);

        var comment1 = ProjectComment.builder().projectCommentId(0L).employee(user1).project(google).title("Google ist ja Billig!").text("Brudi Google ist ja krass billig").date(Timestamp.from(Instant.now())).build();
        comment1 = projectCommentService.saveAndFlush(comment1);
    }
}
