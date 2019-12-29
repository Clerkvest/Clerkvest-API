package de.clerkvest.api.entity.image;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class ImageController {
    private final ImageService service;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Image> createImage(@Valid @RequestBody Image fresh) {
        service.save(fresh);
        return ResponseEntity.ok().body(fresh);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Image> getSingleImage(@PathVariable long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable long id) {
        Optional<Image> employee = service.getById(id);
        employee.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("Image not found");
        });
        return ResponseEntity.ok().build();
    }
}
