package de.clerkvest.api.entity.investment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.investment <p>
 * InvestRepository.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:14
 */
public interface InvestRepository extends JpaRepository<Invest, Long> {

    @Query(value = "SELECT * FROM invest_in i WHERE i.employee_id = ?1", nativeQuery = true)
    Optional<List<Invest>> getByEmployeeId(Long id);

    @Query(value = "SELECT * FROM invest_in i WHERE i.project_id = ?1", nativeQuery = true)
    List<Invest> getByProjectId(Long id);

    @Query(value = "SELECT * FROM invest_in i WHERE i.project_id = ?1 AND i.employee_id = ?2", nativeQuery = true)
    List<Invest> getByProjectIdAAndEmployee(Long id, Long employee);
}
