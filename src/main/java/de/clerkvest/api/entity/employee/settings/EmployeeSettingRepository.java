package de.clerkvest.api.entity.employee.settings;

import de.clerkvest.api.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeSettingRepository extends JpaRepository<EmployeeSetting, Long> {
    Optional<EmployeeSetting> findByEmployee(Employee employee);
}
