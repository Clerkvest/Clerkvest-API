package de.clerkvest.api.entity.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * api <p>
 * de.clerkvest.api.entity.employee.company <p>
 * CompanyController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:18
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService service;

    @Autowired
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Company> getSingleCompany(@PathVariable long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updateCompany(@Valid @RequestBody Company updated) {
        service.update(updated);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company fresh) {
        service.save(fresh);
        return ResponseEntity.ok().body(fresh);
    }
}
