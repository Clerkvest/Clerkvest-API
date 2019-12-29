package de.clerkvest.api.entity.employee.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.employee.comment <p>
 * EmployeeCommentRepository.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:14
 */
public interface EmployeeCommentRepository extends JpaRepository<EmployeeComment, Long> {

    // @Query(value = "SELECT * FROM COMPANY c WHERE c.name = ?1", nativeQuery = true)
    Optional<List<EmployeeComment>> getByEmployeeId(Long id);
}
