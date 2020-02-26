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
        var clerkCompany = Company.builder().companyId(0L).name("Clerk GmbH").domain("clerkvest.com").image(null).payAmount(BigDecimal.valueOf(25)).payInterval(1).inviteOnly(true).build();
        var companyCompany = Company.builder().companyId(1L).name("Company GmbH").domain("company.de").image(null).payAmount(BigDecimal.valueOf(15)).payInterval(30).inviteOnly(false).build();
        clerkCompany = companyService.saveAndFlush(clerkCompany);
        companyCompany = companyService.saveAndFlush(companyCompany);

        var user1 = Employee.builder().employeeId(0L).company(clerkCompany).email("user1@clerkvest.de").balance(BigDecimal.TEN).token("exampleToken0").loginToken(null).firstname("Mike").lastname("User").nickname("User1ClerkNonAdmin").isAdmin(false).build();
        var user2 = Employee.builder().employeeId(1L).company(clerkCompany).email("user2@clerkvest.de").balance(BigDecimal.valueOf(11)).token("exampleToken1").loginToken(null).firstname("Bike").lastname("User2").nickname("User2ClerkAdmin").isAdmin(true).build();
        var user3 = Employee.builder().employeeId(2L).company(companyCompany).email("user1@company.de").balance(BigDecimal.valueOf(12)).token("exampleToken2").loginToken(null).firstname("Mike").lastname("User").nickname("User1CompanyNonAdmin").isAdmin(false).build();
        var user4 = Employee.builder().employeeId(3L).company(companyCompany).email("user2@company.de").balance(BigDecimal.valueOf(13)).token("exampleToken3").loginToken(null).firstname("Bike").lastname("User2").nickname("User2CompanyAdmin").isAdmin(true).build();
        user1 = employeeService.saveAndFlush(user1);
        user2 = employeeService.saveAndFlush(user2);
        user3 = employeeService.saveAndFlush(user3);
        user4 = employeeService.saveAndFlush(user4);

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
