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
public class EmployeeSettingsController implements DTOConverter<EmployeeSettings, EmployeeSettingsDTO> {

    private final EmployeeSettingsService service;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeSettingsController(EmployeeSettingsService service, EmployeeService employeeService, ModelMapper modelMapper) {
        this.service = service;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@employeeSettingsService.getById(#id).isPresent() ? @employeeSettingsService.getById(#id).get().employee.id.equals(#auth.employeeId) : true)")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<EmployeeSettingsDTO> getSingleEmployeeSetting(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<EmployeeSettings> employeeSetting = service.getById(id);
        if (employeeSetting.isPresent()) {
            return ResponseEntity.ok(convertToDto(employeeSetting.get()));
        } else {
            throw new ClerkEntityNotFoundException("EmployeeSetting not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/get")
    public ResponseEntity<EmployeeSettingsDTO> getSingleEmployeeSetting(@AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Employee> employee = employeeService.getById(auth.getEmployeeId());
        if (employee.isPresent()) {
            EmployeeSettings employeeSettings = service.getByUser(employee.get());
            return ResponseEntity.ok(convertToDto(employeeSettings));
        } else {
            throw new ClerkEntityNotFoundException("Employee not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@employeeSettingsService.getById(#updated.id).isPresent() ? @employeeSettingsService.getById(#updated.id).get().employee.id.equals(#auth.employeeId) : true)")
    @PutMapping(value = "/update")
    public ResponseEntity<EmployeeSettingsDTO> updatedEmployeeSetting(@Valid @RequestBody EmployeeSettingsDTO updated, @AuthenticationPrincipal EmployeeUserDetails auth) {
        EmployeeSettings converted = convertToEntity(updated);
        return ResponseEntity.ok().body(convertToDto(service.update(converted)));
    }

    @Override
    public EmployeeSettingsDTO convertToDto(EmployeeSettings post) {
        EmployeeSettingsDTO postDto = modelMapper.map(post, EmployeeSettingsDTO.class);
        LinkBuilder<EmployeeSettingsDTO> linkBuilder = new LinkBuilder<EmployeeSettingsDTO>()
                .withSelf(HateoasLink.EMPLOYEE_SETTING_SINGLE);
        linkBuilder.ifDesiredEmbed(postDto);
        return postDto;
    }

    @Override
    public EmployeeSettings convertToEntity(EmployeeSettingsDTO postDto) {
        EmployeeSettings post = modelMapper.map(postDto, EmployeeSettings.class);
        if (postDto.getId() != null && postDto.getId() != -1) {
            Optional<EmployeeSettings> oldPost = service.getById(postDto.getId());
            if (oldPost.isPresent()) {
                EmployeeSettings val = oldPost.get();
                val.setNotificationProjectFunded(postDto.isNotificationProjectFunded());
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
