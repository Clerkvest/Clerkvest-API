package de.clerkvest.api.entity.project;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.exception.ViolatedConstraintException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.project <p>
 * ProjectService.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:13
 */
@Service
public class ProjectService implements IService<Project> {

    private final ProjectRepository repository;

    @Autowired
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Project project) {
        if (project.getGoal().intValue() < 1) {
            throw new ViolatedConstraintException("Goal can't be below 1");
        }
        if (project.getGoal().compareTo(project.getInvestedIn()) == -1) {
            throw new ViolatedConstraintException("Goal can't be below InvestedIn");
        }
        if (project.getInvestedIn().intValue() < 0) {
            throw new ViolatedConstraintException("InvestedIn can't be below 0");
        }
        repository.save(project);
    }

    @Override
    public void update(Project project) {
        if (project.getGoal().intValue() < 0) {
            throw new ViolatedConstraintException("Goal can't be below 1");
        }
        if (project.getGoal().compareTo(project.getInvestedIn()) == -1) {
            throw new ViolatedConstraintException("Goal can't be below InvestedIn");
        }
        if (project.getInvestedIn().intValue() < 0) {
            throw new ViolatedConstraintException("InvestedIn can't be below 0");
        }
        //Check if company is new
        Optional<Project> existingProject = repository.findById(project.getId());
        existingProject.ifPresentOrElse(
                value -> {
                    value.setDescription(project.getDescription());
                    value.setTitle(project.getTitle());
                    value.setImage(project.getImage());
                    repository.save(value);
                },
                () -> {
                    throw new ClerkEntityNotFoundException("Project can't be updated, not saved yet.");
                }
        );
    }

    @Override
    public List<Project> getAll() {
        return repository.findAll();
    }

    public List<Project> getAllByCompany(Long companyId) {
        return repository.getByCompanyId(companyId);
    }

    @Override
    public Optional<Project> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Project project) {
        repository.delete(project);
    }
}
