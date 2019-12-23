package de.clerkvest.api.entity.project;

import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * ProjectService.java <p>
 *
 * @author q1x
 * @version 1.0
 * @since 21 Dec 2019 19:13
 */
@Service
public class ProjectService implements IService<Project> {

    private final ProjectRepository repository;

    @Autowired
    public ProjectService (ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save (Project project) {
        repository.save(project);
    }

    @Override
    public List<Project> getAll () {
        return repository.findAll();
    }

    @Override
    public Optional<Project> getById (long id) {
        return repository.findById(id);
    }

    @Override
    public void delete (Project project) {
        repository.delete(project);
    }
}
