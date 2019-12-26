package de.clerkvest.api.entity.employee.comment;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * EmployeeCommentService.java <p>
 *
 * @author q1x
 * @version 1.0
 * @since 21 Dec 2019 19:09
 */
@Service
public class EmployeeCommentService implements IService<EmployeeComment> {

    private final EmployeeCommentRepository repository;

    @Autowired
    public EmployeeCommentService(EmployeeCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(EmployeeComment employeeComment) {
        repository.save(employeeComment);
    }

    @Override
    public void update(EmployeeComment employeeComment) {
        //Check if company is new
        Optional<EmployeeComment> existingEmployeeComment = repository.findById(employeeComment.getId());
        existingEmployeeComment.ifPresentOrElse(
                value -> {
                    value.setComment(employeeComment.getComment());
                    repository.save(value);
                },
                () -> {
                    throw new ClerkEntityNotFoundException("EmployeeComment can't be updated, not saved yet.");
                }
        );
    }

    @Override
    public List<EmployeeComment> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<EmployeeComment> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public void delete (EmployeeComment employeeComment) {
        repository.delete(employeeComment);
    }
}
