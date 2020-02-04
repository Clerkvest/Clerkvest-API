package de.clerkvest.api.entity.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.employee.company <p>
 * CompanyRepository.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:14
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

    // @Query(value = "SELECT * FROM COMPANY c WHERE c.name = ?1", nativeQuery = true)
    Optional<Company> findCompaniesByDomain(String domain);

    Optional<Company> getCompanyByDomain(String domain);
}
