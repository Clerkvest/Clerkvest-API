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

    @Query(value = "SELECT * FROM Invest i WHERE i.employeeId = ?1", nativeQuery = true)
    Optional<List<Invest>> getByEmployeeId(Long id);
}
