package de.clerkvest.api.project;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.ProjectDTO;
import de.clerkvest.api.entity.project.ProjectService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureEmbeddedDatabase
public class ProjectMapperTest {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProjectService projectService;

    @Test
    public void whenConvertCompanyEntityToCompanyDto_thenCorrect() {
        Optional<Project> optionalProject = projectService.getById(0L);
        assertThat(optionalProject.isEmpty()).isFalse();

        Project project = optionalProject.get();
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);

        assertThat(projectDTO.getId()).isEqualTo(project.getId());
        assertThat(projectDTO.getInvestedIn()).isEqualTo(project.getInvestedIn());
        assertThat(projectDTO.getEmployeeId()).isEqualTo(project.getCompany().getId());
        assertThat(projectDTO.getCompanyId()).isEqualTo(project.getCompany().getId());
        //assertThat(projectDTO.getImage()).isEqualTo(project.getImage().getId());
        assertThat(projectDTO.getLink()).isEqualTo(project.getLink());
        assertThat(projectDTO.getDescription()).isEqualTo(project.getDescription());
        assertThat(projectDTO.getGoal()).isEqualTo(project.getGoal());
        assertThat(projectDTO.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectDTO.getCreatedAt()).isEqualTo(project.getCreatedAt());
        assertThat(projectDTO.getFundedAt()).isEqualTo(project.getFundedAt());
        assertThat(projectDTO.isReached()).isEqualTo(project.isReached());
    }

    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(0L);
        projectDTO.setEmployeeId(0L);
        projectDTO.setCompanyId(0L);
        projectDTO.setLink("google.de");
        projectDTO.setTitle("Google");
        projectDTO.setDescription("Lets buy google");
        projectDTO.setGoal(BigDecimal.valueOf(100000));
        projectDTO.setInvestedIn(BigDecimal.valueOf(10));
        projectDTO.setReached(false);
        projectDTO.setImage(null);
        //projectDTO.setFundedAt();

        Project project = modelMapper.map(projectDTO, Project.class);

        assertThat(projectDTO.getId()).isEqualTo(project.getId());
        assertThat(projectDTO.getInvestedIn()).isEqualTo(project.getInvestedIn());
        //assertThat(projectDTO.getEmployeeId()).isEqualTo(project.getCompany().getId());
        //assertThat(projectDTO.getCompanyId()).isEqualTo(project.getCompany().getId());
        //assertThat(projectDTO.getImage()).isEqualTo(project.getImage().getId());
        assertThat(projectDTO.getLink()).isEqualTo(project.getLink());
        assertThat(projectDTO.getDescription()).isEqualTo(project.getDescription());
        assertThat(projectDTO.getGoal()).isEqualTo(project.getGoal());
        assertThat(projectDTO.getTitle()).isEqualTo(project.getTitle());
        //assertThat(projectDTO.getCreatedAt()).isEqualTo(project.getCreatedAt());
        //assertThat(projectDTO.getFundedAt()).isEqualTo(project.getFundedAt());
        assertThat(projectDTO.isReached()).isEqualTo(project.isReached());
    }
}
