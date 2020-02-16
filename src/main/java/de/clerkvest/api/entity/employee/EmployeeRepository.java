package de.clerkvest.api.entity.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.employee <p>
 * EmployeeRepository.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:14
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employee u where u.login_token = ?1", nativeQuery = true)
    Optional<Employee> login(String loginToken);

    Optional<Employee> findByToken(String token);

    List<Employee> findAllByCompany(Long companyId);

    Optional<Employee> getByEmail(String mail);

    List<Employee> findAllByCompanyAndIsAdminIsTrue(Long companyId);

}
