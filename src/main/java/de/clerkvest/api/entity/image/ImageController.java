package de.clerkvest.api.entity.image;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
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
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public ImageController(ImageService service, CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.service = service;
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') and (@companyService.getById(#id).isPresent() ? @companyService.getById(#id).get().id.equals(#auth.companyId) : true)")
    @PostMapping(value = "/create/company/{id}")
    public ResponseEntity<Long> createCompanyImage(@RequestParam(value = "file", required = true) MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
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

    @PreAuthorize("hasRole('ROLE_ADMIN') and (@projectService.getById(#id).isPresent() ? @projectService.getById(#id).get().company.id.equals(#auth.companyId) : true)")
    @PostMapping(value = "/create/project/{id}")
    public ResponseEntity<Long> createProjectImage(@RequestParam(value = "file", required = true) MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
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

    @PreAuthorize("hasRole('ROLE_USER')")
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/get/{id}/stream")
    public ResponseEntity<?> getImageStream(@PathVariable Long id, @AuthenticationPrincipal EmployeeUserDetails auth) throws IOException {
        Optional<Image> f = service.getById(id);
        if (f.isPresent()) {
            InputStreamResource inputStreamResource = new InputStreamResource(service.getContent(f.get()));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(f.get().getContentLength());
            headers.set("Content-Type", "image/jpeg");
            return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
        } else {
            throw new ClerkEntityNotFoundException("Image not found");
        }
    }

}
