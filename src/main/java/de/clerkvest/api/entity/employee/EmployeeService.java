package de.clerkvest.api.entity.employee;

import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * EmployeeService.java <p>
 *
 * @author q1x
 * @version 1.0
 * @since 21 Dec 2019 19:10
 */
@Service
public class EmployeeService implements IService<Employee> {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService (EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save (Employee employee) {
        repository.save(employee);
    }

    @Override
    public List<Employee> getAll () {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getById (long id) {
        return repository.findById(id);
    }

    @Override
    public void delete (Employee employee) {
        repository.delete(employee);
    }
}
