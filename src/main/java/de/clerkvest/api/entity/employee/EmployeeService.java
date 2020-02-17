package de.clerkvest.api.entity.employee;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.service.IService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() != null && repository.existsById(employee.getId())) {
            return employee;
        }
        return repository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        //Check if company is new
        Optional<Employee> existingEmployee = repository.findById(employee.getId());
        if (existingEmployee.isPresent()) {
            Employee value = existingEmployee.get();
            value.setAdmin(employee.isAdmin());
            value.setBalance(employee.getBalance());
            value.setFirstname(employee.getFirstname());
            value.setLastname(employee.getLastname());
            value.setNickname(employee.getNickname());
            return repository.save(value);
        } else {
            throw new ClerkEntityNotFoundException("Employee can't be updated, not saved yet.");
        }
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    public List<Employee> getAllAdmins(Company company) {
        return repository.findAllByCompanyAndIsAdminIsTrue(company);
    }

    @Override
    public Optional<Employee> getById(long id) {
        //employee.ifPresent(linkBuilder::ifDesiredEmbed);
        return repository.findById(id);
    }

    public Optional<Employee> getByMail(String mail) {
        return repository.getByEmail(mail);
    }

    @Override
    public void delete(Employee employee) {
        repository.delete(employee);
    }

    public String login(String loginToken) {
        Optional<Employee> employee = repository.login(loginToken);
        if (employee.isPresent()) {
            String token = UUID.randomUUID().toString();
            Employee custom = employee.get();
            custom.setToken(token);
            custom.setLoginToken(null);
            repository.save(custom);
            return token;
        }
        return StringUtils.EMPTY;
    }


    public Optional<Employee> findByToken(String token) {
        Optional<Employee> employee = repository.findByToken(token);
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            return Optional.of(employee1);
        }
        return Optional.empty();
    }

    public List<Employee> getAllForCompany(Company company) {
        return repository.findAllByCompany(company);
    }
}
