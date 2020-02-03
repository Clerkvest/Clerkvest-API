package de.clerkvest.api.security;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("");
        }
        Optional<Employee> tUser = employeeService.findByToken(username);
        if (tUser.isEmpty()) {
            throw new UsernameNotFoundException("");
        }
        Employee employee = tUser.get();
        return new EmployeeUserDetails(employee.getId(), employee.getToken(), employee.getCompany().getId(), employee.getAuthorities());
    }
}
