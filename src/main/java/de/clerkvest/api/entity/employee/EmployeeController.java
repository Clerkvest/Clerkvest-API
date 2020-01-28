package de.clerkvest.api.entity.employee;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.DTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
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
public class EmployeeController implements DTOConverter<Employee,EmployeeDTO> {

    private final EmployeeService service;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody Employee fresh) {
        service.save(fresh);
        return ResponseEntity.ok().body(convertToDto(fresh));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updatedEmployee(@Valid @RequestBody Employee updated) {
        service.update(updated);
        return ResponseEntity.ok().build();
    }

    /*@GetMapping(value = "findByNickname")
    public ResponseEntity<Employee> findByNickname(@RequestParam("nickname") String [] nicknames){

    }*/

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<EmployeeDTO> getSingleEmployee(@PathVariable long id) {
        Optional<Employee> employee = service.getById(id);
        if(employee.isPresent()){
            return ResponseEntity.ok(convertToDto(employee.get()));
        }else{
            throw new ClerkEntityNotFoundException("Employee not found");
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = service.getAll();
        List<EmployeeDTO> employeesDTOs = Arrays.asList(modelMapper.map(employees, EmployeeDTO[].class));
        LinkBuilder<EmployeeDTO> linkBuilder = new LinkBuilder<EmployeeDTO>()
                .withSelf(HateoasLink.EMPLOYEE_SINGLE);
        employeesDTOs.forEach(linkBuilder::ifDesiredEmbed);
        return ResponseEntity.ok(employeesDTOs);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
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

    public Employee convertToEntity(EmployeeDTO postDto) throws ParseException {
        Employee post = modelMapper.map(postDto, Employee.class);
        if (postDto.getEmployee_id() != null) {
            Optional<Employee> oldPost = service.getById(postDto.getEmployee_id());
            //oldPost.ifPresent(value -> {post.setNickname(value.getNickname());});
        }
        return post;
    }
}
