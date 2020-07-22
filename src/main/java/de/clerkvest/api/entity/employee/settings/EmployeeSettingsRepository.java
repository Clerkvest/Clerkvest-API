package de.clerkvest.api.entity.employee.settings;

import de.clerkvest.api.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeSettingsRepository extends JpaRepository<EmployeeSettings, Long> {
    Optional<EmployeeSettings> findByEmployee(Employee employee);
}
