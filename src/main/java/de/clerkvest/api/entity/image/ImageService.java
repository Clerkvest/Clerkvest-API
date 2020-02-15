package de.clerkvest.api.entity.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
public class ImageService {

    private final ImageRepository repository;
    private final ImageContentStore imageContentStore;

    @Autowired
    public ImageService(ImageRepository repository, ImageContentStore imageContentStore) {
        this.repository = repository;
        this.imageContentStore = imageContentStore;
    }

    public Image addImage(MultipartFile file) throws IOException {
        Image image = new Image();
        imageContentStore.setContent(image, file.getInputStream());
        return repository.save(image);
    }


  /*  public void update(Long id, String filename, MultipartFile file) throws IOException {
        //Check if company is new
        Optional<Image> existingImage = repository.findById(id);
        existingImage.ifPresentOrElse(
                value -> {
                    value.setFilename(filename);
                    try {
                        value.setImage(new Binary(BsonBinarySubType.BINARY,file.getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    repository.save(value);
                },
                () -> {
                    throw new ClerkEntityNotFoundException("Image can't be updated, not saved yet.");
                }
        );
    }*/

    public Optional<Image> getById(long id) {
        return repository.findById(id);
    }

    public InputStream getContent(Image image) {
        return imageContentStore.getContent(image);
    }

    public void delete(Image image) {
        imageContentStore.unsetContent(image);
        repository.delete(image);
    }
}
