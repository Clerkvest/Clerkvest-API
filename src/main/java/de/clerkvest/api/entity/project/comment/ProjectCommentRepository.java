package de.clerkvest.api.entity.project.comment;

import de.clerkvest.api.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * api <p>
 * ProjectCommentRepository.java <p>
 *
 * @author q1x
 * @version 1.0
 * @since 21 Dec 2019 17:14
 */
public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long> {

}
