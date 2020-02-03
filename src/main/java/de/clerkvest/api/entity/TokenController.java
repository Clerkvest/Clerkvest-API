package de.clerkvest.api.entity;

import de.clerkvest.api.entity.employee.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/token")
    public String getToken(@RequestParam("loginToken") final String loginToken) {
        String token = employeeService.login(loginToken);
        if (StringUtils.isEmpty(token)) {
            return "no token found";
        }
        return token;
    }
}
