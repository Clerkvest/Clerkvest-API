package de.clerkvest.api.employee;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeDTO;
import de.clerkvest.api.entity.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class EmployeeMapperTest {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenConvertCompanyEntityToCompanyDto_thenCorrect() {
        Optional<Employee> optionalEmployee = employeeRepository.findById(1L);
        assertThat(optionalEmployee.isEmpty()).isFalse();

        Employee employee = optionalEmployee.get();
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        assertThat(employeeDTO.getId()).isEqualTo(employee.getId());
        assertThat(employeeDTO.getToken()).isEqualTo(employee.getToken());
        assertThat(employeeDTO.getCompanyId()).isEqualTo(employee.getCompany().getId());
        assertThat(employeeDTO.getBalance()).isEqualTo(employee.getBalance());
        assertThat(employeeDTO.getNickname()).isEqualTo(employee.getNickname());
        assertThat(employeeDTO.getFirstname()).isEqualTo(employee.getFirstname());
        assertThat(employeeDTO.getLastname()).isEqualTo(employee.getLastname());
        assertThat(employeeDTO.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeDTO.isAdmin()).isEqualTo(employee.isAdmin());
    }

    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setCompanyId(1L);
        employeeDTO.setEmail("user1@clerkvest.de");
        employeeDTO.setBalance(BigDecimal.valueOf(10));
        employeeDTO.setToken("exampleToken0");
        employeeDTO.setFirstname("Mike");
        employeeDTO.setLastname("User");
        employeeDTO.setNickname("User1ClerkNonAdmin");
        employeeDTO.setIsAdmin(false);

        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        assertThat(employeeDTO.getId()).isEqualTo(employee.getId());
        assertThat(employeeDTO.getToken()).isEqualTo(employee.getToken());
        assertThat(employeeDTO.getBalance()).isEqualTo(employee.getBalance());
        assertThat(employeeDTO.getNickname()).isEqualTo(employee.getNickname());
        assertThat(employeeDTO.getFirstname()).isEqualTo(employee.getFirstname());
        assertThat(employeeDTO.getLastname()).isEqualTo(employee.getLastname());
        assertThat(employeeDTO.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeDTO.isAdmin()).isEqualTo(employee.isAdmin());
    }
}
