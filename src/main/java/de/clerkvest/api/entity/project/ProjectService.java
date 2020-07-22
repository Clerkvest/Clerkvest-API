package de.clerkvest.api.entity.project;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.exception.ViolatedConstraintException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProjectService implements IService<Project> {

    private final ProjectRepository repository;

    @Autowired
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Project save(Project project) {
        if (project.getGoal().intValue() < 1) {
            throw new ViolatedConstraintException("Goal can't be below 1");
        }
        if (project.getGoal().compareTo(project.getInvestedIn()) == -1) {
            throw new ViolatedConstraintException("Goal can't be below InvestedIn");
        }
        if (project.getInvestedIn().intValue() < 0) {
            throw new ViolatedConstraintException("InvestedIn can't be below 0");
        }
        return repository.save(project);
    }

    @Override
    public Project saveAndFlush(Project project) {
        var freshEntity = save(project);
        repository.flush();
        return freshEntity;
    }

    @Override
    public Project update(Project project) {
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
        if (existingProject.isPresent()) {
            Project value = existingProject.get();
            value.setDescription(project.getDescription());
            value.setTitle(project.getTitle());
            value.setImage(project.getImage());
            return repository.save(value);
        } else {
            throw new ClerkEntityNotFoundException("Project can't be updated, not saved yet.");
        }
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
        if (project.isReached()) {
            throw new ViolatedConstraintException("Funded Projects can't be deleted");
        }
        repository.delete(project);
    }
}
