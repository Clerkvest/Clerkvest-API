package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.ProjectDTO;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.DTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Arrays;
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
public class ProjectCommentController implements DTOConverter<ProjectComment,ProjectCommentDTO> {
    private final ProjectCommentService service;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectCommentController(ProjectCommentService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ProjectCommentDTO> createProjectComment(@Valid @RequestBody ProjectComment fresh) {
        service.save(fresh);
        return ResponseEntity.ok().body(convertToDto(fresh));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updatedEmployee(@Valid @RequestBody ProjectComment updated) {
        service.update(updated);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteProjectComment(@PathVariable long id) {
        Optional<ProjectComment> employee = service.getById(id);
        employee.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("ProjectComment not found");
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/comments")
    public ResponseEntity<List<ProjectCommentDTO>> getAllCommentsForProject(@PathVariable long id) {
        Optional<List<ProjectComment>> projects = service.getByProjectId(id);
        List<ProjectCommentDTO> projectDTOs = Arrays.asList(modelMapper.map(projects, ProjectCommentDTO[].class));
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
    public ProjectComment convertToEntity(ProjectCommentDTO postDto) throws ParseException {
        ProjectComment post = modelMapper.map(postDto, ProjectComment.class);
        if (postDto.getId() != null) {
            Optional<ProjectComment> oldPost = service.getById(postDto.getId());
            //oldPost.ifPresent(value -> {post.setNickname(value.getNickname());});
        }
        return post;
    }
}
