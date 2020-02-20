package de.clerkvest.api.entity.image;

import de.clerkvest.api.implement.service.IServiceEntity;
import lombok.*;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

/**
 * api <p>
 * de.clerkvest.api.entity.image <p>
 * Image.java <p>
 *
 * @author Michael K.
 * @version 1.0
 * @since 21 Dec 2019 19:11
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "image")
@Entity
public class Image extends RepresentationModel<Image> implements IServiceEntity {


    @Id
    @GeneratedValue
    @Column(name = "image_id", updatable = false)
    private Long imageId;

    @ContentId
    private String contentId;

    @ContentLength
    private long contentLength;

    @Override
    public Long getId() {
        return getImageId();
    }

    @Override
    public void setId(Long id) {
        setImageId(id);
    }

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
