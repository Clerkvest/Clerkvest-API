package de.clerkvest.api.entity.employee;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * api <p>
 * EmployeeController.java <p>
 *
 * @author q1x
 * @version 1.0
 * @since 21 Dec 2019 19:10
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping(value = "")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee fresh) {
        service.save(fresh);
        return ResponseEntity.ok().body(fresh);
    }

    @PutMapping(value = "")
    public ResponseEntity<String> updatedEmployee(@Valid @RequestBody Employee updated) {
        service.update(updated);
        return ResponseEntity.ok().build();
    }

    /*@GetMapping(value = "findByNickname")
    public ResponseEntity<Employee> findByNickname(@RequestParam("nickname") String [] nicknames){

    }*/

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Employee> getSingleEmployee(@PathVariable long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        Optional<Employee> employee = service.getById(id);
        employee.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("Company not found");
        });
        return ResponseEntity.ok().build();
    }
}
