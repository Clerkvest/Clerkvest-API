package de.clerkvest.api.entity.image;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.entity.project.ProjectService;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.security.EmployeeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ImageController(ImageService service, CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.service = service;
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') and (@companyService.getById(#id).isPresent() ? @companyService.getById(#id).get().id.equals(#auth.companyId) : true)")
    @PostMapping(value = "/create/company/{id}")
    public ResponseEntity<Long> createCompanyImage(@RequestParam(value = "file") MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Image image = service.addImage(file);
        Optional<Company> company = companyService.getById(id);
        company.ifPresentOrElse(present -> {
            Image oldImage = present.getImage();
            if (oldImage != null) {
                service.delete(oldImage);
            }
            present.setImage(image);
            companyService.update(present);
        }, () -> {
            throw new ClerkEntityNotFoundException("Company not found");
        });
        return ResponseEntity.ok().body(image.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@projectService.getById(#id).isPresent() ? @projectService.getById(#id).get().employee.id.equals(#auth.employeeId) : true)")
    @PostMapping(value = "/create/project/{id}")
    public ResponseEntity<Long> createProjectImage(@RequestParam(value = "file") MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Image image = service.addImage(file);
        Optional<Project> project = projectService.getById(id);
        project.ifPresentOrElse(present -> {
            Image oldImage = present.getImage();
            if (oldImage != null) {
                service.delete(oldImage);
            }
            present.setImage(image);
            projectService.update(present);
        }, () -> {
            throw new ClerkEntityNotFoundException("Project not found");
        });
        return ResponseEntity.ok().body(image.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@employeeService.getById(#id).isPresent() ? @employeeService.getById(#id).get().id.equals(#auth.employeeId) : true)")
    @PostMapping(value = "/create/employee/{id}")
    public ResponseEntity<Long> createEmployeeImage(@RequestParam(value = "file") MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Image image = service.addImage(file);
        Optional<Employee> employee = employeeService.getById(id);
        employee.ifPresentOrElse(present -> {
            Image oldImage = present.getImage();
            if (oldImage != null) {
                service.delete(oldImage);
            }
            present.setImage(image);
            employeeService.update(present);
        }, () -> {
            throw new ClerkEntityNotFoundException("Employee not found");
        });
        return ResponseEntity.ok().body(image.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/get/{id}", produces = "text/plain")
    public ResponseEntity<String> getImage(@PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Optional<Image> image = service.getById(id);
        if (image.isPresent()) {
            InputStream content = service.getContent(image.get());
            //StringResponse response = new StringResponse(Base64.getEncoder().encodeToString(content.readAllBytes()));
            if (content == null || content.available() == 0) {
                throw new ClerkEntityNotFoundException("Image not found");
            }
            return ResponseEntity.ok(Base64.getEncoder().encodeToString(content.readAllBytes()));
        } else {
            throw new ClerkEntityNotFoundException("Image not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/get/{id}/stream")
    public ResponseEntity<?> getImageStream(@PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Optional<Image> f = service.getById(id);
        if (f.isPresent()) {
            InputStream content = service.getContent(f.get());
            if (content == null || content.available() == 0) {
                throw new ClerkEntityNotFoundException("Image not found");
            }
            InputStreamResource inputStreamResource = new InputStreamResource(content);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(f.get().getContentLength());
            headers.set("Content-Type", "image/jpeg");
            return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
        } else {
            throw new ClerkEntityNotFoundException("Image not found");
        }
    }

}
