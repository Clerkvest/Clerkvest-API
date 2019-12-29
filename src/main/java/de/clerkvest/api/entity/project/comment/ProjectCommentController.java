package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class ProjectCommentController {
    private final ProjectCommentService service;

    @Autowired
    public ProjectCommentController(ProjectCommentService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ProjectComment> createProjectComment(@Valid @RequestBody ProjectComment fresh) {
        service.save(fresh);
        return ResponseEntity.ok().body(fresh);
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
    public Optional<List<ProjectComment>> getAllCommentForEmployee(@PathVariable long id) {
        return service.getByEmployeeId(id);
    }

}
