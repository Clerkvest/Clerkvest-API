package de.clerkvest.api.entity.employee;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.DTOConverter;
import de.clerkvest.api.security.EmployeeUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * api <p>
 * de.clerkvest.api.entity.employee <p>
 * EmployeeController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:10
 */
@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController implements DTOConverter<Employee, EmployeeDTO> {

    private final EmployeeService service;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService service, CompanyService companyService, ModelMapper modelMapper) {
        this.service = service;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') and #fresh.companyId.equals(#auth.companyId)")
    @PostMapping(value = "/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO fresh, @AuthenticationPrincipal EmployeeUserDetails auth) {
        fresh.setId(-1L);
        Employee converted = convertToEntity(fresh);
        return ResponseEntity.ok().body(convertToDto(service.save(converted)));
    }

    @PreAuthorize("hasRole('ROLE_USER') and #updated.id.equals(#auth.employeeId)")
    @PutMapping(value = "/update")
    public ResponseEntity<EmployeeDTO> updatedEmployee(@Valid @RequestBody EmployeeDTO updated, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Employee converted = convertToEntity(updated);
        return ResponseEntity.ok().body(convertToDto(service.update(converted)));
    }

    /*@GetMapping(value = "findByNickname")
    public ResponseEntity<Employee> findByNickname(@RequestParam("nickname") String [] nicknames){

    }*/

    @PreAuthorize("hasRole('ROLE_USER') and (@employeeService.getById(#id).isPresent() ? @employeeService.getById(#id).get().company.id.equals(#auth.companyId) : true)")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<EmployeeDTO> getSingleEmployee(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Employee> employee = service.getById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(convertToDto(employee.get()));
        } else {
            throw new ClerkEntityNotFoundException("Employee not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/get")
    public ResponseEntity<EmployeeDTO> getSingleEmployee(@AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Employee> employee = service.getById(auth.getEmployeeId());
        if (employee.isPresent()) {
            return ResponseEntity.ok(convertToDto(employee.get()));
        } else {
            throw new ClerkEntityNotFoundException("Employee not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Company> company = companyService.getById(auth.getCompanyId());
        if (company.isPresent()) {
            List<Employee> employees = service.getAllForCompany(company.get());
            List<EmployeeDTO> employeesDTOs = Arrays.asList(modelMapper.map(employees, EmployeeDTO[].class));
            LinkBuilder<EmployeeDTO> linkBuilder = new LinkBuilder<EmployeeDTO>()
                    .withSelf(HateoasLink.EMPLOYEE_SINGLE);
            employeesDTOs.forEach(linkBuilder::ifDesiredEmbed);
            return ResponseEntity.ok(employeesDTOs);
        } else {
            throw new ClerkEntityNotFoundException("Company not found");
        }
    }

    @PreAuthorize("(hasRole('ROLE_ADMIN') and (@employeeService.getById(#id).isPresent() ? @employeeService.getById(#id).get().company.id.equals(#auth.companyId) : true)) or (hasRole('ROLE_USER') and #auth.employeeId.equals(#id))")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Employee> employee = service.getById(id);
        employee.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("Employee not found");
        });
        return ResponseEntity.ok().build();
    }

    public EmployeeDTO convertToDto(Employee post) {
        EmployeeDTO postDto = modelMapper.map(post, EmployeeDTO.class);
        LinkBuilder<EmployeeDTO> linkBuilder = new LinkBuilder<EmployeeDTO>()
                .withSelf(HateoasLink.EMPLOYEE_SINGLE)
                .withAll(HateoasLink.EMPLOYEE_ALL)
                .withCreate(HateoasLink.EMPLOYEE_CREATE)
                .withDelete(HateoasLink.EMPLOYEE_DELETE)
                .withUpdate(HateoasLink.EMPLOYEE_UPDATE);
        linkBuilder.ifDesiredEmbed(postDto);
        return postDto;
    }

    public Employee convertToEntity(EmployeeDTO postDto) {
        Employee post = modelMapper.map(postDto, Employee.class);
        if (postDto.getEmployeeId() != null && postDto.getId() != -1) {
            Optional<Employee> oldPost = service.getById(postDto.getEmployeeId());
            if (oldPost.isPresent()) {
                Employee val = oldPost.get();
                val.setNickname(postDto.getNickname());
                val.setFirstname(postDto.getFirstname());
                val.setLastname(postDto.getLastname());
                val.setBalance(postDto.getBalance());
                val.setAdmin(postDto.isAdmin());
                return val;
            }
        } else {
            if (postDto.getCompanyId() != null) {
                companyService.getById(postDto.getCompanyId()).ifPresent(post::setCompany);
            }
        }
        return post;
    }
}
