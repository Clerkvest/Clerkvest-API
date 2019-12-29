package de.clerkvest.api.entity.investment;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.investment <p>
 * InvestController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:12
 */
@RestController
@RequestMapping("/invest")
public class InvestController {
    private final InvestService service;

    @Autowired
    public InvestController(InvestService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Invest> createInvestment(@Valid @RequestBody Invest fresh) {
        service.save(fresh);
        return ResponseEntity.ok().body(fresh);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Invest> getSingleInvestment(@PathVariable long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @GetMapping(value = "/get/employee/{id}")
    public Optional<List<Invest>> getInvestmentsByEmployee(@PathVariable long id) {
        Optional<List<Invest>> employee = service.getByEmployeeId(id);
        if (employee.isEmpty()) {
            throw new ClerkEntityNotFoundException("Investment not found");
        } else {
            return employee;
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteInvestment(@PathVariable long id) {
        Optional<Invest> employee = service.getById(id);
        employee.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("Investment not found");
        });
        return ResponseEntity.ok().build();
    }

}
