package de.clerkvest.api.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyDTO;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.employee.settings.EmployeeSetting;
import de.clerkvest.api.entity.employee.settings.EmployeeSettingDTO;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.investment.InvestDTO;
import de.clerkvest.api.entity.project.ImagedProjectDTO;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.ProjectDTO;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import de.clerkvest.api.entity.project.comment.ProjectCommentDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.context.annotation.*;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.zalando.logbook.Logbook;

import javax.sql.DataSource;

@Configuration
@EnableFilesystemStores
@ComponentScan
public class SpringConfig {

    @Profile({"dev", "test"})
    @Bean
    @Primary
    public DataSource inMemoryDS() throws Exception {
        DataSource embeddedPostgresDS = EmbeddedPostgres.builder()
                .start().getPostgresDatabase();
        //EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("db/migration/V1__Base_version"));
        return embeddedPostgresDS;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //Employee
        TypeMap<Employee, EmployeeDTO> employeeyMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);
        employeeyMap.addMapping(Employee::getEmployeeId, EmployeeDTO::setEmployeeId);
        employeeyMap.addMapping(Employee::isAdmin, EmployeeDTO::setIsAdmin);
        employeeyMap.addMapping(src -> src.getCompany().getId(), EmployeeDTO::setCompanyId);
        employeeyMap.addMappings(map -> map.skip(EmployeeDTO::setEmployeeId));

        //Company
        TypeMap<Company, CompanyDTO> companyMap = mapper.createTypeMap(Company.class, CompanyDTO.class);
        companyMap.addMapping(Company::getPayAmount, CompanyDTO::setPayAmount);
        companyMap.addMapping(Company::isInviteOnly, CompanyDTO::setInviteOnly);
        companyMap.addMapping(Company::getPayInterval, CompanyDTO::setPayInterval);
        companyMap.addMapping(Company::getCompanyId, CompanyDTO::setCompanyId);
        companyMap.addMapping(Company::getImage, CompanyDTO::setImage);
        companyMap.addMapping(src -> src.getImage().getId(), CompanyDTO::setImage);
        companyMap.addMappings(map -> map.skip(CompanyDTO::setCompanyId));

        //mapper.createTypeMap(Image.class, ImageDTO.class);
        //Invest
        TypeMap<Invest, InvestDTO> investMap = mapper.createTypeMap(Invest.class, InvestDTO.class);
        investMap.addMapping(src -> src.getEmployee().getId(), InvestDTO::setEmployeeId);
        investMap.addMapping(Invest::getInvestInId, InvestDTO::setInvest_in_id);
        //investMap.addMapping(Invest::getProjectId,InvestDTO::setProjectId);
        investMap.addMapping(src -> src.getProject().getId(), InvestDTO::setProjectId);
        investMap.addMappings(map -> map.skip(InvestDTO::setInvest_in_id));

        //Project
        TypeMap<Project, ProjectDTO> projectMap = mapper.createTypeMap(Project.class, ProjectDTO.class);
        projectMap.addMapping(src -> src.getCompany().getId(), ProjectDTO::setCompanyId);
        projectMap.addMapping(src -> src.getEmployee().getId(), ProjectDTO::setEmployeeId);
        projectMap.addMapping(Project::getProjectId, ProjectDTO::setProjectId);
        projectMap.addMapping(Project::getFundedAt, ProjectDTO::setFundedAt);
        projectMap.addMapping(Project::getInvestedIn, ProjectDTO::setInvestedIn);
        projectMap.addMapping(Project::getCreatedAt, ProjectDTO::setCreatedAt);
        //projectMap.addMapping(Project::getImageId,ProjectDTO::setImage);
        projectMap.addMapping(src -> src.getImage().getId(), ProjectDTO::setImage);
        projectMap.addMappings(map -> map.skip(ProjectDTO::setProjectId));

        //ImagedProject
        TypeMap<Project, ImagedProjectDTO> imagedProjectDTO = mapper.createTypeMap(Project.class, ImagedProjectDTO.class);
        imagedProjectDTO.addMapping(src -> src.getCompany().getId(), ImagedProjectDTO::setCompanyId);
        imagedProjectDTO.addMapping(src -> src.getEmployee().getId(), ImagedProjectDTO::setEmployeeId);
        imagedProjectDTO.addMapping(Project::getProjectId, ImagedProjectDTO::setProjectId);
        imagedProjectDTO.addMapping(Project::getFundedAt, ImagedProjectDTO::setFundedAt);
        imagedProjectDTO.addMapping(Project::getInvestedIn, ImagedProjectDTO::setInvestedIn);
        imagedProjectDTO.addMapping(Project::getCreatedAt, ImagedProjectDTO::setCreatedAt);
        //projectMap.addMapping(Project::getImageId,ProjectDTO::setImage);
        imagedProjectDTO.addMappings(map -> map.skip(ImagedProjectDTO::setProjectId));

        //Project Comment
        TypeMap<ProjectComment, ProjectCommentDTO> projectCommentMap = mapper.createTypeMap(ProjectComment.class, ProjectCommentDTO.class);
        projectCommentMap.addMapping(src -> src.getEmployee().getId(), ProjectCommentDTO::setEmployeeId);
        projectCommentMap.addMapping(ProjectComment::getProjectCommentId, ProjectCommentDTO::setProjectCommentId);
        projectCommentMap.addMapping(src -> src.getProject().getId(), ProjectCommentDTO::setProjectId);
        projectCommentMap.addMappings(map -> map.skip(ProjectCommentDTO::setProjectCommentId));

        //Employee Setting
        TypeMap<EmployeeSetting, EmployeeSettingDTO> employeeSettingMap = mapper.createTypeMap(EmployeeSetting.class, EmployeeSettingDTO.class);
        employeeSettingMap.addMapping(src -> src.getEmployee().getId(), EmployeeSettingDTO::setEmployeeId);
        employeeSettingMap.addMapping(EmployeeSetting::getEmployeeSettingId, EmployeeSettingDTO::setEmployeeSettingId);
        employeeSettingMap.addMappings(map -> map.skip(EmployeeSettingDTO::setEmployeeSettingId));

        mapper.validate();
        return mapper;
    }

    @Bean
    public Logbook logbook() {
        return Logbook.create();
    }
}
