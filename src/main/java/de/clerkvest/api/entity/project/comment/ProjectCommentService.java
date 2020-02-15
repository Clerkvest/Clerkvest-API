package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.project.comment <p>
 * ProjectCommentService.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:12
 */
@Service
public class ProjectCommentService implements IService<ProjectComment> {

    private final ProjectCommentRepository repository;

    @Autowired
    public ProjectCommentService(ProjectCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProjectComment save(ProjectComment projectComment) {
        return repository.save(projectComment);
    }

    @Override
    public ProjectComment update(ProjectComment projectComment) {
        //Check if ProjectComment is new
        Optional<ProjectComment> existingProjectComment = repository.findById(projectComment.getId());
        if (existingProjectComment.isPresent()) {
            ProjectComment value = existingProjectComment.get();
            value.setTitle(projectComment.getTitle());
            value.setText(projectComment.getText());
            value.setDate(Timestamp.from(Instant.now()));
            return repository.save(value);
        } else {
            throw new ClerkEntityNotFoundException("ProjectComment can't be updated, not saved yet.");
        }
    }

    @Override
    public List<ProjectComment> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ProjectComment> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(ProjectComment projectComment) {
        repository.delete(projectComment);
    }

    public Optional<List<ProjectComment>> getByProjectId(long id) {
        return repository.getProjectCommentsByProjectId(id);
    }
}
