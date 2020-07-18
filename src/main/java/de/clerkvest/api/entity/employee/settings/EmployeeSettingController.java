package de.clerkvest.api.entity.employee.settings;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeService;
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
import java.util.Optional;

@RestController
@RequestMapping("/settings")
@CrossOrigin(origins = "*")
public class EmployeeSettingController implements DTOConverter<EmployeeSetting, EmployeeSettingDTO> {

    private final EmployeeSettingService service;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeSettingController(EmployeeSettingService service, EmployeeService employeeService, ModelMapper modelMapper) {
        this.service = service;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@employeeSettingService.getById(#id).isPresent() ? @employeeSettingService.getById(#id).get().employee.id.equals(#auth.employeeId) : true)")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<EmployeeSettingDTO> getSingleEmployeeSetting(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<EmployeeSetting> employeeSetting = service.getById(id);
        if (employeeSetting.isPresent()) {
            return ResponseEntity.ok(convertToDto(employeeSetting.get()));
        } else {
            throw new ClerkEntityNotFoundException("EmployeeSetting not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/get")
    public ResponseEntity<EmployeeSettingDTO> getSingleEmployeeSetting(@AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Employee> employee = employeeService.getById(auth.getEmployeeId());
        if (employee.isPresent()) {
            EmployeeSetting employeeSetting = service.getByUser(employee.get());
            return ResponseEntity.ok(convertToDto(employeeSetting));
        } else {
            throw new ClerkEntityNotFoundException("Employee not found");
        }
    }

    @PreAuthorize("(hasRole('ROLE_ADMIN') and (@employeeService.getById(#updated.id).isPresent() ? @employeeService.getById(#updated.id).get().company.id.equals(#auth.companyId) : false)) or (hasRole('ROLE_USER') and #updated.id.equals(#auth.employeeId))")
    @PutMapping(value = "/update")
    public ResponseEntity<EmployeeSettingDTO> updatedEmployeeSetting(@Valid @RequestBody EmployeeSettingDTO updated, @AuthenticationPrincipal EmployeeUserDetails auth) {
        EmployeeSetting converted = convertToEntity(updated);
        return ResponseEntity.ok().body(convertToDto(service.update(converted)));
    }

    @Override
    public EmployeeSettingDTO convertToDto(EmployeeSetting post) {
        EmployeeSettingDTO postDto = modelMapper.map(post, EmployeeSettingDTO.class);
        LinkBuilder<EmployeeSettingDTO> linkBuilder = new LinkBuilder<EmployeeSettingDTO>()
                .withSelf(HateoasLink.EMPLOYEE_SETTING_SINGLE);
        linkBuilder.ifDesiredEmbed(postDto);
        return postDto;
    }

    @Override
    public EmployeeSetting convertToEntity(EmployeeSettingDTO postDto) {
        EmployeeSetting post = modelMapper.map(postDto, EmployeeSetting.class);
        if (postDto.getId() != null && postDto.getId() != -1) {
            Optional<EmployeeSetting> oldPost = service.getById(postDto.getId());
            if (oldPost.isPresent()) {
                EmployeeSetting val = oldPost.get();
                val.setNotificationProjectNearlyFunded(postDto.isNotificationProjectFunded());
                val.setNotificationProjectComment(postDto.isNotificationProjectComment());
                val.setNotificationProjectAvailable(postDto.isNotificationProjectAvailable());
                val.setNotificationProjectNearlyFunded(postDto.isNotificationProjectNearlyFunded());
                return val;
            }
        } else {
            if (postDto.getEmployeeId() != null) {
                employeeService.getById(postDto.getEmployeeId()).ifPresent(post::setEmployee);
            }
        }
        return post;
    }
}
