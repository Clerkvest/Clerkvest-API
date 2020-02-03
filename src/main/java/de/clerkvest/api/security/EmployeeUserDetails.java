package de.clerkvest.api.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class EmployeeUserDetails extends User {
    private Long employeeId;
    private String token;
    private Long companyId;

    public EmployeeUserDetails(Long employeeId, String token, Long companyId, Collection<? extends GrantedAuthority> authorities) {
        super(token, token, authorities);
        this.employeeId = employeeId;
        this.token = token;
        this.companyId = companyId;
    }

    private EmployeeUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    private EmployeeUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getToken() {
        return token;
    }

    public Long getCompanyId() {
        return companyId;
    }
}
