package de.clerkvest.api;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyDTO;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.investment.InvestDTO;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.ProjectDTO;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import de.clerkvest.api.entity.project.comment.ProjectCommentDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new CustomModelMapper();
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
        companyMap.addMapping(Company::getImageId, CompanyDTO::setImage);
        companyMap.addMapping(src -> src.getImageId().getId(), CompanyDTO::setImage);
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

        //Project Comment
        TypeMap<ProjectComment, ProjectCommentDTO> projectCommentMap = mapper.createTypeMap(ProjectComment.class, ProjectCommentDTO.class);
        projectCommentMap.addMapping(src -> src.getEmployee().getId(), ProjectCommentDTO::setEmployeeId);
        projectCommentMap.addMapping(ProjectComment::getProjectCommentId, ProjectCommentDTO::setProjectCommentId);
        projectCommentMap.addMapping(src -> src.getProject().getId(), ProjectCommentDTO::setProjectId);
        projectCommentMap.addMappings(map -> map.skip(ProjectCommentDTO::setProjectCommentId));

        mapper.validate();
        return mapper;
    }

    private class CustomModelMapper extends ModelMapper {
        CustomModelMapper() {
            super();
            this.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            /*this.addConverter((Converter<ProjectComment, ProjectCommentDTO>) mappingContext -> {
                final ProjectComment comment = mappingContext.getSource();
                return ProjectCommentDTO.builder()
                        .projectCommentId(comment.getId())
                        .employeeId(comment.getEmployeeId().getId())
                        .projectId(comment.getProjectId().getId())
                        .text(comment.getText())
                        .title(comment.getTitle())
                        .date(comment.getDate())
                        .build();
            });*/
        }
    }
}
