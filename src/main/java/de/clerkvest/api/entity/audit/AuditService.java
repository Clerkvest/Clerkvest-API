package de.clerkvest.api.entity.audit;

import de.clerkvest.api.exception.ViolatedConstraintException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditService implements IService<Audit> {
    private final AuditRepository repository;

    @Autowired
    public AuditService(AuditRepository repository) {
        this.repository = repository;
    }

    @Override
    public Audit save(Audit audit) {
        return repository.save(audit);
    }

    @Override
    public Audit saveAndFlush(Audit audit) {
        var freshEntity = save(audit);
        repository.flush();
        return freshEntity;
    }

    @Override
    public Audit update(Audit audit) {
        throw new ViolatedConstraintException("Audits can't be updated!");
    }

    @Override
    public List<Audit> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Audit> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Audit audit) {
        throw new ViolatedConstraintException("Audits can't be deleted!");
    }
}
