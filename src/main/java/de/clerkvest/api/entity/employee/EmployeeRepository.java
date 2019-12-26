package de.clerkvest.api.entity.employee;

import org.springframework.data.jpa.repository.JpaRepository;

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

}
