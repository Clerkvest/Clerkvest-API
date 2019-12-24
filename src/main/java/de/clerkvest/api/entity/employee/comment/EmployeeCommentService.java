package de.clerkvest.api.entity.employee.comment;

import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.employee.comment <p>
 * EmployeeCommentService.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:09
 */
@Service
public class EmployeeCommentService implements IService<EmployeeComment> {

    private final EmployeeCommentRepository repository;

    @Autowired
    public EmployeeCommentService (EmployeeCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save (EmployeeComment employeeComment) {
        repository.save(employeeComment);
    }

    @Override
    public List<EmployeeComment> getAll () {
        return repository.findAll();
    }

    @Override
    public Optional<EmployeeComment> getById (long id) {
        return repository.findById(id);
    }

    @Override
    public void delete (EmployeeComment employeeComment) {
        repository.delete(employeeComment);
    }
}
