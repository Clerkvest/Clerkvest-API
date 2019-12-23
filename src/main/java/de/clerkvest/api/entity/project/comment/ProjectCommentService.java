package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * ProjectCommentService.java <p>
 *
 * @author q1x
 * @version 1.0
 * @since 21 Dec 2019 19:12
 */
@Service
public class ProjectCommentService implements IService<ProjectComment> {

    private final ProjectCommentRepository repository;

    @Autowired
    public ProjectCommentService (ProjectCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save (ProjectComment projectComment) {
        repository.save(projectComment);
    }

    @Override
    public List<ProjectComment> getAll () {
        return repository.findAll();
    }

    @Override
    public Optional<ProjectComment> getById (long id) {
        return repository.findById(id);
    }

    @Override
    public void delete (ProjectComment projectComment) {
        repository.delete(projectComment);
    }
}
