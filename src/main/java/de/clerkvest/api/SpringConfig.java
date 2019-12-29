package de.clerkvest.api;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.employee.comment.EmployeeComment;
import de.clerkvest.api.entity.image.Image;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        mapper.createTypeMap(Employee.class, EmployeeDTO.class);
       /* mapper.createTypeMap(Company.class, CompanyDTO.class);
        mapper.createTypeMap(EmployeeComment.class, EmployeeCommentDTO.class);
        mapper.createTypeMap(Image.class, ImageDTO.class);
        mapper.createTypeMap(Invest.class, InvestDTO.class);
        mapper.createTypeMap(Project.class, ProjectDTO.class);
        mapper.createTypeMap(ProjectComment.class, ProjectCommentDTO.class);*/
        mapper.validate();
        return mapper;
    }
}
