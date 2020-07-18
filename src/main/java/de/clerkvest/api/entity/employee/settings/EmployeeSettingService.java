package de.clerkvest.api.entity.employee.settings;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSettingService implements IService<EmployeeSetting> {

    private final EmployeeSettingRepository repository;

    @Autowired
    public EmployeeSettingService(EmployeeSettingRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeSetting save(EmployeeSetting employeeSetting) {
        if (employeeSetting.getId() != null && repository.existsById(employeeSetting.getId())) {
            return employeeSetting;
        }
        return repository.save(employeeSetting);
    }

    @Override
    public EmployeeSetting saveAndFlush(EmployeeSetting employeeSetting) {
        var freshEntity = save(employeeSetting);
        repository.flush();
        return freshEntity;
    }

    @Override
    public EmployeeSetting update(EmployeeSetting employeeSetting) {
        //Check if company is new
        Optional<EmployeeSetting> existingEmployeeSetting = repository.findById(employeeSetting.getId());
        if (existingEmployeeSetting.isPresent()) {
            EmployeeSetting value = existingEmployeeSetting.get();
            value.setNotificationProjectFunded(employeeSetting.isNotificationProjectFunded());
            value.setNotificationProjectComment(employeeSetting.isNotificationProjectComment());
            value.setNotificationProjectAvailable(employeeSetting.isNotificationProjectAvailable());
            value.setNotificationProjectNearlyFunded(employeeSetting.isNotificationProjectNearlyFunded());
            return repository.save(value);
        } else {
            throw new ClerkEntityNotFoundException("EmployeeSetting can't be updated, not saved yet.");
        }
    }

    @Override
    public List<EmployeeSetting> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<EmployeeSetting> getById(long id) {
        return repository.findById(id);
    }


    @Override
    public void delete(EmployeeSetting employeeSetting) {
        repository.delete(employeeSetting);
    }

    public EmployeeSetting getByUser(Employee employee) {
        Optional<EmployeeSetting> setting = repository.findByEmployee(employee);
        if (setting.isEmpty()) {
            EmployeeSetting freshSetting = new EmployeeSetting();
            freshSetting.setEmployee(employee);
            repository.save(freshSetting);
            return freshSetting;
        } else {
            return setting.get();
        }
    }
}
