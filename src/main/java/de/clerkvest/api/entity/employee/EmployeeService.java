package de.clerkvest.api.entity.employee;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.employee <p>
 * EmployeeService.java <p>
 *
 * @author Danny B.
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
        if (employee.getId() != null && repository.existsById(employee.getId())) {
            return;
        }
        repository.save(employee);
    }

    @Override
    public void update(Employee employee) {
        //Check if company is new
        Optional<Employee> existingEmployee = repository.findById(employee.getId());
        existingEmployee.ifPresentOrElse(
                value -> {
                    value.set_admin(employee.is_admin());
                    value.setBalance(employee.getBalance());
                    value.setFirstname(employee.getFirstname());
                    value.setLastname(employee.getLastname());
                    value.setNickname(employee.getNickname());
                    repository.save(value);
                },
                () -> {
                    throw new ClerkEntityNotFoundException("Employee can't be updated, not saved yet.");
                }
        );
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getById(long id) {
        //employee.ifPresent(linkBuilder::ifDesiredEmbed);
        return repository.findById(id);
    }

    @Override
    public void delete (Employee employee) {
        repository.delete(employee);
    }
}
