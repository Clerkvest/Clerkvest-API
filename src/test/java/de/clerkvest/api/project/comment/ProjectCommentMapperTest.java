package de.clerkvest.api.project.comment;


import de.clerkvest.api.Application;
import de.clerkvest.api.entity.project.comment.ProjectComment;
import de.clerkvest.api.entity.project.comment.ProjectCommentDTO;
import de.clerkvest.api.entity.project.comment.ProjectCommentService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional

public class ProjectCommentMapperTest {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProjectCommentService projectCommentService;

    @Test
    public void whenConvertCompanyEntityToCompanyDto_thenCorrect() {
        Optional<ProjectComment> optionalProjectComment = projectCommentService.getById(1L);
        assertThat(optionalProjectComment.isEmpty()).isFalse();

        ProjectComment optionalProject = optionalProjectComment.get();
        ProjectCommentDTO projectCommentDTO = modelMapper.map(optionalProject, ProjectCommentDTO.class);

        assertThat(projectCommentDTO.getId()).isEqualTo(optionalProject.getId());
        assertThat(projectCommentDTO.getEmployeeId()).isEqualTo(optionalProject.getEmployee().getId());
        assertThat(projectCommentDTO.getProjectId()).isEqualTo(optionalProject.getProject().getId());
        assertThat(projectCommentDTO.getText()).isEqualTo(optionalProject.getText());
        assertThat(projectCommentDTO.getTitle()).isEqualTo(optionalProject.getTitle());
        assertThat(projectCommentDTO.getDate()).isEqualTo(optionalProject.getDate());
    }

    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {
        ProjectCommentDTO projectDTO = new ProjectCommentDTO();
        projectDTO.setId(1L);
        projectDTO.setEmployeeId(1L);
        projectDTO.setProjectId(1L);
        projectDTO.setTitle("Google ist ja Billig!");
        projectDTO.setText("Brudi Google ist ja krass billig");
        //projectDTO.setDate("Lets buy google");

        ProjectComment project = modelMapper.map(projectDTO, ProjectComment.class);

        assertThat(projectDTO.getId()).isEqualTo(project.getId());
        assertThat(projectDTO.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectDTO.getText()).isEqualTo(project.getText());
    }
}
