package de.clerkvest.api.entity.image;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageContentStore extends ContentStore<Image, String> {
}
