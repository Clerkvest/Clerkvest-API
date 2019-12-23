package de.clerkvest.api.entity.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * api <p>
 * CompanyController.java <p>
 *
 * @author q1x
 * @version 1.0
 * @since 21 Dec 2019 17:18
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService service;

    @Autowired
    public CompanyController (CompanyService service) {
        this.service = service;
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Company> getSingleCompany (@PathVariable long id) {
        return ResponseEntity.of(service.getById(id));
    }
}
