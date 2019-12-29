package de.clerkvest.api.entity.employee.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * api <p>
 * de.clerkvest.api.entity.employee.comment <p>
 * EmployeeCommentController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:10
 */
@RestController
@RequestMapping("/employee")
public class EmployeeCommentController {


    private final EmployeeCommentService service;

    @Autowired
    public EmployeeCommentController(EmployeeCommentService service) {
        this.service = service;
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<EmployeeComment> createEmployeeComment(@Valid @RequestBody EmployeeComment fresh) {
        service.save(fresh);
        return ResponseEntity.ok().body(fresh);
    }

    @GetMapping(value = "/{id}/comments")
    public List<EmployeeComment> getAllCommentForEmployee(@PathVariable long id) {
        return service.getByEmployeeId(id);
    }

}
