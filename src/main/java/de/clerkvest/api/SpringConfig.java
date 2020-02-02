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
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //Employee
        TypeMap<Employee, EmployeeDTO> employeeyMap =mapper.createTypeMap(Employee.class, EmployeeDTO.class);
        employeeyMap.addMapping(Employee::getEmployeeId,EmployeeDTO::setEmployee_id);
        employeeyMap.addMapping(Employee::is_admin,EmployeeDTO::setIs_admin);
        employeeyMap.addMapping(src -> src.getCompany().getCompanyId(), EmployeeDTO::setCompany);
        employeeyMap.addMappings(map -> map.skip(EmployeeDTO::setEmployee_id));

        //Company
        TypeMap<Company, CompanyDTO> companyMap = mapper.createTypeMap(Company.class, CompanyDTO.class);
        companyMap.addMapping(Company::getPayAmount,CompanyDTO::setPay_amount);
        companyMap.addMapping(Company::isInviteOnly,CompanyDTO::setInvite_only);
        companyMap.addMapping(Company::getPayInterval,CompanyDTO::setPay_interval);
        companyMap.addMapping(Company::getCompanyId,CompanyDTO::setCompany_id);
        companyMap.addMapping(Company::getImageId,CompanyDTO::setImage);
        companyMap.addMappings(map -> map.skip(CompanyDTO::setCompany_id));

        //mapper.createTypeMap(Image.class, ImageDTO.class);
        //Invest
        TypeMap<Invest,InvestDTO> investMap = mapper.createTypeMap(Invest.class, InvestDTO.class);
        investMap.addMapping(src -> src.getEmployeeId().getId(),InvestDTO::setEmployee_id);
        investMap.addMapping(Invest::getInvestInId,InvestDTO::setInvest_in_id);
        investMap.addMapping(Invest::getProjectId,InvestDTO::setProject_id);
        investMap.addMappings(map -> map.skip(InvestDTO::setInvest_in_id));

        //Project
        TypeMap<Project,ProjectDTO> projectMap = mapper.createTypeMap(Project.class, ProjectDTO.class);
        projectMap.addMapping(src -> src.getCompanyId().getId(),ProjectDTO::setCompanyId);
        projectMap.addMapping(src -> src.getEmployeeId().getId(),ProjectDTO::setEmployee_id);
        projectMap.addMapping(Project::getProjectId,ProjectDTO::setProject_id);
        projectMap.addMapping(Project::getFundedAt,ProjectDTO::setFunded_at);
        projectMap.addMapping(Project::getInvestedIn,ProjectDTO::setInvestedIn);
        projectMap.addMapping(Project::getCreatedAt,ProjectDTO::setCreated_at);
        projectMap.addMapping(Project::getImageId,ProjectDTO::setImage);
        projectMap.addMappings(map -> map.skip(ProjectDTO::setProject_id));

        //Project Comment
        TypeMap<ProjectComment,ProjectCommentDTO> projectCommentMap = mapper.createTypeMap(ProjectComment.class, ProjectCommentDTO.class);
        projectCommentMap.addMapping(ProjectComment::getEmployeeId,ProjectCommentDTO::setEmployee_id);
        projectCommentMap.addMapping(ProjectComment::getProjectCommentId,ProjectCommentDTO::setProject_comment_id);
        projectCommentMap.addMapping(ProjectComment::getProjectId,ProjectCommentDTO::setProject_id);
        projectCommentMap.addMappings(map -> map.skip(ProjectCommentDTO::setProject_comment_id));

        mapper.validate();
        return mapper;
    }
}
