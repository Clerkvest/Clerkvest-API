package de.clerkvest.api.entity.project;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class ProjectController {
        private final ProjectService service;

        @Autowired
        public ProjectController(ProjectService service) {
                this.service = service;
        }

        @PostMapping(value = "/create")
        public ResponseEntity<Project> createProject(@Valid @RequestBody Project fresh) {
                service.save(fresh);
                return ResponseEntity.ok().body(fresh);
        }

        @PutMapping(value = "/update")
        public ResponseEntity<String> updatedProject(@Valid @RequestBody Project updated) {
                service.update(updated);
                return ResponseEntity.ok().build();
        }

        @GetMapping(value = "/get/{id}")
        public ResponseEntity<Project> getSingleProject(@PathVariable long id) {
                return ResponseEntity.of(service.getById(id));
        }

        @GetMapping(value = "/all")
        public List<Project> getAllProjects(@PathVariable long id) {
                return service.getAll();
        }

        @DeleteMapping(value = "/delete/{id}")
        public ResponseEntity<String> deleteProject(@PathVariable long id) {
                Optional<Project> project = service.getById(id);
                project.ifPresentOrElse(service::delete, () -> {
                        throw new ClerkEntityNotFoundException("Company not found");
                });
                return ResponseEntity.ok().build();
        }
}
