package de.clerkvest.api.entity.image;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.ProjectService;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.security.EmployeeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.image <p>
 * ImageController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:11
 */
@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*")
public class ImageController {
    private final ImageService service;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public ImageController(ImageService service, CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.service = service;
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @PostMapping(value = "/create/company/{id}")
    public ResponseEntity<Long> createCompanyImage(@RequestParam(value = "file", required = true) MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Image image = service.addImage(file);
        Optional<Company> company = companyService.getById(id);
        company.ifPresentOrElse(present -> {
            present.setImage(image);
        }, () -> {
            throw new ClerkEntityNotFoundException("Company not found");
        });
        return ResponseEntity.ok().body(image.getId());
    }

    /*@PostMapping(value = "/create/employee/{id}")
    public ResponseEntity<Long> createEmployeeImage(@RequestParam(value = "file", required = true) MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Image image = service.addImage(file);
        Optional<Employee> employee = employeeService.getById(id);
        employee.ifPresentOrElse(present->{
            present.set(image);
        },()->{
            throw new ClerkEntityNotFoundException("Company not found");
        });
        return ResponseEntity.ok().body(image.getId());
    }*/

    @PostMapping(value = "/create/project/{id}")
    public ResponseEntity<Long> createProjectImage(@RequestParam(value = "file", required = true) MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Image image = service.addImage(file);
        Optional<Project> project = projectService.getById(id);
        project.ifPresentOrElse(present -> {
            present.setImage(image);
        }, () -> {
            throw new ClerkEntityNotFoundException("Project not found");
        });
        return ResponseEntity.ok().body(image.getId());
    }

    @GetMapping(value = "/get/{id}", produces = "text/plain")
    public ResponseEntity<String> getImage(@PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Optional<Image> image = service.getById(id);
        if (image.isPresent()) {
            InputStream content = service.getContent(image.get());
            //StringResponse response = new StringResponse(Base64.getEncoder().encodeToString(content.readAllBytes()));
            return ResponseEntity.ok(Base64.getEncoder().encodeToString(content.readAllBytes()));
        } else {
            throw new ClerkEntityNotFoundException("Image not found");
        }
    }

    /*@DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<StringResponse> deleteImage(@PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth){
        Optional<Image> image = service.getById(id);
        image.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("Image not found");
        });
        return ResponseEntity.ok().build();
    }*/

}
