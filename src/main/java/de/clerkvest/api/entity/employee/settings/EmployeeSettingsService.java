package de.clerkvest.api.entity.employee.settings;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EmployeeSettingsService implements IService<EmployeeSettings> {

    private final EmployeeSettingsRepository repository;

    @Autowired
    public EmployeeSettingsService(EmployeeSettingsRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeSettings save(EmployeeSettings employeeSettings) {
        if (employeeSettings.getId() != null && repository.existsById(employeeSettings.getId())) {
            return employeeSettings;
        }
        return repository.save(employeeSettings);
    }

    @Override
    public EmployeeSettings saveAndFlush(EmployeeSettings employeeSettings) {
        var freshEntity = save(employeeSettings);
        repository.flush();
        return freshEntity;
    }

    @Override
    public EmployeeSettings update(EmployeeSettings employeeSettings) {
        //Check if company is new
        Optional<EmployeeSettings> existingEmployeeSetting = repository.findById(employeeSettings.getId());
        if (existingEmployeeSetting.isPresent()) {
            EmployeeSettings value = existingEmployeeSetting.get();
            value.setNotificationProjectFunded(employeeSettings.isNotificationProjectFunded());
            value.setNotificationProjectComment(employeeSettings.isNotificationProjectComment());
            value.setNotificationProjectAvailable(employeeSettings.isNotificationProjectAvailable());
            value.setNotificationProjectNearlyFunded(employeeSettings.isNotificationProjectNearlyFunded());
            return repository.save(value);
        } else {
            throw new ClerkEntityNotFoundException("EmployeeSetting can't be updated, not saved yet.");
        }
    }

    @Override
    public List<EmployeeSettings> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<EmployeeSettings> getById(long id) {
        return repository.findById(id);
    }


    @Override
    public void delete(EmployeeSettings employeeSettings) {
        repository.delete(employeeSettings);
    }

    public EmployeeSettings getByUser(Employee employee) {
        Optional<EmployeeSettings> setting = repository.findByEmployee(employee);
        if (setting.isEmpty()) {
            EmployeeSettings freshSetting = new EmployeeSettings();
            freshSetting.setEmployee(employee);
            repository.saveAndFlush(freshSetting);
            return freshSetting;
        } else {
            return setting.get();
        }
    }
}
