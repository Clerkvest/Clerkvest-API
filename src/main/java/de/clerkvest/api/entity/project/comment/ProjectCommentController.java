package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.entity.project.ProjectService;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.DTOConverter;
import de.clerkvest.api.security.EmployeeUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.project.comment <p>
 * ProjectCommentController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:13
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
public class ProjectCommentController implements DTOConverter<ProjectComment, ProjectCommentDTO> {
    private final ProjectCommentService service;
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectCommentController(ProjectCommentService service, EmployeeService employeeService, ProjectService projectService, ModelMapper modelMapper) {
        this.service = service;
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@projectService.getById(#fresh.projectId).isPresent() ? @projectService.getById(#fresh.projectId).get().company.id.equals(#auth.companyId): true) and #fresh.employeeId.equals(#auth.employeeId)")
    @PostMapping(value = "/create")
    public ResponseEntity<ProjectCommentDTO> createProjectComment(@Valid @RequestBody ProjectCommentDTO fresh, @AuthenticationPrincipal EmployeeUserDetails auth) {
        fresh.setId(-1L);
        ProjectComment converted = convertToEntity(fresh);
        return ResponseEntity.ok().body(convertToDto(service.save(converted)));
    }

    @PreAuthorize("hasRole('ROLE_USER') and #updated.employeeId.equals(#auth.employeeId)")
    @PutMapping(value = "/update")
    public ResponseEntity<String> updatedEmployee(@Valid @RequestBody ProjectCommentDTO updated, @AuthenticationPrincipal EmployeeUserDetails auth) {
        ProjectComment converted = convertToEntity(updated);
        service.update(converted);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@projectCommentService.getById(#id).isPresent() ? @projectCommentService.getById(#id).get().employee.id.equals(#auth.employeeId) : true)")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteProjectComment(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<ProjectComment> employee = service.getById(id);
        employee.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("ProjectComment not found");
        });
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@projectService.getById(#id).isPresent() ? @projectService.getById(#id).get().company.id.equals(#auth.companyId): true)")
    @GetMapping(value = "/{id}/comments")
    public ResponseEntity<List<ProjectCommentDTO>> getAllCommentsForProject(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<List<ProjectComment>> projects = service.getByProjectId(id);
        List<ProjectCommentDTO> projectDTOs = new ArrayList<>();
        projects.ifPresent(presentProjects -> presentProjects.forEach(project -> projectDTOs.add(convertToDto(project))));
        return ResponseEntity.ok(projectDTOs);
    }

    @Override
    public ProjectCommentDTO convertToDto(ProjectComment post) {
        ProjectCommentDTO postDto = modelMapper.map(post, ProjectCommentDTO.class);
        LinkBuilder<ProjectCommentDTO> linkBuilder = new LinkBuilder<ProjectCommentDTO>()
                .withCreate(HateoasLink.PROJECT_COMMENT_CREATE)
                .withDelete(HateoasLink.PROJECT_COMMENT_DELETE)
                .withUpdate(HateoasLink.PROJECT_COMMENT_UPDATE);
        linkBuilder.ifDesiredEmbed(postDto);
        return postDto;
    }

    @Override
    public ProjectComment convertToEntity(ProjectCommentDTO postDto) {
        ProjectComment post = modelMapper.map(postDto, ProjectComment.class);
        if (postDto.getId() != null && post.getId() != -1) {
            Optional<ProjectComment> oldPost = service.getById(postDto.getId());
            if (oldPost.isPresent()) {
                ProjectComment val = oldPost.get();
                val.setText(postDto.getText());
                val.setTitle(postDto.getTitle());
                return val;
            }
        } else {
            if (postDto.getEmployeeId() != null) {
                employeeService.getById(postDto.getEmployeeId()).ifPresent(post::setEmployee);
            }
            if (postDto.getProjectId() != null) {
                projectService.getById(postDto.getProjectId()).ifPresent(post::setProject);
            }
        }
        return post;
    }
}
