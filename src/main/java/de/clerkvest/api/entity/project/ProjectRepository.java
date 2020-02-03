package de.clerkvest.api.entity.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * api <p>
 * de.clerkvest.api.entity.project <p>
 * ProjectRepository.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:14
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT * FROM project c WHERE c.company_id = ?1", nativeQuery = true)
    List<Project> getByCompanyId(Long companyId);
}
