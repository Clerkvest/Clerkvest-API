package de.clerkvest.api.entity.image;

import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.image <p>
 * ImageService.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:11
 */
@Service
public class ImageService implements IService<Image> {

    private final ImageRepository repository;

    @Autowired
    public ImageService (ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save (Image image) {
        repository.save(image);
    }

    @Override
    public List<Image> getAll () {
        return repository.findAll();
    }

    @Override
    public Optional<Image> getById (long id) {
        return repository.findById(id);
    }

    @Override
    public void delete (Image image) {
        repository.delete(image);
    }
}
