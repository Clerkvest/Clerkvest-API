package de.clerkvest.api.entity.project.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.project.comment <p>
 * ProjectCommentRepository.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:14
 */
public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long> {


    @Query(value = "SELECT * FROM PROJECT_COMMENT c WHERE c.PROJECT_ID = ?1", nativeQuery = true)
    Optional<List<ProjectComment>> getProjectCommentsByProjectId(Long id);

}
