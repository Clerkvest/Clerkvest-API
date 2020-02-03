package de.clerkvest.api.entity.project;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.EmployeeService;
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
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.project <p>
 * ProjectController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:13
 */
@RestController
@RequestMapping("/project")
public class ProjectController implements DTOConverter<Project, ProjectDTO> {
    private final ProjectService service;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectController(ProjectService service, CompanyService companyService, EmployeeService employeeService, ModelMapper modelMapper) {
        this.service = service;
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_USER') and #fresh.employeeId.equals(#auth.employeeId) and #fresh.companyId.equals(#auth.companyId)")
    @PostMapping(value = "/create")
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO fresh, @AuthenticationPrincipal EmployeeUserDetails auth) throws ParseException {
        fresh.setId(-1L);
        Project converted = convertToEntity(fresh);
        service.save(converted);
        return ResponseEntity.ok().body(convertToDto(converted));
    }

    @PreAuthorize("hasRole('ROLE_USER') and #updated.employeeId.equals(#auth.employeeId)")
    @PutMapping(value = "/update")
    public ResponseEntity<ProjectDTO> updatedProject(@Valid @RequestBody ProjectDTO updated, @AuthenticationPrincipal EmployeeUserDetails auth) throws ParseException {
        Project converted = convertToEntity(updated);
        service.update(converted);
        return ResponseEntity.ok().body(convertToDto(converted));
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@projectService.getById(#id).isPresent()? @projectService.getById(#id).get().company.id.equals(#auth.companyId): true)")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ProjectDTO> getSingleProject(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Project> project = service.getById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(convertToDto(project.get()));
        } else {
            throw new ClerkEntityNotFoundException("Project not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(@AuthenticationPrincipal EmployeeUserDetails auth) {
        List<Project> projects = service.getAllByCompany(auth.getCompanyId());
        List<ProjectDTO> projectDTOs = Arrays.asList(modelMapper.map(projects, ProjectDTO[].class));
        LinkBuilder<ProjectDTO> linkBuilder = new LinkBuilder<ProjectDTO>()
                .withSelf(HateoasLink.PROJECT_SINGLE);
        projectDTOs.forEach(linkBuilder::ifDesiredEmbed);
        return ResponseEntity.ok(projectDTOs);
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@projectService.getById(#id).isPresent()? @projectService.getById(#id).get().employee.id.equals(#auth.employeeId): true)")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Project> project = service.getById(id);
        project.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("Project not found");
        });
        return ResponseEntity.ok().build();
    }

    @Override
    public ProjectDTO convertToDto(Project post) {
        ProjectDTO postDto = modelMapper.map(post, ProjectDTO.class);
        LinkBuilder<ProjectDTO> linkBuilder = new LinkBuilder<ProjectDTO>()
                .withSelf(HateoasLink.PROJECT_SINGLE)
                .withAll(HateoasLink.PROJECT_ALL)
                .withCreate(HateoasLink.PROJECT_CREATE)
                .withDelete(HateoasLink.PROJECT_DELETE)
                .withUpdate(HateoasLink.PROJECT_UPDATE);
        linkBuilder.ifDesiredEmbed(postDto);
        return postDto;
    }

    @Override
    public Project convertToEntity(ProjectDTO postDto) throws ParseException {
        Project post = modelMapper.map(postDto, Project.class);
        if (postDto.getId() != null) {
            Optional<Project> oldPost = service.getById(postDto.getId());
            if (oldPost.isPresent()) {
                Project val = oldPost.get();
                val.setTitle(postDto.getTitle());
                val.setDescription(postDto.getDescription());
                val.setGoal(postDto.getGoal());
                val.setInvestedIn(postDto.getInvestedIn());
                val.setReached(postDto.isReached());
                return val;
            }
        } else {
            if (postDto.getEmployeeId() != null) {
                employeeService.getById(postDto.getEmployeeId()).ifPresent(post::setEmployee);
            }
            if (postDto.getCompanyId() != null) {
                companyService.getById(postDto.getProjectId()).ifPresent(post::setCompany);
            }
        }
        return post;
    }
}
